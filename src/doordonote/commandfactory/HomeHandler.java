package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.HomeCommand;
import doordonote.common.Util;

public class HomeHandler extends CommandHandler {

	public HomeHandler(String commmandBody) throws ExcessArgumentException {
		super(commmandBody);
		if (!commandBody.isEmpty()) {
			throw new ExcessArgumentException();
		}
	}

	@Override
	public Command generateCommand() throws ExcessArgumentException {
		if (Util.isEmptyOrNull(commandBody)) {
			return new HomeCommand();
		} else {
			// this command should not have any arguments
			throw new ExcessArgumentException();
		}
	}

}
