package doordonote.commandfactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

// TODO: Change name to make it more understandable
// TODO: Make parser static
public abstract class AbstractAddCommandHandler extends AbstractCommandHandler {
	protected static final int NUM_OF_DATES_IN_EVENTS = 2;
	protected static final int NUM_OF_DATES_IN_DEADLINES = 1;

	protected Parser dateParser = null;
	protected Date eventStartDate = null;
	protected Date eventEndDate = null;
	protected Date deadlineDate = null;
	protected boolean isEvent = false;
	protected boolean isDeadline = false;	
	protected int eventDateIndex = -1;
	protected int deadlineDateIndex = -1;
	protected List<Date> dateList = null;

	public AbstractAddCommandHandler(String commandBody, Parser dateParser) {
		super(commandBody);
		this.dateParser = dateParser;
		this.eventDateIndex = getEventDateIndex();
		this.deadlineDateIndex = getDeadlineDateIndex();
		if (eventDateIndex > deadlineDateIndex) {
			dateList = getDateList(eventDateIndex);
			if (dateList.size() == NUM_OF_DATES_IN_EVENTS) {
				eventStartDate = dateList.get(0);
				eventEndDate = dateList.get(1);
				isEvent = true;
			}
		} else if (deadlineDateIndex > eventDateIndex){
			dateList = getDateList(deadlineDateIndex);
			if (dateList.size() == NUM_OF_DATES_IN_DEADLINES) {
				deadlineDate = dateList.get(0);
				isDeadline = true;
			}
		}
	}
	
	protected List<Date> getDateList(int indexOfTimePeriod) {		
		if (indexOfTimePeriod < 0) {
			return null;
		}
		String eventPeriodString = commandBody.substring(indexOfTimePeriod);
		List<DateGroup> dateGroupList = dateParser.parse(eventPeriodString);
		if (dateGroupList.isEmpty()) {
			return new ArrayList<Date>();
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
		return isEvent;
	}
	
	protected boolean isDeadline() {
		return isDeadline;
	}
	
	protected Date getEventStartDate() {
		return eventStartDate;
	}
	
	protected Date getEventEndDate() {
		return eventEndDate;
	}
	
	protected Date getDeadlineDate() {
		return deadlineDate;
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
