package doordonote.commandfactory;

import doordonote.command.Command;

public abstract class AbstractCommandHandler {
	protected String commandBody = null;
	
	public AbstractCommandHandler(String commandBody) {
		this.commandBody = commandBody;
	}

	public abstract Command generateCommand() throws NumberFormatException, NegativeIndexException;
}
