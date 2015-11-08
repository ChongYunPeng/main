package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.GetPathCommand;
import doordonote.command.ReadPathCommand;
import doordonote.common.Util;

//@@author A0131436N

public class ReadPathHandler extends CommandHandler {

	protected ReadPathHandler(String commandBody) {
		super(commandBody);

	}

	@Override
	public Command generateCommand()
			throws NumberFormatException, NegativeIndexException, ExcessArgumentException, Exception {
		
		if (Util.isEmptyOrNull(this.commandBody)) {
			return new GetPathCommand();
		}
		
		return new ReadPathCommand(this.commandBody);
	}

}
