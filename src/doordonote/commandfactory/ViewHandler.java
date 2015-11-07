package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.ViewDeletedTaskCommand;
import doordonote.command.ViewFinishedTaskCommand;
import doordonote.common.Util;

//@@author A0131436N

public class ViewHandler extends CommandHandler {

	protected ViewHandler(String commandBody) throws EmptyCommandBodyException {
		super(commandBody);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new EmptyCommandBodyException();
		}
		this.commandBody = commandBody.trim().toLowerCase();
	}

	@Override
	public Command generateCommand() throws Exception {
		if (commandBody.contains("del")) {
			return new ViewDeletedTaskCommand();
		} else if (commandBody.contains("fin")) {
			return new ViewFinishedTaskCommand();
		} else {
			throw new Exception("Invalid display arguments");
		}
	}

}
