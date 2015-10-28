package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.RedoCommand;
import doordonote.common.Util;

public class RedoHandler extends AbstractCommandHandler {

	public RedoHandler(String commmandBody) throws ExcessArgumentException {
		super(commmandBody);
		if (!Util.isEmptyOrNull(commandBody)) {
			throw new ExcessArgumentException();
		}
	}

	@Override
	public Command generateCommand() {
		return new RedoCommand();
	}

}
