package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.PathCommand;
import doordonote.common.Util;

public class PathHandler extends CommandHandler {

	public PathHandler(String commandBody) throws EmptyCommandBodyException {
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
