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

public class Controller implements UIToController, CommandToController {
	
	private static final String MESSAGE_HOME = "Back to homescreen";
	private static final String STATE_UPDATE = "Update";
	private static final String STATE_HELP = "Help";
	private static final String STATE_FIND = "Find";
	private static final String STATE_HOME = "Home";
	private static final String STATE_DISPLAY_FINISH = "Display finish";
	private static final String STATE_DISPLAY_DELETE = "Display delete";
	
	protected CommandFactory cmdFactory = null;
	protected Storage storage = null;
	protected List<Task> fullTaskList = null;
	protected List<Task> userTaskList = null;
	protected String UIState = null;
	protected String taskToBeUpdatedString = null;
	
	public Controller() {
		cmdFactory = new CommandFactory();
		storage = StorageHandler.getInstance();
		try {
			fullTaskList = getStorageTaskList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userTaskList = fullTaskList;
	}
	

	@Override
	public String add(String taskDescription, Date startDate, Date endDate) throws IOException {
		String outputMsg = storage.add(taskDescription, startDate, endDate);
		fullTaskList = getStorageTaskList();
		UIState = STATE_UPDATE;
		userTaskList = fullTaskList;
		return outputMsg;
	}

	@Override
	public String delete(int taskId) throws Exception {
		Task taskToDelete = getTask(taskId);
		String outputMsg = storage.delete(taskToDelete);
		fullTaskList = getStorageTaskList();
		UIState = STATE_UPDATE;
		userTaskList = fullTaskList;
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
		
		if (!userTaskList.isEmpty()) {
			UIState = STATE_FIND;
			return "Tasks found";
		} else {
			UIState = STATE_UPDATE;
			return "No task found!";
		}
	}

	@Override
	public String finish(int taskId) throws Exception {
		Task taskToFinish = getTask(taskId);
		String outputMsg = storage.finish(taskToFinish);
		fullTaskList = getStorageTaskList();

		userTaskList = fullTaskList;
		UIState = STATE_UPDATE;

		return outputMsg;
	}
	
	@Override
	public String getState() {
		return UIState;
	}

	@Override
	public List<Task> getTasks() {
		return userTaskList;
	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		UIState = STATE_HELP;
		return null;
	}

	@Override
	public String help(String commandType) {
		// TODO Auto-generated method stub
		UIState = STATE_HELP + commandType;
		return null;
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
		UIState = STATE_UPDATE;
		return outputMsg;
	}

	@Override
	public String undo() throws IOException {
		String outputMsg = storage.undo();
		fullTaskList = getStorageTaskList();

		UIState = STATE_UPDATE;
		userTaskList = fullTaskList;
		return outputMsg;
	}

	@Override
	public String update(int taskId, String taskDescription, Date startDate, Date endDate) throws Exception {
		Task taskToUpdate = getTask(taskId);
		String outputMsg = storage.update(taskToUpdate, taskDescription, startDate, endDate);
		fullTaskList = getStorageTaskList();

		UIState = STATE_UPDATE;
		userTaskList = fullTaskList;
		return outputMsg;
	}
	
	@Override
	public String home() throws IOException {
		fullTaskList = getStorageTaskList();

		UIState = STATE_HOME;
		userTaskList = fullTaskList;
		return MESSAGE_HOME;
	}


	@Override
	public String restore(int taskId) throws Exception {
		Task taskToRestore = getTask(taskId);
		String outputMsg = storage.restore(taskToRestore);
		fullTaskList = getStorageTaskList();

		UIState = STATE_UPDATE;
		userTaskList = fullTaskList;
		return outputMsg;
	}


	@Override
	public String displayFinished() throws IOException {
		fullTaskList = storage.readDoneTasks();
		userTaskList = fullTaskList;
		UIState = STATE_DISPLAY_FINISH;
		return "Displaying finished tasks";
	}


	@Override
	public String displayDeleted() throws IOException {
		fullTaskList = storage.readDeletedTasks();
		userTaskList = fullTaskList;
		UIState = STATE_DISPLAY_DELETE;
		return "Displaying deleted tasks";
	}


	@Override
	public String getTaskID(int taskId) throws Exception {
		Task taskToBeUpdated = getTask(taskId);
		setTaskToBeUpdated(taskToBeUpdated);
		return "Task " + taskId + " found!";
	}


	protected void setTaskToBeUpdated(Task taskToBeUpdated) {
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
		taskToBeUpdatedString = taskString;
	}


	@Override
	public String getTaskToBeUpdated() {

		return taskToBeUpdatedString;
	}
	
	
	
//	public static void main(String[] args) {
//		Controller control = new Controller();
//		control.parseAndExecuteCommand("add task by Mondahy");
//	}
}
