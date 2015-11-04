//@@author A0131436N

package doordonote.command;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 *
 */
public class DeleteCommand implements Command {
	int taskID = -1;
	
	/**
	 * @param 	taskID
	 * 			This is the ID of the task users will see in the UI.
	 */
	public DeleteCommand(int taskID) {
		// DeleteHandler should have checked that taskID is more than zero
		assert(taskID > 0);
		this.taskID = taskID;
	}

	@Override
	public String execute(CommandToController controller) throws Exception {
		return controller.delete(taskID);
	}

}
