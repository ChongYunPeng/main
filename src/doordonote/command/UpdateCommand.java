package doordonote.command;

import java.util.Date;

import doordonote.logic.Controller;
import doordonote.common.Task;

public class UpdateCommand implements Command {

	protected int taskID = -1;
	protected String taskDescription = null;
	protected Date startDate = null;
	protected Date endDate = null;
	protected Task originalTask = null;
	protected Task newTask = null;
	
	public UpdateCommand(int taskID, String taskDescription, Date startDate, Date endDate) {
		
		assert(taskID > 0); 				// UpdateHandler should have checked that taskID > 0
		assert(taskDescription != null); 	// UpdateHandler should have checked that taskDescription is not null 
		this.taskDescription = taskDescription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.taskID = taskID;
	}
	@Override
	public boolean isUndoable() {
		return true;
	}

	@Override
	public String undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String execute(Controller controller) {
		controller.add(taskDescription, startDate, endDate);
		controller.delete(taskID);
		return "Task completed";
	}
	



}
