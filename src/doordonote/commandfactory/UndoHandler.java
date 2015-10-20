package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.UndoCommand;
import doordonote.common.Util;

public class UndoHandler extends AbstractCommandHandler {

	public UndoHandler(String commmandBody) {
		super(commmandBody);
	}

	@Override
	public Command generateCommand() {
		if (Util.isEmptyOrNull(commandBody)) {
			return new UndoCommand();
		} else {
			// throw exception
			return null;
		}
	}

}
