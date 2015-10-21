package doordonote.commandfactory;

import java.util.Date;
import com.joestelmach.natty.Parser;

import doordonote.command.AddCommand;
import doordonote.command.Command;

public class AddHandler extends AbstractAddCommandHandler {	
	
	public AddHandler(String commandBody, Parser dateParser) throws EmptyCommandBodyException {
		super(commandBody, dateParser);
		if (commandBody.isEmpty()) {
			throw new EmptyCommandBodyException();
		}
	}

	@Override
	public Command generateCommand() {
		String taskDescription = getTaskDescription();
		Date startDate = null;
		Date endDate = null;
		if (isEvent()) {
			startDate = getEventStartDate();
			endDate = getEventEndDate();
			
			// Make sure that both dates are defined
			assert(startDate != null && endDate != null);
		} else if (isDeadline()) {
			endDate = getDeadlineDate();
			assert(endDate != null);
		} 
		
		return new AddCommand(taskDescription, startDate, endDate);
	}

}
