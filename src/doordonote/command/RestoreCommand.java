package doordonote.command;

import doordonote.logic.CommandToController;

public class RestoreCommand implements Command {
	protected int indexToRestore = -1;

	public RestoreCommand(int indexToRestore) {
		assert(indexToRestore > 0);
		this.indexToRestore = indexToRestore;
	}

	@Override
	public String execute(CommandToController controller) {
		return controller.restore(indexToRestore);
	}

}
