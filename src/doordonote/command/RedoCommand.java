package doordonote.command;

import doordonote.logic.CommandToController;

public class RedoCommand implements Command {
	@Override
	public String execute(CommandToController controller) {
		return controller.redo();
	}

}
