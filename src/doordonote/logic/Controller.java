package doordonote.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import doordonote.command.Command;
import doordonote.commandfactory.CommandFactory;
import doordonote.common.Task;
import doordonote.common.Util;
import doordonote.storage.Storage;
import doordonote.storage.StorageHandler;

//import doordonote.storage.Task;

public class Controller implements UIToLogic, CommandToController {
	
	private static final String MESSAGE_HOME = "HOME";
//	private static final String STATE_UPDATE = "Update";
//	private static final String STATE_DISPLAY = "Display";
//	private static final String STATE_HELP = "Help";
//	private static final String STATE_FIND = "Find";
//	private static final String STATE_HOME = "Home";
//	private static final String STATE_DISPLAY_FINISH = "Display finish";
//	private static final String STATE_DISPLAY_DELETE = "Display delete";
	
	protected CommandFactory cmdFactory = null;
	protected Storage storage = null;
	protected List<Task> fullTaskList = null;
	protected List<Task> userTaskList = null;
//	protected String UIState = null;
	protected UIState stateObj = null;
	
	public Controller() {
		cmdFactory = new CommandFactory();
		storage = StorageHandler.getInstance();
		try {
		    fullTaskList = getStorageTaskList();
		}
		catch(IOException E) {
			
		}

		userTaskList = fullTaskList;
		stateObj = new UIState();
	}
	

	@Override
	public String add(String taskDescription, Date startDate, Date endDate) throws IOException {
		String outputMsg = storage.add(taskDescription, startDate, endDate);
		fullTaskList = getStorageTaskList();
		userTaskList = fullTaskList;
		stateObj.setDefault();
		return outputMsg;
	}

	@Override
	public String delete(int taskId) throws Exception {
		Task taskToDelete = getTask(taskId);
		String outputMsg = storage.delete(taskToDelete);
		fullTaskList = getStorageTaskList();
		userTaskList = fullTaskList;
		stateObj.setDefault();
		return outputMsg;
	}
	
	// TODO add exception handling for when taskId > List size
	protected Task getTask(int taskId) throws Exception {
		if (taskId > userTaskList.size()) {
			throw new Exception("Invalid taskID!");
		}
		return userTaskList.get(taskId - 1);
	}
	
	protected List<Task> getStorageTaskList() throws IOException {
		return storage.readTasks();
	}

	@Override
	public String find(List<String> keywords) {
		assert(keywords != null);
		List<Task> tempList = fullTaskList;
		userTaskList = tempList;
		for (String keyword : keywords) {
			tempList = new ArrayList<Task>();
			for (Task task : userTaskList) {
				if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
					tempList.add(task);
				}
			}
			userTaskList = tempList;
		}
		stateObj.setDefault();
		stateObj.title = "Filter: ";
		for (String word : keywords) {
			stateObj.title += word;
		}
		
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
		fullTaskList = getStorageTaskList();
        
		userTaskList = fullTaskList;
		stateObj.setDefault();
		return outputMsg;
	}
	
	@Override
	public UIState getState() {
		return stateObj;
	}

	@Override
	public List<Task> getTasks() {
		return userTaskList;
	}

	@Override
	public String help() {
		stateObj.setDefault();
		stateObj.helpBox = "help";
		return "Displaying help";
	}

	@Override
	public String help(String commandType) {
		stateObj.setDefault();
		stateObj.helpBox = commandType;
		return "Displaying " + commandType + " help";
	}

	@Override
	public String parseAndExecuteCommand(String userInput) throws Exception {
		Command cmd;
		cmd = cmdFactory.parse(userInput);
		return cmd.execute(this);
	}

	@Override
	public String redo() throws IOException {
		String outputMsg = storage.redo();
		fullTaskList = getStorageTaskList();

		userTaskList = fullTaskList;
		stateObj.setDefault();
		return outputMsg;
	}

	@Override
	public String undo() throws IOException {
		String outputMsg = storage.undo();
		fullTaskList = getStorageTaskList();

		stateObj.setDefault();
		userTaskList = fullTaskList;
		return outputMsg;
	}

	@Override
	public String update(int taskId, String taskDescription, Date startDate, Date endDate) throws Exception {
		Task taskToUpdate = getTask(taskId);
		String outputMsg = storage.update(taskToUpdate, taskDescription, startDate, endDate);
		fullTaskList = getStorageTaskList();
		stateObj.setDefault();
		userTaskList = fullTaskList;
		return outputMsg;
	}
	
	@Override
	public String home() throws IOException {
		fullTaskList = getStorageTaskList();

		stateObj.setDefault();
		userTaskList = fullTaskList;
		return MESSAGE_HOME;
	}


	@Override
	public String restore(int taskId) throws Exception {
		Task taskToRestore = getTask(taskId);
		String outputMsg = storage.restore(taskToRestore);
		fullTaskList = getStorageTaskList();
		stateObj.setDefault();
		userTaskList = fullTaskList;
		return outputMsg;
	}


	@Override
	public String displayFinished() throws IOException {
		fullTaskList = storage.readDoneTasks();
		userTaskList = fullTaskList;
		stateObj.setDefault();
		stateObj.title = "Finished Tasks";
		return "Displaying finished tasks";
	}


	@Override
	public String displayDeleted() throws IOException {
		fullTaskList = storage.readDeletedTasks();
		userTaskList = fullTaskList;
		stateObj.setDefault();
		stateObj.title = "Deleted Tasks";
		return "Displaying deleted tasks";
	}


	@Override
	public String getTaskID(int taskId) throws Exception {
		Task taskToBeUpdated = getTask(taskId);
		stateObj.setDefault();
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



	
	
	
//	public static void main(String[] args) {
//		Controller control = new Controller();
//		control.parseAndExecuteCommand("add task by Mondahy");
//	}
}
