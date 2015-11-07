package doordonote.commandfactory;

import java.util.Date;
import java.util.List;

import doordonote.common.Util;

//@@author A0131436N

// TODO: Change name to make it more understandable
// TODO: Make parser static
public abstract class AbstractAddCommandHandler extends CommandHandler {

	protected DateParser dateParser = null;
	protected Date startDate = null;
	protected Date endDate = null;
	protected String taskDescription = null;
	
	public AbstractAddCommandHandler(String commandBody, DateParser dateParser) throws Exception {
		super(commandBody);
		if (Util.isBlankString(commandBody)) {
			throw new EmptyCommandBodyException();
		}
		this.dateParser = dateParser;			
		initialiseParameters(commandBody);
	}

	private void initialiseParameters(String commandBody) throws Exception {
		if (isProbablyEvent()) {
			String dateString = commandBody.substring(getEventStartDateIndex());
			List<Date> dateList = dateParser.parseAndGetDateList(dateString);
			
			if (dateList == null || dateList.size() < 2) {
				taskDescription = commandBody;
			} else {
				taskDescription = getTaskDescription(getEventStartDateIndex());
				this.startDate = dateList.get(0);
				this.endDate = dateList.get(1);
				
				if (startDate.compareTo(endDate) >= 0) {
					throw new Exception("Start date cannot be later than end date");
				}
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
		return commandBody.toLowerCase().lastIndexOf(" from ");
	}

	
	private int getDeadlineDateIndex() {
		return commandBody.toLowerCase().lastIndexOf(" by ");
	}
	
	private String getTaskDescription(int endIndex) {
		return commandBody.substring(0, endIndex).trim();
	}
	
	protected String getTaskDescription() {
		return taskDescription;
	}
}
