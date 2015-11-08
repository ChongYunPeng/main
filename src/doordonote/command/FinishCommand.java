package doordonote.command;

import doordonote.logic.CommandToController;

//@@author A0131436N

/**
 * @author yunpeng
 *
 */
public class FinishCommand implements Command {
	protected int taskIdToFinish = -1;
	
	/**
	 * Marks a task as finished.
	 * 
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
