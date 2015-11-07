//@@author A0131436N

package doordonote.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import doordonote.common.Task;
import doordonote.common.Util;
import doordonote.logic.UIState.ListType;
import doordonote.storage.Storage;
import doordonote.storage.StorageHandler;

public class Controller implements CommandToController {
	
	private static final String MESSAGE_HOME = "Displaying all unfinished task(s)";
	
	protected Storage storage = null;
	protected UIState stateObj = null;
	protected TaskFilter taskFilter = null;
	
	protected List<Task> userTaskList = null;
	
	
	public Controller() throws IOException {
		this.storage = StorageHandler.getInstance();		
		stateObj = new UIState();
		taskFilter = new TaskFilter(storage);
		updateTaskList();		
	}
	
	/**
	 * @param storage
	 * 
	 * Used for injection dependency to replace Storage with a stub for testing
	 * @throws IOException 
	 */
	protected Controller(Storage storage) throws IOException {
		this.storage = storage;
		stateObj = new UIState();
		taskFilter = new TaskFilter(storage);
		userTaskList = new ArrayList<Task>();
		updateTaskList();
	}

	@Override
	public String add(String taskDescription, Date startDate, Date endDate) throws IOException {
		// Should have checked this in Command
		assert(!Util.isEmptyOrNull(taskDescription));
		stateObj.clearTempState();
		
		List<Task> oldTaskList = updateTaskList();
		
		Task taskToBeAdded = Util.createTask(taskDescription, startDate, endDate);
		String outputMsg = storage.add(taskToBeAdded);		
		if (stateObj.displayType != ListType.NORMAL) {
			stateObj.setDefault();
		} else {
			List<Task> newTaskList = updateTaskList();
			assert(newTaskList != null && oldTaskList != null);
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
		return outputMsg;
	}
	
	protected Task getTask(int taskId) throws Exception {
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
		
		List<Task> userTaskList = updateTaskList();
		
		if (!userTaskList.isEmpty()) {
			return userTaskList.size() + " task(s) found";
		} else {
			return "No task found!";
		}
	}

	@Override
	public String finish(int taskId) throws Exception {
		stateObj.clearTempState();
		
		if (stateObj.displayType == ListType.FINISHED) {
			throw new Exception("Task is already finished!");
		}

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
		return "Displaying help";
	}

	@Override
	public String redo() throws IOException {
		stateObj.setDefault();
		String outputMsg = storage.redo();
		return outputMsg;
	}

	@Override
	public String undo() throws IOException {
		stateObj.setDefault();
		String outputMsg = storage.undo();
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

		updateTaskList();
		
		stateObj.idNewTask = getNewTaskId(newTask);
		return outputMsg;
	}
	
	@Override
	public String home() throws IOException {
		stateObj.setDefault();
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
	
	@Override
	public String getTaskStringById(int taskId) throws Exception {
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
	 * @throws IOException 
	 */
	protected int getNewTaskId(Task newTask) throws IOException {
		updateTaskList();
		return userTaskList.indexOf(newTask);
	}

	@Override
	public List<Task> updateTaskList() throws IOException {
		userTaskList = taskFilter.getUserTaskList(stateObj);
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

	@Override
	public String getStorageFilePath(String pathName) {
		assert(pathName != null);
		stateObj.setDefault();
		return storage.get(pathName);
	}

	@Override
	public String path(String pathName) {
		assert(pathName != null);
		stateObj.setDefault();
		return storage.path(pathName);
	}


}
