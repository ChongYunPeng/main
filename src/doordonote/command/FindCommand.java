package doordonote.command;

import java.io.IOException;
import java.util.List;

import doordonote.logic.CommandToController;

//@@author A0131436N

/**
 * @author yunpeng
 *
 */
public class FindCommand implements Command {
	protected List<String> keywords = null;
	
	/**
	 * @param 	keywords
	 * 		  	Filters user task list by only tasks that contains (not case sensitive) 
	 * 			words in {@code keywords}.
	 */
	public FindCommand(List<String> keywords) {
		assert(keywords != null);
		this.keywords = keywords;
	}

	@Override
	public String execute(CommandToController controller) throws IOException {
		return controller.find(keywords);
	}

}
