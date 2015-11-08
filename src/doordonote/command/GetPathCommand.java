package doordonote.command;

import java.io.IOException;

import doordonote.logic.CommandToController;

//@@author A0131436N

/**
 * 
 * This {@code Command} is used to load a storage file from the given path 
 *
 */
public class GetPathCommand implements Command {
	protected String pathName = null;
	
	/**
	 * @param 	path
	 * 			File path of the .json storage file in the file system.
	 * 			File path can be absolute or relative to current folder.
	 */
	public GetPathCommand(String path) {
		assert(path != null);
		pathName = path;
	}

	@Override
	public String execute(CommandToController controller) throws IOException, Exception {
		return controller.getStorageFilePath(pathName);
	}

}
