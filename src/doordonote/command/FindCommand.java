package doordonote.command;

import doordonote.logic.CommandToController;

public class FindCommand implements Command {

	@Override
	public boolean isUndoable() {
		return false;
	}

	@Override
	public String execute(CommandToController controller) {
		controller.undo();
		return null;
	}

}
