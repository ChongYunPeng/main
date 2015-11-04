//@@author A0131436N

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
	
	private static final String MESSAGE_HOME = "Displaying all unfinished task(s)";
	
	protected Storage storage = null;
	protected UIState stateObj = null;
	protected TaskListFilter taskFilter = null;
	
	
	public Controller() throws IOException {
		storage = StorageHandler.getInstance();		
		stateObj = new UIState();
		taskFilter = new TaskListFilter(storage);
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
		List<Task> oldTaskList = taskFilter.getUserTaskList(stateObj);
		Task taskToBeAdded = Util.createTask(taskDescription, startDate, endDate);
		String outputMsg = storage.add(taskToBeAdded);
		
		taskFilter.updateFullTaskList();
		
		if (stateObj.displayType != ListType.NORMAL) {
			stateObj.setDefault();
		} else {
			List<Task> newTaskList = taskFilter.getUserTaskList(stateObj);
			if (newTaskList.size() > oldTaskList.size()) {
				stateObj.clearTempState();				
			} else {
				stateObj.setDefault();
			}
		}
		stateObj.idNewTask = getNewTaskId(taskToBeAdded);
		return outputMsg;
	}

	@Override
	public String delete(int taskId) throws Exception {
		stateObj.clearTempState();
		Task taskToDelete = getTask(taskId);
		String outputMsg = null;
		if (stateObj.displayType == ListType.DELETED) {
			outputMsg = storage.remove(taskToDelete);
		} else {
			outputMsg = storage.delete(taskToDelete);

		}
		taskFilter.updateFullTaskList();
		return outputMsg;
	}
	
	protected Task getTask(int taskId) throws Exception {
		List<Task> userTaskList = taskFilter.getUserTaskList(stateObj);
		if (taskId > userTaskList.size()) {
			throw new Exception("Invalid taskID!");
		}
		return userTaskList.get(taskId - 1);
	}
	

	@Override
	public String find(List<String> keywords) throws IOException {
		stateObj.clearTempState();
		stateObj.filterList = keywords;
		stateObj.startDate = null;
		
		List<Task> userTaskList = taskFilter.getUserTaskList(stateObj);
		
		if (!userTaskList.isEmpty()) {
			return userTaskList.size() + " task(s) found";
		} else {
			return "No task found!";
		}
	}

	@Override
	public String finish(int taskId) throws Exception {
		stateObj.clearTempState();

		Task taskToFinish = getTask(taskId);
		String outputMsg = storage.finish(taskToFinish);
    	return outputMsg;
	}
	
	@Override
	public UIState getState() {
		return stateObj;
	}

	@Override
	public String help() {
		stateObj.clearTempState();
		stateObj.helpBox = "help";
		return "Displaying help";
	}

	@Override
	public String help(String commandType) {
		stateObj.clearTempState();

		stateObj.helpBox = commandType;
		return "Displaying " + commandType + " help";
	}

	@Override
	public String redo() throws IOException {
		String outputMsg = storage.redo();
		taskFilter.updateFullTaskList();
		stateObj.setDefault();
		return outputMsg;
	}

	@Override
	public String undo() throws IOException {
		String outputMsg = storage.undo();
		taskFilter.updateFullTaskList();
		stateObj.setDefault();
		return outputMsg;
	}

	@Override
	public String update(int taskId, String taskDescription, Date startDate, Date endDate) throws Exception {
		stateObj.clearTempState();

		if (stateObj.displayType == ListType.FINISHED || stateObj.displayType == ListType.DELETED) {
			throw new Exception("Cannot update deleted/ finished tasks!");
		}
		
		Task taskToUpdate = getTask(taskId);
		Task newTask = Util.createTask(taskDescription, startDate, endDate);
		String outputMsg = storage.update(taskToUpdate, newTask);

		taskFilter.updateFullTaskList();
		
		stateObj.idNewTask = getNewTaskId(newTask);
		return outputMsg;
	}
	
	@Override
	public String home() throws IOException {
		stateObj.setDefault();
		taskFilter.updateFullTaskList();
		return MESSAGE_HOME;
	}


	@Override
	public String restore(int taskId) throws Exception {
		stateObj.clearTempState();

		if (stateObj.displayType == ListType.NORMAL) {
			throw new Exception("Cannot restore an undeleted/ unfinished task!");
		}
		
		Task taskToRestore = getTask(taskId);
		String outputMsg = storage.restore(taskToRestore);
		return outputMsg;
	}


	@Override
	public String displayFinished() throws IOException {
		stateObj.setDefault();
		stateObj.displayType = ListType.FINISHED;
		return "Displaying finished tasks";
	}


	@Override
	public String displayDeleted() throws IOException {
		stateObj.setDefault();
		stateObj.displayType = ListType.DELETED;
		return "Displaying deleted tasks";
	}

	public String displayOverDue() throws IOException {
		stateObj.setDefault();
		stateObj.displayType = ListType.OVERDUE;
		return "Displaying overdue tasks";		
	}
	
	@Override
	public String getTaskID(int taskId) throws Exception {
		stateObj.clearTempState();
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
		List<Task> userTaskList = taskFilter.getUserTaskList(stateObj);
		return userTaskList.indexOf(newTask);
	}

	@Override
	public List<Task> getUserTaskList() throws IOException {
		List<Task> userTaskList = taskFilter.getUserTaskList(stateObj);
		return userTaskList;
	}

	@Override
	public String find(Date startDate) throws IOException {
		assert(startDate != null);
		stateObj.clearTempState();
		stateObj.startDate = startDate;
		stateObj.filterList = null;
		return "Displaying from " + Util.getDateString(startDate);
	}


}
