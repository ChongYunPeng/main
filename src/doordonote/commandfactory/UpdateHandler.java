package doordonote.commandfactory;

import java.util.Date;

import com.joestelmach.natty.Parser;

import doordonote.command.Command;
import doordonote.command.UpdateCommand;
import doordonote.common.Util;

public class UpdateHandler extends AbstractAddCommandHandler {
	protected Parser dateParser = null;

	public UpdateHandler(String commandBody, Parser dateParser) {
		super(commandBody, dateParser);
	}

	
	// TODO: Add in handler for commands such as "update 5"
	@Override
	public Command generateCommand() {
		int indexToUpdate = getIndexToUpdate();
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
		return new UpdateCommand(indexToUpdate, taskDescription, startDate, endDate);
	}
	
	@Override
	protected String getTaskDescription() {
		return Util.removeFirstWord(super.getTaskDescription());		
	}
	
	protected int getIndexToUpdate() {
		try {
			int indexToUpdate = Integer.parseInt(Util.getFirstWord(commandBody));
			if (indexToUpdate <= 0) {
				// throw exception
				return -1;
			} else {
				return indexToUpdate;
			}
		} catch (NumberFormatException e) {
			return -1;
		}		
	}
	

}
