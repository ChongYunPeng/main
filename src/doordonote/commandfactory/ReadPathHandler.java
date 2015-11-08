package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.GetPathCommand;
import doordonote.command.ReadPathCommand;
import doordonote.common.Util;

//@@author A0131436N

/**
 * Reads the storage file from the user specified path.
 * If no path is specified, returns the current file path that is being used.
 * 
 * @author yunpeng
 *
 */
public class ReadPathHandler extends CommandHandler {

	protected ReadPathHandler(String commandBody) {
		super(commandBody);
	}

	@Override
	public Command generateCommand() {		
		if (Util.isEmptyOrNull(commandBody)) {
			return new GetPathCommand();
		}
		return new ReadPathCommand(commandBody);
	}

}
