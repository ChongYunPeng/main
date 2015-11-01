package doordonote.commandfactory;

import java.util.Arrays;
import java.util.List;

import doordonote.command.Command;
import doordonote.command.FindCommand;
import doordonote.command.HomeCommand;
import doordonote.common.Util;

public class FindHandler extends CommandHandler {

	public FindHandler(String commmandBody) throws EmptyCommandBodyException {
		super(commmandBody);
		if (Util.isEmptyOrNull(commandBody)) {
			throw new EmptyCommandBodyException();
		}
	}

	@Override
	public Command generateCommand() {
		if (commandBody.isEmpty()) {
			return new HomeCommand();
		} else {
			String[] keywordsArray= commandBody.split("\\s+");
//			@SuppressWarnings("unchecked")
			List<String> keywords = Arrays.asList(keywordsArray);
			return new FindCommand(keywords);
		}
	}

}
