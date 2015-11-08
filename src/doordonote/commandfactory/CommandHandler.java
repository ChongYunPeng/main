package doordonote.commandfactory;

import doordonote.command.Command;

//@@author A0131436N

/**
 * Creates a concrete {@code Command} object. 
 * 
 * @author yunpeng
 *
 */
public abstract class CommandHandler {
	protected String commandBody = null;
	protected static final String EXCEPTION_NO_ARGUMENT = "%1$s command requires arguments. Type 'help %1$s' for more details.";
	protected static final String EXCEPTION_EXCESS_ARGUMENTS = "%1$s should not have any arguments. Type 'help %1$s' for more details.";
	protected static final String EXCEPTION_INVALID_TASK_ID_FORMAT = "%1$s expects a positive integer as Task ID. Type 'help %1$s' for more details.";
	protected static final String EXCEPTION_INVALID_TASK_ID_VALUE = "Task ID cannot be less than 1!";

	protected CommandHandler(String commandBody) {
		this.commandBody = commandBody;
	}

	public static int getTaskIdFromString(String taskIdString, String cmdType) throws Exception {
		int taskId;
		try {
			taskId = Integer.parseInt(taskIdString);
		} catch (NumberFormatException e) {
			throw new Exception(String.format(EXCEPTION_INVALID_TASK_ID_FORMAT, cmdType));
		}
		
		if (taskId <= 0) {
			throw new Exception(EXCEPTION_INVALID_TASK_ID_VALUE);
		}
		return taskId;
	}

	/**
	 * Generates a {@code Command} that will be executed.
	 * This method should be overridden by concrete {@code CommandHandler} class.
	 * 
	 * @return A {@code Command} that is to be executed.
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public abstract Command generateCommand() throws NumberFormatException, Exception;
}
