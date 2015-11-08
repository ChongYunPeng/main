package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.HomeCommand;
import doordonote.common.Util;

//@@author A0131436N

public class HomeHandler extends CommandHandler {

	protected HomeHandler(String commmandBody) throws Exception {
		super(commmandBody);
		if (!Util.isEmptyOrNull(commmandBody)) {
			throw new Exception(String.format(EXCEPTION_EXCESS_ARGUMENTS, "home"));
		}
	}

	@Override
	public Command generateCommand() {
		return new HomeCommand();
	}

}
