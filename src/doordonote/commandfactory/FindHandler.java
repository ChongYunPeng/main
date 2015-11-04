package doordonote.commandfactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import doordonote.command.Command;
import doordonote.command.FindCommand;
import doordonote.command.FindDateCommand;
import doordonote.common.Util;

public class FindHandler extends CommandHandler {
	protected DateParser dateParser = null; 
	
	public FindHandler(String commmandBody, DateParser parser) throws EmptyCommandBodyException {
		super(commmandBody);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new EmptyCommandBodyException();
		}
		dateParser = parser;			
	}

	@Override
	public Command generateCommand() {
		String firstWord = Util.getFirstWord(commandBody);
		if (firstWord.equalsIgnoreCase("from")) {
			DateTime midnightToday = new DateTime().withTimeAtStartOfDay();
			Date defaultTime = midnightToday.toDate();

			List<Date> dateList = dateParser.parseAndGetDateList(commandBody, defaultTime);
			if (!dateList.isEmpty() && dateList.size() == 1) {
				Date startDate = dateList.get(0);
				return new FindDateCommand(startDate);
			} else if (!dateList.isEmpty()) {
				Date startDate = dateList.get(0);
				Date endDate = dateList.get(1);
				return new FindDateCommand(startDate, endDate);
			}
		}
		
		String[] keywordsArray= commandBody.split("\\s+");
		List<String> keywords = Arrays.asList(keywordsArray);
		return new FindCommand(keywords);
	}

}
