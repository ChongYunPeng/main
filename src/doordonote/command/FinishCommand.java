package doordonote.command;

import doordonote.logic.Controller;

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
	public String undo() {
		return null;
	}

	@Override
	public String execute(Controller controller) {
		controller.finish(taskIdToFinish);
		return null;
	}

}
