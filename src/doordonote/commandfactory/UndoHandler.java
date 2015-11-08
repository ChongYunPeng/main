package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.UndoCommand;
import doordonote.common.Util;

//@@author A0131436N

public class UndoHandler extends CommandHandler {

	protected UndoHandler(String commmandBody) throws Exception {
		super(commmandBody);
		if (!Util.isEmptyOrNull(commandBody)) {
			throw new Exception(String.format(EXCEPTION_EXCESS_ARGUMENTS, "undo"));
		}
	}

	@Override
	public Command generateCommand() {
		return new UndoCommand();
	}

}
