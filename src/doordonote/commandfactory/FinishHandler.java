package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.FinishCommand;
import doordonote.common.Util;

//@@author A0131436N

public class FinishHandler extends CommandHandler {

	protected FinishHandler(String commmandBody) throws Exception {
		super(commmandBody);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new Exception(String.format(EXCEPTION_NO_ARGUMENT, "finish"));
		}
	}

	@Override
	public Command generateCommand() throws Exception {
		int indexToFinish = getTaskIdFromString(commandBody, "finish");
		return new FinishCommand(indexToFinish);
	}

}
