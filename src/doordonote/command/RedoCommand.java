package doordonote.command;

import java.io.IOException;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 *	Redo the effects made by {@code UndoCommand}
 */
public class RedoCommand implements Command {
	@Override
	public String execute(CommandToController controller) throws IOException {
		return controller.redo();
	}

}
