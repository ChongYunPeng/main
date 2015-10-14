package doordonote.command;

import doordonote.logic.Controller;
import doordonote.storage.Task;

public class DeleteCommand implements Command {
	int taskID = -1;
	protected Controller controller;
	protected Task deletedTask = null;
	protected boolean hasExecuted = false;

	
	/**
	 * @param 	taskID
	 * 			This is the ID of the task as seen from the UI.
	 */
	public DeleteCommand(int taskID) {
		// Delete handler object should have checked that taskID is not negative
		assert(taskID >= 0);
		this.taskID = taskID;
	}
	

	@Override
	public boolean isUndoable() {
		return true;
	}

	@Override
	public String execute(Controller controller) {
		this.controller = controller;
		deletedTask = controller.delete(taskID);
		return null;
	}
	
	@Override
	public String undo() {
		assert(hasExecuted);
		controller.add(deletedTask);
		return null;
	}


}
