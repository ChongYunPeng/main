package doordonote.command;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 *	Redo the effects made by {@code UndoCommand}
 */
public class RedoCommand implements Command {
	@Override
	public String execute(CommandToController controller) {
		return controller.redo();
	}

}
