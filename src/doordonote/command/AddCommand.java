package doordonote.command;

import java.util.Date;

import doordonote.logic.Controller;
import doordonote.common.Task;

public class AddCommand implements Command {
	protected String taskDescription = null;
	protected Date startDate = null;
	protected Date endDate = null;
	protected Task addedTask = null;
	protected Controller controller = null;
	protected boolean hasExecuted = false;
	
	/**
	 * @param 	taskDescription 
	 * 			the description of the task to be added
	 * @param 	startDate
	 * 			The start date of the event. A null value indicates this task is not an event
	 * @param 	endDate
	 * 			The end date of the event. A null value indicates this task is not a deadline
	 */
	public AddCommand(String taskDescription, Date startDate, Date endDate) {
		// AddHandler should have checked that task is not null
		assert(taskDescription != null);
		
		this.taskDescription = taskDescription;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	@Override
	public String execute(Controller controller) {
		assert(controller != null);
		this.controller = controller;
		this.addedTask = controller.add(taskDescription, startDate, endDate);
		
		// Handle error
		if (addedTask == null) {
			
		}
		hasExecuted = true;
		// TODO: Add return statement
		return null;
	}

	@Override
	public boolean isUndoable() {
		return true;
	}

	@Override
	public String undo() {
		assert(hasExecuted);
		controller.delete(addedTask);
		
		// TODO: Add return statement
		return null;
	}
}
