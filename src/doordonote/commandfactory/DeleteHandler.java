package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.DeleteCommand;
import doordonote.common.Util;

//@@author A0131436N

public class DeleteHandler extends CommandHandler {
	
	protected DeleteHandler(String commmandBody) throws Exception {
		super(commmandBody);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new Exception(String.format(EXCEPTION_NO_ARGUMENT, "delete"));
		}
	}

	@Override
	public Command generateCommand() throws Exception {
		int indexToDelete = getTaskIdFromString(commandBody, "delete");
		return new DeleteCommand(indexToDelete);	
	}

}
