package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.PathCommand;
import doordonote.common.Util;

//@@author A0131436N

public class PathHandler extends CommandHandler {

	protected PathHandler(String commandBody) throws EmptyCommandBodyException {
		super(commandBody);
		
		if (Util.isEmptyOrNull(commandBody)) {
			throw new EmptyCommandBodyException();
		}
	}

	@Override
	public Command generateCommand()
			throws NumberFormatException, NegativeIndexException, ExcessArgumentException, Exception {
		return new PathCommand(commandBody);
	}

}
