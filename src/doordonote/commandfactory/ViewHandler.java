//@@author A0131436N

package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.ViewDeletedTaskCommand;
import doordonote.command.ViewFinishedTaskCommand;
import doordonote.common.Util;

public class ViewHandler extends CommandHandler {

	public ViewHandler(String commandBody) throws EmptyCommandBodyException {
		super(commandBody);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new EmptyCommandBodyException();
		}
	}

	@Override
	public Command generateCommand() throws Exception {
		if (commandBody.trim().toLowerCase().equals("deleted")) {
			return new ViewDeletedTaskCommand();
		} else if (commandBody.trim().toLowerCase().equals("finished")) {
			return new ViewFinishedTaskCommand();
		} else {
			throw new Exception("Invalid display arguments");
		}
	}

}
