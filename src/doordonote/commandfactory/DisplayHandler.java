package doordonote.commandfactory;

import com.sun.xml.internal.fastinfoset.stax.events.Util;

import doordonote.command.Command;
import doordonote.command.DisplayDeletedTaskCommand;
import doordonote.command.DisplayFinishedTaskCommand;

public class DisplayHandler extends CommandHandler {

	public DisplayHandler(String commandBody) throws EmptyCommandBodyException {
		super(commandBody);
		if (Util.isEmptyString(commandBody)) {
			throw new EmptyCommandBodyException();
		}
	}

	@Override
	public Command generateCommand() throws Exception {
		if (commandBody.trim().toLowerCase().equals("deleted")) {
			return new DisplayDeletedTaskCommand();
		} else if (commandBody.trim().toLowerCase().equals("finished")) {
			return new DisplayFinishedTaskCommand();
		} else {
			throw new Exception("Invalid display arguments");
		}
	}

}
