package doordonote.commandfactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import doordonote.command.Command;
import doordonote.command.FindCommand;
import doordonote.command.FindDateCommand;
import doordonote.common.Util;

//@@author A0131436N

/**
 * Generates command to filter user task list based on either Date or a list of keywords
 * 
 * @author yunpeng
 *
 */
public class FindHandler extends CommandHandler {
	protected DateParser dateParser = null; 
	
	protected FindHandler(String commmandBody, DateParser parser) throws Exception {
		super(commmandBody);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new Exception(String.format(EXCEPTION_NO_ARGUMENT, "find"));
		}
		dateParser = parser;			
	}

	@Override
	public Command generateCommand() {
		String firstWord = Util.getFirstWord(commandBody).toLowerCase();
		if (firstWord.equals("from")) {
			Date defaultTime = setDateToTodayMidnight();
			Date startDate = dateParser.parse(commandBody, defaultTime);
			if (startDate != null) {
				return new FindDateCommand(startDate);
			}
		}
		
		String[] keywordsArray= commandBody.split("\\s+");
		List<String> keywords = Arrays.asList(keywordsArray);
		return new FindCommand(keywords);
	}

	private Date setDateToTodayMidnight() {
		DateTime midnightToday = new DateTime().withTimeAtStartOfDay();
		Date defaultTime = midnightToday.toDate();
		return defaultTime;
	}

}
