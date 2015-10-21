package doordonote.command;

import doordonote.logic.CommandToController;

public class UndoCommand implements Command {
	@Override
	public String execute(CommandToController controller) {
		return controller.undo();
	}

}
