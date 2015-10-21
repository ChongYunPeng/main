package doordonote.command;

import doordonote.logic.Controller;

public class UndoCommand implements Command {

	public UndoCommand() {
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

	@Override
	public String execute(Controller controller) {
		return controller.undo();
	}

}
