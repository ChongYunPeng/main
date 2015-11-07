package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.GetPathCommand;
import doordonote.common.Util;

public class GetPathHandler extends CommandHandler {

	public GetPathHandler(String commandBody) throws EmptyCommandBodyException {
		super(commandBody);
		
		if (Util.isEmptyOrNull(this.commandBody)) {
			throw new EmptyCommandBodyException();
		}
	}

	@Override
	public Command generateCommand()
			throws NumberFormatException, NegativeIndexException, ExcessArgumentException, Exception {
		return new GetPathCommand(this.commandBody);
	}

}
