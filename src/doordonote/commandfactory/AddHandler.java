package doordonote.commandfactory;

import java.util.Date;
import com.joestelmach.natty.Parser;

import doordonote.command.AddCommand;
import doordonote.command.Command;

public class AddHandler extends AbstractAddCommandHandler {	
	public AddHandler(String commandBody, Parser dateParser) {
		super(commandBody, dateParser);
	}

	
	// TODO: Write cleaner code, throw exception when handling null commandBody, stricter rules for parsing dates
	@Override
	public Command generateCommand() {
		String taskDescription = getTaskDescription();
		Date startDate = null;
		Date endDate = null;
		if (taskDescription == null || taskDescription.isEmpty()) {
			return null;
		} else if (isMostLikelyEvent()) {
			startDate = getEventStartDate();
			endDate = getEventEndDate();
		}  else {
			startDate = null;
			endDate = getDeadlineDate();
		}
		return new AddCommand(taskDescription, startDate, endDate);
	}
	
	
//	public static void main(String[] args) {
//		AddHandler addHandler = new AddHandler("do wok from monday to thurs", new Parser());
//		Command cmd = addHandler.generateCommand();
//		System.out.println("test");
//	}
	

}
