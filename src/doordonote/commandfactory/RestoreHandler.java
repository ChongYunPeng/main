package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.RestoreCommand;
import doordonote.common.Util;

//@@author A0131436N

public class RestoreHandler extends CommandHandler {

	protected RestoreHandler(String commandBody) throws Exception {
		super(commandBody);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new Exception(String.format(EXCEPTION_NO_ARGUMENT, "restore"));
		}
	}

	@Override
	public Command generateCommand() throws Exception {
		int indexToRestore = getTaskIdFromString(commandBody, "restore");
		return new RestoreCommand(indexToRestore);
	}

}
