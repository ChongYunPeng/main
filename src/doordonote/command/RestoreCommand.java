package doordonote.command;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 *
 */
public class RestoreCommand implements Command {
	protected int indexToRestore = -1;

	/**
	 * @param 	indexToRestore
	 * 		 	restores a deleted/ finished {@code Task}
	 * 		 	Task restored is marked as undeleted and unfinished
	 */
	public RestoreCommand(int indexToRestore) {
		assert(indexToRestore > 0);
		this.indexToRestore = indexToRestore;
	}

	@Override
	public String execute(CommandToController controller) {
		return controller.restore(indexToRestore);
	}

}
