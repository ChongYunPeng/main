package doordonote.command;

import doordonote.logic.Controller;

public class FindCommand implements Command {

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
		controller.undo();
		return null;
	}

}
