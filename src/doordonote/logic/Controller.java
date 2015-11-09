package doordonote.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import doordonote.common.Task;
import doordonote.common.Util;
import doordonote.logic.UIState.ListType;
import doordonote.storage.DuplicateTaskException;
import doordonote.storage.Storage;
import doordonote.storage.StorageHandler;

//@@author A0131436N

public class Controller implements CommandToController {
	
	private static final String MESSAGE_HOME = "Displaying all unfinished task(s)";
	private static final int UNDO_STACK_SIZE = 10;
	
	protected Storage storage = null;
	protected UIState stateObj = null;
	protected TaskFilter taskFilter = null;
	
	protected List<Task> userTaskList = null;
	
	protected Deque<UIState> undoStack = null;
	protected Stack<UIState> redoStack = null;
	
	
	public Controller() {
		this(StorageHandler.getInstance());
	}
	
	/**
	 * @param storage
	 * 
	 * Constructor used for injection dependency to replace Storage with a stub for testing
	 * @throws IOException 
	 */
	protected Controller(Storage storage) {
		this.storage = storage;
		stateObj = new UIState();
		taskFilter = new TaskFilter(storage);
		undoStack = new LinkedList<UIState>();
		redoStack = new Stack<UIState>();
		userTaskList = new ArrayList<Task>();
		try {
			updateTaskList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String add(String taskDescription, Date startDate, Date endDate) throws IOException, DuplicateTaskException {
		assert(!Util.isEmptyOrNull(taskDescription));
		
		List<Task> oldTaskList = userTaskList;
		Task taskToBeAdded = Util.createTask(taskDescription, startDate, endDate);
		String outputMsg = storage.add(taskToBeAdded);
		addCurrentStateToUndoStackAndClearRedoStack();
		
		updateTaskList();
		setUIStateOnAdd(oldTaskList);
		stateObj.idNewTask = getNewTaskId(taskToBeAdded);
		return outputMsg;
	}

	private void setUIStateOnAdd(List<Task> oldTaskList) throws IOException {
		if (stateObj.displayType != ListType.NORMAL) {
			stateObj.setDefault();
		} else {
			List<Task> newTaskList = userTaskList;
			
			assert(newTaskList != null && oldTaskList != null);
			if (newTaskList.size() > oldTaskList.size()) {
				stateObj.clearTempState();				
			} else {
				stateObj.setDefault();
			}
		}
	}

	@Override
	public String delete(int taskId) throws Exception {
		Task taskToDelete = getTask(taskId);
		String outputMsg = null;
		if (stateObj.displayType == ListType.DELETED) {
			outputMsg = storage.remove(taskToDelete);
		} else {
			outputMsg = storage.delete(taskToDelete);

		}
		addCurrentStateToUndoStackAndClearRedoStack();
		return outputMsg;
	}
	
	private Task getTask(int taskId) throws Exception {
		if (taskId > userTaskList.size()) {
			throw new Exception("Invalid taskID!");
		}
		return userTaskList.get(taskId - 1);
	}
	

	@Override
	public String find(List<String> keywords) throws IOException {
		stateObj.filterList = keywords;
		stateObj.filterDate = null;
		
		updateTaskList();
		
		if (!userTaskList.isEmpty()) {
			return userTaskList.size() + " task(s) found";
		} else {
			return "No task found!";
		}
	}

	@Override
	public String finish(int taskId) throws Exception {		
		if (stateObj.displayType == ListType.FINISHED) {
			throw new Exception("Task is already finished!");
		}

		Task taskToFinish = getTask(taskId);
		String outputMsg = storage.finish(taskToFinish);
		addCurrentStateToUndoStackAndClearRedoStack();

    	return outputMsg;
	}
	
	@Override
	public UIState getState() {
		UIState copyOfUIState = new UIState(stateObj);
		stateObj.clearTempState();
		return copyOfUIState;
	}

	@Override
	public String help() {
		stateObj.helpBox = "help";
		return "Displaying help";
	}

	@Override
	public String help(String commandType) {
		stateObj.helpBox = commandType;
		return "Displaying help";
	}

	@Override
	public String redo() throws IOException {
		String outputMsg = storage.redo();
		if (redoStack.isEmpty()) {
			return outputMsg;
		}
		stateObj = redoStack.pop();
		undoStack.push(new UIState(stateObj));
		return outputMsg;
	}

	@Override
	public String undo() throws IOException {
		String outputMsg = storage.undo();
		if (undoStack.isEmpty()) {
			return outputMsg;
		}
		stateObj = undoStack.pop();
		redoStack.push(new UIState(stateObj));
		return outputMsg;
	}

	@Override
	public String update(int taskId, String taskDescription, Date startDate, Date endDate) throws Exception {
		if (stateObj.displayType == ListType.FINISHED || stateObj.displayType == ListType.DELETED) {
			throw new Exception("Cannot update deleted/ finished tasks!");
		}
		
		Task taskToUpdate = getTask(taskId);
		Task newTask = Util.createTask(taskDescription, startDate, endDate);
		String outputMsg = storage.update(taskToUpdate, newTask);
		addCurrentStateToUndoStackAndClearRedoStack();

		updateTaskList();
		
		stateObj.idNewTask = getNewTaskId(newTask);
		stateObj.inputBox = "";
		return outputMsg;
	}
	
	@Override
	public String home() throws IOException {
		stateObj.setDefault();
		return MESSAGE_HOME;
	}


	@Override
	public String restore(int taskId) throws Exception {
		if (stateObj.displayType == ListType.NORMAL) {
			throw new Exception("Cannot restore an undeleted/ unfinished task!");
		}
		
		Task taskToRestore = getTask(taskId);
		String outputMsg = storage.restore(taskToRestore);
		addCurrentStateToUndoStackAndClearRedoStack();
		
		return outputMsg;
	}


	@Override
	public String viewFinished() throws IOException {
		stateObj.setDefault();
		stateObj.displayType = ListType.FINISHED;
		return "Displaying finished tasks";
	}


	@Override
	public String viewDeleted() throws IOException {
		stateObj.setDefault();
		stateObj.displayType = ListType.DELETED;
		return "Displaying deleted tasks";
	}
	
	@Override
	public String getTaskStringById(int taskId) throws Exception {
		Task taskToBeUpdated = getTask(taskId);
		stateObj.inputBox = getTaskToBeUpdated(taskToBeUpdated, taskId);
		stateObj.idNewTask = -1;
		return "Task " + taskId + " found!";
	}


	protected String getTaskToBeUpdated(Task taskToBeUpdated, int taskId) {
		String taskDescription = taskToBeUpdated.getDescription();
		Date startDate = taskToBeUpdated.getStartDate();
		Date endDate = taskToBeUpdated.getEndDate();
		String startDateString = Util.getDateString(startDate);
		String endDateString = Util.getDateString(endDate);

		String taskString = null;
		if (endDate == null) {
			taskString = taskDescription;
		} else if (startDate == null) {
			taskString = taskDescription + " by " + endDateString;
		} else {
			taskString = taskDescription + " from " + startDateString + " to " + endDateString;
		}
		return "update " + taskId + " " + taskString;
	}
	
	
	@Override
	public List<Task> getTaskList() throws IOException {
		updateTaskList();
		return userTaskList; 
	}
	
	@Override
	public String find(Date filterDate) throws IOException {
		assert(filterDate != null);
//		stateObj.clearTempState();
		stateObj.filterDate = filterDate;
		stateObj.filterList = null;
		return "Displaying from " + Util.getDateString(filterDate);
	}

	@Override
	public String readFromFilePath(String pathName) {
//		stateObj.clearTempState();
		assert(pathName != null);
		String feedback = storage.get(pathName);
		undoStack.clear();
		redoStack.clear();
		
		stateObj.setDefault();
		return feedback;
	}

	@Override
	public String saveFileAt(String pathName) {
		assert(pathName != null);
		String feedback = storage.path(pathName);
		undoStack.clear();
		redoStack.clear();
		
		stateObj.setDefault();
		return feedback;
	}

	@Override
	public String getCurrentFilePath() {
		return "Currently reading from: " + storage.getCurrentFilePath();
	}
	
	private int getNewTaskId(Task newTask) throws IOException {
		updateTaskList();
		return userTaskList.indexOf(newTask);
	}
	
	private void updateTaskList() throws IOException {
		userTaskList = taskFilter.getUserTaskList(stateObj);
	}

	/**
	 * Adds current UIState to undoStack and clear redoStack
	 */
	private void addCurrentStateToUndoStackAndClearRedoStack() {
		undoStack.push(new UIState(stateObj));
		redoStack.clear();
		if (undoStack.size() > UNDO_STACK_SIZE) {
			undoStack.removeLast();
		}
	}
}
