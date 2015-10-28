package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.HelpCommand;
import doordonote.common.Util;

public class HelpHandler extends CommandHandler {

	public HelpHandler(String commmandBody) {
		super(commmandBody);
	}

	@Override
	public Command generateCommand() {
		if (Util.isEmptyOrNull(commandBody)) {
			return new HelpCommand();
		} else {
			return new HelpCommand(commandBody);
		}
	}

}
