package doordonote.command;

import java.io.IOException;
import java.util.Date;

import doordonote.logic.CommandToController;

public class FindDateCommand implements Command {
	protected Date startDate = null;
	protected Date endDate = null;
	
	public FindDateCommand(Date startDate) {
		assert(startDate != null);
	}
	
	public FindDateCommand(Date startDate, Date endDate) {
		this(startDate);
		this.endDate = endDate;
	}

	@Override
	public String execute(CommandToController controller) throws IOException, Exception {
		return controller.find(startDate, endDate);
	}
}
