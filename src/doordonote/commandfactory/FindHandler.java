package doordonote.commandfactory;

import java.util.List;

import com.joestelmach.natty.repackaged.edu.emory.mathcs.backport.java.util.Arrays;

import doordonote.command.Command;
import doordonote.command.FindCommand;

public class FindHandler extends AbstractCommandHandler {

	public FindHandler(String commmandBody) {
		super(commmandBody);
	}

	@Override
	public Command generateCommand() {
		if (commandBody.isEmpty()) {
			return new FindCommand();
		} else {
			String[] keywordsArray= commandBody.split("\\s+");
			@SuppressWarnings("unchecked")
			List<String> keywords = Arrays.asList(keywordsArray);
			return new FindCommand(keywords);
		}
	}

}
