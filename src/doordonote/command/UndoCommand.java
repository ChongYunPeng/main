package doordonote.command;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 *
 *	Undo the previous action that modified the storage file.
 *	(E.g. AddCommand/ DeleteCommand/ UpdateCommand)
 */
public class UndoCommand implements Command {
	@Override
	public String execute(CommandToController controller) {
		return controller.undo();
	}

}
