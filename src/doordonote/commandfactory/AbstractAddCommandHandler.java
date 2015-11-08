package doordonote.commandfactory;

import java.util.Date;
import java.util.List;

import doordonote.common.Util;

//@@author A0131436N

/**
 * Parses dates from user input String. Extended by AddCommand and UpdateCommand
 * 
 * @author yunpeng
 *
 */
public abstract class AbstractAddCommandHandler extends CommandHandler {
	protected static final String EXCEPTION_STARTDATE_AFTER_ENDDATE = "End date (%1$s) must be later than Start date (%2$s)";
	protected static final String EXCEPTION_TASK_TOO_LONG = "Task description is too long! Maximum number of characters is 500";
	
	private static final int MAX_TASK_DESCRIPTION_LENGTH = 500;

	protected DateParser dateParser = null;
	protected Date startDate = null;
	protected Date endDate = null;
	protected String taskDescription = null;

	public AbstractAddCommandHandler(String commandBody, DateParser dateParser, String cmdType) throws Exception {
		super(commandBody);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new Exception(String.format(EXCEPTION_NO_ARGUMENT, cmdType));
		}
		this.dateParser = dateParser;
		initialiseParameters(commandBody);
	}

	/**
	 * Parses and sets the taskDescription, startDate and endDate field
	 * 
	 * @param commandBody
	 *            User input.
	 * @throws Exception
	 *             Throws exception if an event startDate is later than its
	 *             endDate.
	 */
	private void initialiseParameters(String commandBody) throws Exception {
		if (isProbablyEvent()) {
			setEventStartAndEndDate(commandBody);
		} else if (isProbablyDeadline()) {
			setDeadlineDate(commandBody);
		} else {
			taskDescription = commandBody;
		}
	}

	private void setDeadlineDate(String commandBody) {
		String endDateString = commandBody.substring(getDeadlineDateIndex());
		Date endDate = dateParser.parse(endDateString);

		if (endDate == null) {
			taskDescription = commandBody;
		} else {
			taskDescription = getTaskDescription(getDeadlineDateIndex());
			this.endDate = endDate;
		}
	}

	private void setEventStartAndEndDate(String commandBody) throws Exception {
		String dateString = commandBody.substring(getEventStartDateIndex());
		List<Date> dateList = dateParser.parseAndGetDateList(dateString);

		if (dateList == null || dateList.size() < 2) {
			taskDescription = commandBody;
		} else {
			taskDescription = getTaskDescription(getEventStartDateIndex());
			this.startDate = dateList.get(0);
			this.endDate = dateList.get(1);

			if (startDate.compareTo(endDate) >= 0) {
				throw new Exception(String.format(EXCEPTION_STARTDATE_AFTER_ENDDATE, Util.getDateString(endDate),
						Util.getDateString(startDate)));
			}
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

	protected String getTaskDescription() throws Exception {
		if (taskDescription.length() > MAX_TASK_DESCRIPTION_LENGTH) {
			throw new Exception(EXCEPTION_TASK_TOO_LONG);
		}
		return taskDescription;
	}
}
