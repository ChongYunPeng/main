package doordonote.logic;

import java.util.Date;
import java.util.List;

import doordonote.common.Task;
import doordonote.common.TaskTest;

//import doordonote.storage.Task;

public class Controller implements UIToController, CommandToController {

	@Override
	public String add(String taskDescription, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(int taskID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String find(List<String> keywords) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
	public String parseAndExecuteCommand(String cmdString) {
		// TODO Auto-generated method stub
		return null;
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

}
