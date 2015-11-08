package doordonote.command;

import java.io.IOException;
import java.util.Date;

import doordonote.logic.CommandToController;

//@@author A0131436N

/**
 * @author yunpeng
 *
 */
public class FindDateCommand implements Command {
	protected Date startDate = null;
	
	/**
	 * @param 	startDate
	 * 			Filters user task list by only tasks that end after {@code startDate}.
	 * 			Floating task should not be displayed
	 */
	public FindDateCommand(Date startDate) {
		assert(startDate != null);
		this.startDate = startDate;
	}

	@Override
	public String execute(CommandToController controller) throws IOException, Exception {
		return controller.find(startDate);
	}
}
