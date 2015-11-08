package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.SetPathCommand;
import doordonote.common.Util;

//@@author A0131436N

/**
 * Sets the path where the storage file should be saved in the local file system.
 * 
 * @author yunpeng
 *
 */
public class SetPathHandler extends CommandHandler {

	protected SetPathHandler(String commandBody) throws Exception {
		super(commandBody);
		
		if (Util.isEmptyOrNull(commandBody)) {
			throw new Exception(String.format(EXCEPTION_NO_ARGUMENT, "save"));
		}
	}

	@Override
	public Command generateCommand() {
		return new SetPathCommand(commandBody);
	}

}
