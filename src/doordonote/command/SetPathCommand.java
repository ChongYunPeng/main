package doordonote.command;

import java.io.IOException;

import doordonote.logic.CommandToController;

//@@author A0131436N

/**
 * Command used to save a storage file in a particular path in the file system.
 * If a file of the same name already exists, simply read from the existing file. 
 *
 */
public class SetPathCommand implements Command {
	protected String pathName = null;
	
	/**
	 * @param 	path
	 * 			Location on the file system to create .json storage file.
	 * 			If file already exists, simply read from it.
	 */
	public SetPathCommand(String path) {
		pathName = path;
	}

	@Override
	public String execute(CommandToController controller) throws IOException, Exception {
		return controller.saveFileAt(pathName);
	}

}
