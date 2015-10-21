package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.RedoCommand;
import doordonote.common.Util;

public class RedoHandler extends AbstractCommandHandler {

	public RedoHandler(String commmandBody) throws ExcessArgumentException {
		super(commmandBody);
		if (!commandBody.isEmpty()) {
			throw new ExcessArgumentException();
		}
	}

	@Override
	public Command generateCommand() {
		if (Util.isEmptyOrNull(commandBody)) {
			return new RedoCommand();
		} else {
			// throw exception
			return null;
		}
	}

}
