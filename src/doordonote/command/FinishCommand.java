package doordonote.command;

import doordonote.logic.CommandToController;

public class FinishCommand implements Command {
	protected int taskIdToFinish = -1;
	
	public FinishCommand(int taskIdToFinish) {
		assert(taskIdToFinish > 0);
		this.taskIdToFinish = taskIdToFinish;
	}
	
	@Override
	public boolean isUndoable() {
		return false;
	}

	@Override
	public String execute(CommandToController controller) {
		controller.finish(taskIdToFinish);
		return null;
	}

}
