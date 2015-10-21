package doordonote.commandfactory;

import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import doordonote.command.AddCommand;
import doordonote.command.Command;

// TODO: Change name to make it more understandable
// TODO: Make parser static
public abstract class AbstractAddCommandHandler extends AbstractCommandHandler {
	protected static final int NUM_OF_DATES_IN_EVENTS = 2;
	protected Parser dateParser = null;
	
	public AbstractAddCommandHandler(String commandBody, Parser dateParser) {
		super(commandBody);
		this.dateParser = dateParser;
	}
	
	protected List<Date> getDateList(int indexOfTimePeriod) {		
		if (indexOfTimePeriod < 0) {
			return null;
		}
		String eventPeriodString = commandBody.substring(indexOfTimePeriod);
		List<DateGroup> dateGroupList = dateParser.parse(eventPeriodString);
		if (dateGroupList.isEmpty()) {
			return null;
		} else {
			return dateGroupList.get(0).getDates();
		}
	}
	
	protected int getEventDateIndex() {
		return commandBody.toLowerCase().lastIndexOf("from");
	}
	
	protected int getDeadlineDateIndex() {
		return commandBody.toLowerCase().lastIndexOf("by");
	}
	
	protected boolean isEvent() {
		int indexOfEventDate = getEventDateIndex();
		int indexOfDeadlineDate = getDeadlineDateIndex();
		if (indexOfEventDate > indexOfDeadlineDate) {
			Date startDate = getEventStartDate();
			Date endDate = getEventEndDate();
			return (startDate != null && endDate != null);
		} else {
			return false;
		}
	}
	
	protected boolean isDeadline() {
		int indexOfEventDate = getEventDateIndex();
		int indexOfDeadlineDate = getDeadlineDateIndex();
		if (indexOfDeadlineDate > indexOfEventDate) {
			Date endDate = getDeadlineDate();
			return endDate != null;
		} else {
			return false;
		}
	}
	
	protected Date getEventStartDate() {
		int eventDateIndex = getEventDateIndex();
		List<Date> datesList = getDateList(eventDateIndex);

		if (isEvent() && datesList != null && datesList.size() == 2) {
			return datesList.get(0);
		} else {
			return null;
		}
	}
	
	protected Date getEventEndDate() {
		int eventDateIndex = getEventDateIndex();
		List<Date> datesList = getDateList(eventDateIndex);

		if (isEvent() && datesList != null && datesList.size() == 2) {
			return datesList.get(1);
		} else {
			return null;
		}
	}
	
	protected Date getDeadlineDate() {
		int deadlineDateIndex = getDeadlineDateIndex();
		List<Date> datesList = getDateList(deadlineDateIndex);

		if (!isEvent() && datesList != null && !datesList.isEmpty()) {
			return datesList.get(0);
		} else {
			return null;
		}
	}
	
	
	protected String getTaskDescription() {
		if (isEvent()) {
			return commandBody.substring(0, getEventDateIndex());
		} else if (isDeadline()) {
			return commandBody.substring(0, getDeadlineDateIndex());
		} else {
			return commandBody;
		}
	}
	

}
