package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.FinishCommand;
import doordonote.common.Util;

//@@author A0131436N

public class FinishHandler extends CommandHandler {

	protected FinishHandler(String commmandBody) throws EmptyCommandBodyException {
		super(commmandBody);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new EmptyCommandBodyException();
		}
	}

	@Override
	public Command generateCommand() throws NumberFormatException, NegativeIndexException {
		int indexToFinish = getTaskIdFromString(commandBody);
		if (indexToFinish <= 0) {
			throw new NegativeIndexException();
		} else {
			return new FinishCommand(indexToFinish);
		}	
	}

}
