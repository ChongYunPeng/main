package doordonote.commandfactory;

import java.util.Date;

import doordonote.command.Command;
import doordonote.command.UpdateCommand;
import doordonote.common.Util;

//@@author A0131436N

public class UpdateHandler extends AbstractAddCommandHandler {
	protected UpdateHandler(String commandBody, DateParser dateParser) throws Exception {
		super(commandBody, dateParser, "update");
	}

		@Override
	public Command generateCommand() throws Exception {
		int indexToUpdate = getIndexToUpdate();
		String taskDescription = getTaskDescription();
		Date startDate = getStartDate();
		Date endDate = getEndDate();
		if (Util.isBlankString(taskDescription)) {
			return new UpdateCommand(indexToUpdate);
		} else {			
			return new UpdateCommand(indexToUpdate, taskDescription, startDate, endDate);
		}
	}
	
	@Override
	protected String getTaskDescription() throws Exception {
		return Util.removeFirstWord(super.getTaskDescription());		
	}
	
	protected int getIndexToUpdate() throws Exception {
		String taskIdString = Util.getFirstWord(commandBody);
		int indexToUpdate = getTaskIdFromString(taskIdString, "update");
		return indexToUpdate;
	}
	

}
