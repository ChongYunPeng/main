package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.RestoreCommand;
import doordonote.common.Util;

//@@author A0131436N

public class RestoreHandler extends CommandHandler {

	protected RestoreHandler(String commandBody) throws EmptyCommandBodyException {
		super(commandBody);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new EmptyCommandBodyException();
		}
	}

	@Override
	public Command generateCommand() throws NumberFormatException, NegativeIndexException {
		int indexToRestore = getTaskIdFromString(commandBody);
		if (indexToRestore <= 0) {
			throw new NegativeIndexException();
		} else {
			return new RestoreCommand(indexToRestore);
		}	
	}

}
