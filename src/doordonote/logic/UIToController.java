package doordonote.logic;

import java.util.List;

import doordonote.commandfactory.EmptyCommandBodyException;
import doordonote.commandfactory.ExcessArgumentException;
import doordonote.commandfactory.InvalidCommandException;
import doordonote.commandfactory.NegativeIndexException;
import doordonote.common.Task;
import doordonote.common.TaskTest;

public interface UIToController {
	List<Task> getTasks();
	String parseAndExecuteCommand(String cmdString) throws InvalidCommandException, EmptyCommandBodyException, ExcessArgumentException, NumberFormatException, NegativeIndexException;
}
