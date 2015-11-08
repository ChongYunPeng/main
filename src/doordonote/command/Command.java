package doordonote.command;

import java.io.IOException;

import doordonote.logic.CommandToController;

//@@author A0131436N

/**
 * Interface where new concrete Command implementations should inherit from.
 * 
 */
public interface Command {
	/**
	 * Executes this command
	 * 
	 * @param 	controller
	 * 			methods in the controller will be called
	 * @return 	feedback that gives information about the outcome of the execution of the command
	 * @throws 	IOException 
	 * @throws 	Exception 
	 */
	String execute(CommandToController controller) throws IOException, Exception;
}
