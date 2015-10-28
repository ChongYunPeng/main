package doordonote.command;

import java.io.IOException;

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
	public String execute(CommandToController controller) throws IOException {
		return controller.finish(taskIdToFinish);
	}

}
