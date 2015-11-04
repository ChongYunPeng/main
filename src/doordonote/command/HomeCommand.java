//@@author A0131436N

package doordonote.command;

import java.io.IOException;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 * This command is used to display the default task lists.
 *
 */
public class HomeCommand implements Command{
	@Override
	public String execute(CommandToController controller) throws IOException {
		assert(controller != null);
		return controller.home();
	}
}
