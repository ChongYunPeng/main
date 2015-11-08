package doordonote.commandfactory;

import java.util.Date;

import doordonote.command.AddCommand;
import doordonote.command.Command;

//@@author A0131436N

public class AddHandler extends AbstractAddCommandHandler {
	
	protected AddHandler(String commandBody, DateParser dateParser) throws Exception {
		super(commandBody, dateParser, "add");
	}

	@Override
	public Command generateCommand() throws Exception {
		String taskDescription = getTaskDescription();
		Date startDate = getStartDate();
		Date endDate = getEndDate();

		return new AddCommand(taskDescription, startDate, endDate);
	}

}
