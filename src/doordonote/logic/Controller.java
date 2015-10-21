package doordonote.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import doordonote.command.Command;
import doordonote.commandfactory.CommandFactory;
import doordonote.common.EventTask;
import doordonote.common.Task;
import doordonote.common.TaskTest;
import doordonote.storage.Storage;
import doordonote.storage.StorageHandler;

//import doordonote.storage.Task;

public class Controller implements UIToController, CommandToController {
	protected CommandFactory cmdFactory = null;
	protected Deque<Command> undoStack = null;
	protected Deque<Command> redoStack = null;
	protected Storage storage = null;
	protected List<Task> fullTaskList = null;
	protected List<Task> userTaskList = null;
	
	public Controller() {
		cmdFactory = new CommandFactory();
		undoStack = new LinkedList<Command>();
		redoStack = new LinkedList<Command>();
		storage = StorageHandler.getInstance();
		try {
			fullTaskList = getStorageTaskList();
			userTaskList = fullTaskList;
		} catch (IOException e) {
			// TODO Add exception handling
			e.printStackTrace();
		}
	}
	

	@Override
	public String add(String taskDescription, Date startDate, Date endDate) {
		String outputMsg = storage.add(taskDescription, startDate, endDate);
		try {
			fullTaskList = getStorageTaskList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userTaskList = fullTaskList;
		return outputMsg;
	}

	@Override
	public String delete(int taskID) {
		Task taskToDelete = getTask(taskID);
		String outputMsg = storage.delete(taskToDelete);
		try {
			fullTaskList = getStorageTaskList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userTaskList = fullTaskList;
		return outputMsg;
	}
	
	protected Task getTask(int taskID) {
		return userTaskList.get(taskID);
	}
	
	protected List<Task> getStorageTaskList() throws IOException {
		return storage.readTasks();
	}

	@Override
	public String find(List<String> keywords) {
		assert(keywords != null);
		List<Task> tempList = fullTaskList;
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
			return "Tasks found";
		} else {
			return "No task found";
		}
	}

	@Override
	public String find(String taskType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String finish(int taskID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> getTasks() {
		return userTaskList;
	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String help(String commandType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String parseAndExecuteCommand(String userInput) {
		Command cmd = cmdFactory.parse(userInput);
		if (cmd.isUndoable()) {
			undoStack.push(cmd);
			if (undoStack.size() < 10) {
				undoStack.removeLast();
			} 
		}
		return cmd.execute(this);
	}

	@Override
	public String redo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(int taskID, String taskDescription, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		UIToController controller = new Controller();
		controller.parseAndExecuteCommand("add run from monday to fri");
	}

}
