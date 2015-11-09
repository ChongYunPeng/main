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

	private static final String USER_EDITABLE_TASK_FORMAT = "update %1$s %2$s";	
	
	private static final String MESSAGE_HOME = "Displaying all unfinished task(s)";
	private static final String MESSAGE_FIND = "%1$s task(s) found!";
	private static final String MESSAGE_HELP = "Displaying help.";
	private static final String MESSAGE_DELETED_LIST = "Displaying deleted tasks.";
	private static final String MESSAGE_FINISHED_LIST = "Displaying finished tasks.";
	private static final String MESSAGE_READING_FROM_PATH = "Currently reading from: %1$s";
	private static final String MESSAGE_TASK_FOUND = "Task %1$s found!";
	private static final String MESSAGE_FIND_BY_DATE = "Displaying tasks that ends after %1$s.";

	private static final String EXCEPTION_INVALID_TASK_ID = "Task ID %1$s is not valid. There are only %2$s task(s) in the list";
	private static final String EXCEPTION_TASK_ALREADY_FINISH = "Task is already finished!";
	private static final String EXCEPTION_CANNOT_UPDATE_FINISH_OR_DELETED_TASK = "Cannot update deleted/ finished tasks";
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
	 *            Constructor used for injection dependency to replace Storage
	 *            with a stub for testing
	 * @throws IOException
	 */
	protected Controller(Storage storage) {
		this.storage = storage;
		stateObj = new UIState();
		taskFilter = new TaskFilter(storage);
		undoStack = new LinkedList<UIState>();
		redoStack = new Stack<UIState>();
		userTaskList = null;
		try {
			updateTaskList();
		} catch (IOException e) {
			// Error opening storage file
			userTaskList = new ArrayList<Task>();
		}
	}

	
	@Override
	public String add(String taskDescription, Date startDate, Date endDate) throws IOException, DuplicateTaskException {
		assert(!Util.isEmptyOrNull(taskDescription));

		Task taskToBeAdded = Util.createTask(taskDescription, startDate, endDate);
		String outputMsg = storage.add(taskToBeAdded); // Throws exceptions
		addCurrentStateToUndoStackAndClearRedoStack();

		List<Task> oldTaskList = userTaskList;
		updateTaskList();
		setUIStateOnAdd(oldTaskList);
		stateObj.idNewTask = getNewTaskId(taskToBeAdded);
		return outputMsg;
	}

	/**
	 * Sets UIState to dedisplayType to NORMAL. If displayType is already
	 * NORMAL, compares taskList prior to calling this method and after calling
	 * this method. If there are more tasks in the new taskList, task is added
	 * to user task list without changing stateObj. Hence no need to change
	 * stateObj.
	 * 
	 * Else, task added may be filtered away by other parameters in stateObj.
	 * Set stateObj to default.
	 * 
	 * @param oldTaskList
	 * @throws IOException
	 */
	private void setUIStateOnAdd(List<Task> oldTaskList) {
		if (stateObj.displayType != ListType.NORMAL) {
			stateObj.setDefault();
		} else {
			assert(oldTaskList != null);

			if (userTaskList.size() <= oldTaskList.size()) {
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
	

	/**
	 * @param taskId
	 *            Id of Task to be returned.
	 * @return a Task
	 * @throws Exception
	 */
	private Task getTask(int taskId) throws Exception {
		if (taskId > userTaskList.size()) {
			throw new Exception(String.format(EXCEPTION_INVALID_TASK_ID, taskId, userTaskList.size()));
		}
		return userTaskList.get(taskId - 1);
	}

	
	@Override
	public String find(List<String> keywords) throws IOException {
		stateObj.filterList = keywords;
		stateObj.filterDate = null;

		updateTaskList();
		return String.format(MESSAGE_FIND, userTaskList.size());
	}

	
	@Override
	public String finish(int taskId) throws Exception {
		if (stateObj.displayType == ListType.FINISHED) {
			throw new Exception(EXCEPTION_TASK_ALREADY_FINISH);
		}

		Task taskToFinish = getTask(taskId);
		String outputMsg = storage.finish(taskToFinish);
		addCurrentStateToUndoStackAndClearRedoStack();

		return outputMsg;
	}
	

	
	@Override
	public UIState getState() {
		// Create a copy to prevent modification by other classes.
		UIState copyOfUIState = new UIState(stateObj);
		stateObj.clearTempState();
		return copyOfUIState;
	}
	

	
	@Override
	public String help() {
		stateObj.helpBox = "help";
		return MESSAGE_HELP;
	}

	@Override
	public String help(String commandType) {
		stateObj.helpBox = commandType;
		return MESSAGE_HELP;
	}

	@Override
	public String redo() {
		String outputMsg = storage.redo();
		setCurrentStateFromRedoStack();
		return outputMsg;
	}

	private void setCurrentStateFromRedoStack() {
		if (!redoStack.isEmpty()) {
			stateObj = redoStack.pop();
			undoStack.push(new UIState(stateObj));		
		}
	}
	

	@Override
	public String undo() {
		String outputMsg = storage.undo();
		setCurrentStateFromUndoStack();
		return outputMsg;
	}

	private void setCurrentStateFromUndoStack() {
		if (!undoStack.isEmpty()) {
			stateObj = undoStack.pop();
			redoStack.push(new UIState(stateObj));
		}
	}

	
	@Override
	public String update(int taskId, String taskDescription, Date startDate, Date endDate) throws Exception {
		if (stateObj.displayType == ListType.FINISHED || stateObj.displayType == ListType.DELETED) {
			throw new Exception(EXCEPTION_CANNOT_UPDATE_FINISH_OR_DELETED_TASK);
		}

		Task taskToUpdate = getTask(taskId);
		Task newTask = Util.createTask(taskDescription, startDate, endDate);
		String outputMsg = storage.update(taskToUpdate, newTask);
		addCurrentStateToUndoStackAndClearRedoStack();
		updateTaskList();

		stateObj.idNewTask = getNewTaskId(newTask);
		return outputMsg;
	}

	@Override
	public String home() {
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
	public String viewFinished() {
		stateObj.setDefault();
		stateObj.displayType = ListType.FINISHED;
		return MESSAGE_FINISHED_LIST;
	}

	@Override
	public String viewDeleted() {
		stateObj.setDefault();
		stateObj.displayType = ListType.DELETED;
		return MESSAGE_DELETED_LIST;
	}

	@Override
	public String getTaskStringToUserInputBoxById(int taskId) throws Exception {
		Task taskToBeUpdated = getTask(taskId);
		stateObj.inputBox = getTaskToBeUpdated(taskToBeUpdated, taskId);
		stateObj.idNewTask = -1;
		return String.format(MESSAGE_TASK_FOUND, taskId);
	}

	protected String getTaskToBeUpdated(Task taskToBeUpdated, int taskId) {
		String taskDescription = taskToBeUpdated.getDescription();
		Date startDate = taskToBeUpdated.getStartDate();
		Date endDate = taskToBeUpdated.getEndDate();
		return getUserEditableTaskString(taskId, taskDescription, startDate, endDate);
	}

	private String getUserEditableTaskString(int taskId, String taskDescription, Date startDate, Date endDate) {
		String taskString = null;
		if (endDate == null) {
			taskString = taskDescription;
		} else if (startDate == null) {
			String endDateString = Util.getDateString(endDate);
			taskString = taskDescription + " by " + endDateString;
		} else {
			String startDateString = Util.getDateString(startDate);
			String endDateString = Util.getDateString(endDate);
			taskString = taskDescription + " from " + startDateString + " to " + endDateString;
		}
		return String.format(USER_EDITABLE_TASK_FORMAT, taskId, taskString);
	}

	@Override
	public List<Task> getTaskList() throws IOException {
		updateTaskList();
		return userTaskList;
	}

	@Override
	public String find(Date filterDate) throws IOException {
		assert(filterDate != null);
		stateObj.filterDate = filterDate;
		stateObj.filterList = null;
		return String.format(MESSAGE_FIND_BY_DATE, Util.getDateString(filterDate));
	}

	
	@Override
	public String readFromFilePath(String pathName) {
		assert(pathName != null);
		String feedback = storage.get(pathName);
		clearUndoAndRedoStack();

		stateObj.setDefault();
		return feedback;
	}

	@Override
	public String saveFileAt(String pathName) {
		assert(pathName != null);
		String feedback = storage.path(pathName);
		clearUndoAndRedoStack();

		stateObj.setDefault();
		return feedback;
	}

	private void clearUndoAndRedoStack() {
		undoStack.clear();
		redoStack.clear();
	}

	
	@Override
	public String getCurrentFilePath() {
		return String.format(MESSAGE_READING_FROM_PATH, storage.getCurrentFilePath());
	}

	/**
	 * Used by add and update method. Returns ID of task added for UI to highlight
	 * when that task is added to the list.
	 * 
	 * @param newTask
	 * 			New task that was added.
	 * @return	ID of task added.
	 * @throws IOException
	 */
	private int getNewTaskId(Task newTask) throws IOException {
		updateTaskList();
		return userTaskList.indexOf(newTask);
	}

	/**
	 * Updates the user task list.
	 * 
	 * @throws IOException
	 */
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
