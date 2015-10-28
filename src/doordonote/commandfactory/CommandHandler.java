package doordonote.commandfactory;

import doordonote.command.Command;

public abstract class CommandHandler {
	protected String commandBody = null;
	
	public CommandHandler(String commandBody) {
		this.commandBody = commandBody;
	}

	public static int getTaskIdFromString(String taskIdString) {
		return Integer.parseInt(taskIdString);
	}
	
	public abstract Command generateCommand() throws NumberFormatException, NegativeIndexException, ExcessArgumentException, Exception;
}
