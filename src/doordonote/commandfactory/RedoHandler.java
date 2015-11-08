package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.RedoCommand;
import doordonote.common.Util;

//@@author A0131436N

public class RedoHandler extends CommandHandler {

	protected RedoHandler(String commmandBody) throws Exception {
		super(commmandBody);
		if (!Util.isEmptyOrNull(commandBody)) {
			throw new Exception(String.format(EXCEPTION_EXCESS_ARGUMENTS, "redo"));
		}
	}

	@Override
	public Command generateCommand() {
		return new RedoCommand();
	}

}
