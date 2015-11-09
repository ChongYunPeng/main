package doordonote.logic;

import java.io.IOException;
import java.util.List;

import doordonote.common.Task;

//@@author A0131436N

public interface UIToLogic {
	/** 
	 * Gets a list of Tasks filtered based on the UIState object.
	 * Tasks in the list is sorted.
	 * 
	 * @return A sorted {@code List<Task>}
	 * @throws IOException 
	 */
	List<Task> getTasks();
	
	/**
	 * @param 	userInput
	 * 			User input commands to be parsed and interpreted.
	 * @return	Feedback message for users to know what has been executed.
	 * @throws 	Exception
	 * 			Exception contains error messages to inform users that their
	 * 			input is not executed properly.
	 */
	String parseAndExecuteCommand(String userInput) throws Exception;
	
	/**
	 * 
	 * @return UIState object that will be interpreted by UI
	 */
	UIState getState();
}
