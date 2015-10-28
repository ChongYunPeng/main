package doordonote.commandfactory;

import java.util.Date;

import doordonote.common.Util;

// TODO: Change name to make it more understandable
// TODO: Make parser static
public abstract class AbstractAddCommandHandler extends CommandHandler {

	protected DateParser dateParser = null;
	protected Date startDate = null;
	protected Date endDate = null;
	protected String taskDescription = null;
	
	public AbstractAddCommandHandler(String commandBody, DateParser dateParser) throws EmptyCommandBodyException {
		super(commandBody);
		if (Util.isBlankString(commandBody)) {
			throw new EmptyCommandBodyException();
		}
		this.dateParser = dateParser;			
		initialiseParameters(commandBody);
	}

	private void initialiseParameters(String commandBody) {
		if (isProbablyEvent()) {
			try {
				String startDateString = commandBody.substring(getEventStartDateIndex(), getEventEndDateIndex());
				String endDateString = commandBody.substring(getEventEndDateIndex());
				Date startDate = dateParser.parse(startDateString);
				Date endDate = dateParser.parse(endDateString);
				
				if (startDate == null || endDate == null) {
					taskDescription = commandBody;
				} else {
					taskDescription = getTaskDescription(getEventStartDateIndex());
					this.startDate = startDate;
					this.endDate = endDate;
				}
			} catch (Exception e) {
				taskDescription = commandBody;
			}
		} else if (isProbablyDeadline()) {
			try {
				String endDateString = commandBody.substring(getDeadlineDateIndex());
				Date endDate = dateParser.parse(endDateString);
				
				if (endDate == null) {
					taskDescription = commandBody;
				} else {
					taskDescription = getTaskDescription(getDeadlineDateIndex());
					this.endDate = endDate;
				}
			} catch (Exception e) {
				taskDescription = commandBody;
			}
		} else {
			taskDescription = commandBody;
		}
	}
	
	protected Date getStartDate() {
		return startDate;
	}
	
	protected Date getEndDate() {
		return endDate;
	}
	
	private boolean isProbablyEvent() {
		return getEventStartDateIndex() > getDeadlineDateIndex();
	}
	
	private boolean isProbablyDeadline() {
		return getEventStartDateIndex() < getDeadlineDateIndex();
	}
	
	private int getEventStartDateIndex() {
		return commandBody.toLowerCase().lastIndexOf("from");
	}
	
	private int getEventEndDateIndex() {
		return commandBody.toLowerCase().lastIndexOf(" to ");
	}
	
	private int getDeadlineDateIndex() {
		return commandBody.toLowerCase().lastIndexOf(" by ");
	}
	
	private String getTaskDescription(int endIndex) {
		return commandBody.substring(0, endIndex);
	}
	
	protected String getTaskDescription() {
		return taskDescription;
	}
}
