package doordonote.commandfactory;

import doordonote.command.Command;

//@@author A0131436N

public abstract class CommandHandler {
	protected String commandBody = null;
	
	protected CommandHandler(String commandBody) {
		this.commandBody = commandBody;
	}

	public static int getTaskIdFromString(String taskIdString) {
		return Integer.parseInt(taskIdString);
	}
	
	public abstract Command generateCommand() throws NumberFormatException, NegativeIndexException, ExcessArgumentException, Exception;
}
