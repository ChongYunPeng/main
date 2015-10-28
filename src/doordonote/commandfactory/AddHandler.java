package doordonote.commandfactory;

import java.util.Date;

import doordonote.command.AddCommand;
import doordonote.command.Command;
import doordonote.common.Util;

public class AddHandler extends AbstractAddCommandHandler {	
	
	public AddHandler(String commandBody, DateParser dateParser) throws EmptyCommandBodyException {
		super(commandBody, dateParser);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new EmptyCommandBodyException();
		}
	}

	@Override
	public Command generateCommand() {
		String taskDescription = getTaskDescription();
		Date startDate = getStartDate();
		Date endDate = getEndDate();
		
		return new AddCommand(taskDescription, startDate, endDate);
	}

}
