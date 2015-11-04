//@@author A0131436N

package doordonote.command;

import java.io.IOException;
import java.util.List;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 *
 */
public class FindCommand implements Command {
	protected List<String> keywords = null;
	
	/**
	 * @param 	keywords
	 * 		  	Filters and displays only tasks that contains (not case sensitive) 
	 * 			every String in this {@code List}.
	 */
	public FindCommand(List<String> keywords) {
		this.keywords = keywords;
	}

	@Override
	public String execute(CommandToController controller) throws IOException {
		return controller.find(keywords);
	}

}
