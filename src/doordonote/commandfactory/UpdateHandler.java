package doordonote.commandfactory;

import java.util.Date;

import doordonote.command.Command;
import doordonote.command.UpdateCommand;
import doordonote.common.Util;

//@@author A0131436N

public class UpdateHandler extends AbstractAddCommandHandler {
	protected UpdateHandler(String commandBody, DateParser dateParser) throws Exception {
		super(commandBody, dateParser);
		if (commandBody.isEmpty()) {
			throw new EmptyCommandBodyException();
		}
	}

	
	// TODO: Add in handler for commands such as "update 5"
	@Override
	public Command generateCommand() throws NumberFormatException, NegativeIndexException {
		int indexToUpdate = getIndexToUpdate();
		String taskDescription = getTaskDescription();
		Date startDate = getStartDate();
		Date endDate = getEndDate();
		if (Util.isBlankString(taskDescription)) {
			return new UpdateCommand(indexToUpdate);
		}
		return new UpdateCommand(indexToUpdate, taskDescription, startDate, endDate);
	}
	
	@Override
	protected String getTaskDescription() {
		return Util.removeFirstWord(super.getTaskDescription());		
	}
	
	protected int getIndexToUpdate() throws NumberFormatException, NegativeIndexException {
		int indexToUpdate = Integer.parseInt(Util.getFirstWord(commandBody));
		if (indexToUpdate <= 0) {
			 throw new NegativeIndexException();
		} else {
			return indexToUpdate;
		}
	}
	

}
