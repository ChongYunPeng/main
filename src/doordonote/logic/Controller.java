package doordonote.logic;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import doordonote.common.Task;
import doordonote.common.Util;
import doordonote.logic.UIState.ListType;
import doordonote.storage.Storage;
import doordonote.storage.StorageHandler;

//import doordonote.storage.Task;

public class Controller implements CommandToController {
	
	private static final String MESSAGE_HOME = "Displaying all unfinished tasks";
	
	protected Storage storage = null;
	protected UIState stateObj = null;
	protected TaskListFilter taskFilter = null;
	protected List<Task> userTaskList = null;
	
	public Controller() throws IOException {
		storage = StorageHandler.getInstance();		
		stateObj = new UIState();
		taskFilter = new TaskListFilter(storage);
		userTaskList = taskFilter.getUserTaskList(stateObj);
	}
	
	/**
	 * @param store
	 * 
	 * Used for injection dependency to replace Storage with a stub for testing
	 */
	protected void setStorageTaskList(Storage storage) {
		this.storage = storage;
		taskFilter.setStorage(storage);
	}

	@Override
	public String add(String taskDescription, Date startDate, Date endDate) throws IOException {
		List<Task> oldTaskList = userTaskList;
		Task taskToBeAdded = Util.createTask(taskDescription, startDate, endDate);
		String outputMsg = storage.add(taskToBeAdded);
		if (stateObj.displayType != ListType.NORMAL) {
			stateObj.setDefault();
		} else {
			List<Task> newTaskList = taskFilter.getUserTaskList(stateObj);
			if (newTaskList.size() > oldTaskList.size()) {
				// task is added with the same task filter
				// Display the task list in the same filter
			} else {
				stateObj.setDefault();
			}
		}
		userTaskList = taskFilter.getUserTaskList(stateObj);
		stateObj.idNewTask = getNewTaskId(taskToBeAdded);
		return outputMsg;
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
		userTaskList = taskFilter.getUserTaskList(stateObj);
		return outputMsg;
	}
	
	// TODO add exception handling for when taskId > List size
	protected Task getTask(int taskId) throws Exception {
		if (taskId > userTaskList.size()) {
			throw new Exception("Invalid taskID!");
		}
		return userTaskList.get(taskId - 1);
	}
	

	@Override
	public String find(List<String> keywords) throws IOException {
		stateObj.filterList = keywords;
		userTaskList = taskFilter.getUserTaskList(stateObj);
		
		if (!userTaskList.isEmpty()) {
			return userTaskList.size() + " task(s) found";
		} else {
			return "No task found!";
		}
	}

	@Override
	public String finish(int taskId) throws Exception {
		Task taskToFinish = getTask(taskId);
		String outputMsg = storage.finish(taskToFinish);
        
		userTaskList = taskFilter.getUserTaskList(stateObj);
		return outputMsg;
	}
	
	@Override
	public UIState getState() {
		return stateObj;
	}

	@Override
	public String help() {
		stateObj.helpBox = "help";
		return "Displaying help";
	}

	@Override
	public String help(String commandType) {
		stateObj.helpBox = commandType;
		return "Displaying " + commandType + " help";
	}

	@Override
	public String redo() throws IOException {
		String outputMsg = storage.redo();
		stateObj.setDefault();
		return outputMsg;
	}

	@Override
	public String undo() throws IOException {
		String outputMsg = storage.undo();

		stateObj.setDefault();
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


		List<Task> oldTaskList = userTaskList;
		List<Task> newTaskList = taskFilter.getUserTaskList(stateObj);
		if (newTaskList.size() > oldTaskList.size()) {
			// task is added with the same task filter
			// Display the task list in the same filter
		} else {
			stateObj.setDefault();
		}
		userTaskList = taskFilter.getUserTaskList(stateObj);
		stateObj.idNewTask = getNewTaskId(newTask);
		return outputMsg;
	}
	
	@Override
	public String home() throws IOException {
		stateObj.setDefault();
		userTaskList = taskFilter.getUserTaskList(stateObj);
		return MESSAGE_HOME;
	}


	@Override
	public String restore(int taskId) throws Exception {
		if (stateObj.displayType == ListType.NORMAL) {
			throw new Exception("Cannot restore an undeleted/ unfinished task!");
		}
		
		Task taskToRestore = getTask(taskId);
		String outputMsg = storage.restore(taskToRestore);
		userTaskList = taskFilter.getUserTaskList(stateObj);		
		return outputMsg;
	}


	@Override
	public String displayFinished() throws IOException {
		stateObj.setDefault();
		stateObj.displayType = ListType.FINISHED;
		userTaskList = taskFilter.getUserTaskList(stateObj);
		return "Displaying finished tasks";
	}


	@Override
	public String displayDeleted() throws IOException {
		stateObj.setDefault();
		stateObj.displayType = ListType.DELETED;
		userTaskList = taskFilter.getUserTaskList(stateObj);
		return "Displaying deleted tasks";
	}

	public String displayOverDue() throws IOException {
		stateObj.setDefault();
		stateObj.displayType = ListType.OVERDUE;
		userTaskList = taskFilter.getUserTaskList(stateObj);
		return "Displaying overdue tasks";		
	}
	
	@Override
	public String getTaskID(int taskId) throws Exception {
		Task taskToBeUpdated = getTask(taskId);
		stateObj.inputBox = getTaskToBeUpdated(taskToBeUpdated, taskId);
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
	
	/**
	 * @param oldTaskList
	 * @return taskId of new task added
	 */
	protected int getNewTaskId(Task newTask) {
		return userTaskList.indexOf(newTask);
	}

	@Override
	public List<Task> getUserTaskList() throws IOException {
		userTaskList = taskFilter.getUserTaskList(stateObj);
		return userTaskList;
	}


}
