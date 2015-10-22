package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.HomeCommand;
import doordonote.common.Util;

public class HomeHandler extends AbstractCommandHandler {

	public HomeHandler(String commmandBody) throws ExcessArgumentException {
		super(commmandBody);
		if (!commandBody.isEmpty()) {
			throw new ExcessArgumentException();
		}
	}

	@Override
	public Command generateCommand() {
		if (Util.isEmptyOrNull(commandBody)) {
			return new HomeCommand();
		} else {
			// throw exception
			return null;
		}
	}

}
