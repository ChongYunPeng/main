package doordonote.command;

import java.util.ArrayList;
import java.util.List;

import doordonote.logic.CommandToController;

public class FindCommand implements Command {
	protected List<String> keywords = null;
	
	public FindCommand() {
		keywords = new ArrayList<String>();
		keywords.add("");
	}
	
	public FindCommand(List<String> keywords) {
		this.keywords = keywords;
	}

	@Override
	public String execute(CommandToController controller) {
		return controller.find(keywords);
	}

}
