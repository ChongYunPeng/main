package doordonote.command;

import doordonote.logic.CommandToController;

public class HomeCommand implements Command{
	@Override
	public String execute(CommandToController controller) {
		assert(controller != null);
		return controller.home();
	}
}
