package doordonote.command;

import java.util.Date;

import doordonote.common.Util;
import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 *
 */
public class AddCommand implements Command {
	protected String taskDescription = null;
	protected Date startDate = null;
	protected Date endDate = null;
	
	/**
	 * @param 	taskDescription 
	 * 			the description of the task to be added
	 * @param 	startDate
	 * 			The start date of the event. A null value indicates this task is not an event
	 * @param 	endDate
	 * 			The end date of the event. A null value indicates this task is not a deadline
	 */
	public AddCommand(String taskDescription, Date startDate, Date endDate) {
		// AddHandler should have checked that task is not null and not empty
		assert(taskDescription != null && !Util.isBlankString(taskDescription));
		
		this.taskDescription = taskDescription;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	@Override
	public String execute(CommandToController controller) {
		assert(controller != null);
		return controller.add(taskDescription, startDate, endDate);
	}
}
