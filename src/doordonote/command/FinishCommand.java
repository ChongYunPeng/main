//@@author A0131436N

package doordonote.command;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 *
 */
public class FinishCommand implements Command {
	protected int taskIdToFinish = -1;
	
	/**
	 * @param 	taskIdToFinish
	 * 			This is the ID of the task users will see in the UI.
	 */
	public FinishCommand(int taskIdToFinish) {
		assert(taskIdToFinish > 0);
		this.taskIdToFinish = taskIdToFinish;
	}

	@Override
	public String execute(CommandToController controller) throws Exception {
		return controller.finish(taskIdToFinish);
	}

}
