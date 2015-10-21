package doordonote.command;

import doordonote.logic.CommandToController;

public class DeleteCommand implements Command {
	int taskID = -1;
	protected CommandToController controller;
	protected boolean hasExecuted = false;

	
	/**
	 * @param 	taskID
	 * 			This is the ID of the task as seen from the UI.
	 */
	public DeleteCommand(int taskID) {
		// DeleteHandler should have checked that taskID is more than zero
		assert(taskID > 0);
		this.taskID = taskID;
	}
	

	@Override
	public boolean isUndoable() {
		return true;
	}

	@Override
	public String execute(CommandToController controller) {
		this.controller = controller;
		return controller.delete(taskID);
	}

}
