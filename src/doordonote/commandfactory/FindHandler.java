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

public class FindHandler extends CommandHandler {
	protected DateParser dateParser = null; 
	
	protected FindHandler(String commmandBody, DateParser parser) throws EmptyCommandBodyException {
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

			Date startDate = dateParser.parse(commandBody, defaultTime);
			if (startDate != null) {
				return new FindDateCommand(startDate);
			}
		}
		
		String[] keywordsArray= commandBody.split("\\s+");
		List<String> keywords = Arrays.asList(keywordsArray);
		return new FindCommand(keywords);
	}

}
