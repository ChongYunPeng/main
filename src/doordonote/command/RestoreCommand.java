package doordonote.command;

import doordonote.logic.CommandToController;

//@@author A0131436N

/**
 * @author yunpeng
 *
 */
public class RestoreCommand implements Command {
	protected int indexToRestore = -1;

	/**
	 * @param 	indexToRestore
	 * 			Task ID to be restored.
	 * 		 	Task restored is treated as undeleted and unfinished
	 */
	public RestoreCommand(int indexToRestore) {
		assert(indexToRestore > 0);
		this.indexToRestore = indexToRestore;
	}

	@Override
	public String execute(CommandToController controller) throws Exception {
		return controller.restore(indexToRestore);
	}

}
