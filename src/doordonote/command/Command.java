package doordonote.command;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng 
 */
public interface Command {
	/**
	 * @param 	controller
	 * 			methods in the controller will be called
	 * @return 	feedback that gives information about the outcome of the execution of the command
	 */
	String execute(CommandToController controller);
}
