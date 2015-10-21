package doordonote.command;

import doordonote.logic.CommandToController;

public class UndoCommand implements Command {

	public UndoCommand() {
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

	@Override
	public String execute(CommandToController controller) {
		return controller.undo();
	}

}
