package doordonote.command;

import java.io.IOException;

import doordonote.logic.CommandToController;

//@@author A0131436N

/**
 * @author yunpeng
 *
 *	Undo the previous action that modified the storage file.
 *	(E.g. AddCommand/ DeleteCommand/ UpdateCommand)
 */
public class UndoCommand implements Command {
	@Override
	public String execute(CommandToController controller) throws IOException {
		return controller.undo();
	}

}
