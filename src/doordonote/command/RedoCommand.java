package doordonote.command;

import doordonote.logic.Controller;

public class RedoCommand implements Command {

	@Override
	public boolean isUndoable() {
		return false;
	}

	@Override
	public String execute(Controller controller) {
		return controller.redo();
	}

}
