package doordonote.command;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 * This command is used to display the default task lists.
 *
 */
public class HomeCommand implements Command{
	@Override
	public String execute(CommandToController controller) {
		assert(controller != null);
		return controller.home();
	}
}
