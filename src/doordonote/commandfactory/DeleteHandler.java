package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.DeleteCommand;
import doordonote.common.Util;

//@@author A0131436N

public class DeleteHandler extends CommandHandler {
	
	protected DeleteHandler(String commmandBody) throws EmptyCommandBodyException {
		super(commmandBody);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new EmptyCommandBodyException();
		}
	}

	@Override
	public Command generateCommand() throws NumberFormatException, NegativeIndexException {
		int indexToDelete = getTaskIdFromString(commandBody);
		if (indexToDelete <= 0) {
			throw new NegativeIndexException();
		} else {
			return new DeleteCommand(indexToDelete);
		}	
	}

}
