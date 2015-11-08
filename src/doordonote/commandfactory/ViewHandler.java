package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.ViewDeletedTaskCommand;
import doordonote.command.ViewFinishedTaskCommand;
import doordonote.common.Util;

//@@author A0131436N

public class ViewHandler extends CommandHandler {
	protected static final String EXCEPTION_INVALID_VIEW_ARGUMENT = "Do you mean 'view deleted' or 'view finished'?";

	protected ViewHandler(String commandBody) throws Exception {
		super(commandBody);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new Exception(String.format(EXCEPTION_NO_ARGUMENT, "view"));
		}
		this.commandBody = commandBody.trim().toLowerCase();
	}

	@Override
	public Command generateCommand() throws Exception {
		if (commandBody.contains("del")) {
			return new ViewDeletedTaskCommand();
		} else if (commandBody.contains("fin")) {
			return new ViewFinishedTaskCommand();
		} else {
			throw new Exception(EXCEPTION_INVALID_VIEW_ARGUMENT);
		}
	}

}
