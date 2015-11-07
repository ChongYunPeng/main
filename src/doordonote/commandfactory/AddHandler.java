package doordonote.commandfactory;

import java.util.Date;

import doordonote.command.AddCommand;
import doordonote.command.Command;
import doordonote.common.Util;

//@@author A0131436N

public class AddHandler extends AbstractAddCommandHandler {	
	private static final int MAX_TASK_DESCRIPTION_LENGTH = 500;
	private static final String MSG_TASK_TOO_LONG = "Task description is too long! Maximum number of characters is 500";

	protected AddHandler(String commandBody, DateParser dateParser) throws Exception {
		super(commandBody, dateParser);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new EmptyCommandBodyException();
		}
	}

	@Override
	public Command generateCommand() throws Exception {
		String taskDescription = getTaskDescription();
		if (taskDescription.length() > MAX_TASK_DESCRIPTION_LENGTH) {
			throw new Exception(MSG_TASK_TOO_LONG);
		}
		
		Date startDate = getStartDate();
		Date endDate = getEndDate();
		
		return new AddCommand(taskDescription, startDate, endDate);
	}

}
