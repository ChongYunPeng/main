//@@author A0131436N

package doordonote.command;

import java.util.Date;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 */
public class UpdateCommand implements Command {
	protected int taskID = -1;
	protected String taskDescription = null;
	protected Date startDate = null;
	protected Date endDate = null;
	
	/**
	 * @param 	taskID
	 * 			Task ID to be updated. This task will be deleted and replaced by a new Task
	 * @param 	taskDescription
	 * 			Task description that will replace the old task
	 * @param 	startDate
	 * @param 	endDate
	 */
	public UpdateCommand(int taskID, String taskDescription, Date startDate, Date endDate) {
		this(taskID);
		// UpdateHandler should have checked that taskDescription is not null and not empty
		assert(taskDescription != null && !taskDescription.isEmpty()); 	
		
		this.taskDescription = taskDescription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.taskID = taskID;
	}
	
	public UpdateCommand(int taskID) {
		// UpdateHandler should have checked that taskID > 0
		assert(taskID > 0); 
		this.taskID = taskID;
		this.taskDescription = null;
	}

	@Override
	public String execute(CommandToController controller) throws Exception {
		if (taskDescription == null) {
			return controller.getTaskById(this.taskID);
		}
		return controller.update(taskID, taskDescription, startDate, endDate);
	}
	



}
