package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.command.DeleteCommand;
import doordonote.common.Util;

public class DeleteHandler extends AbstractCommandHandler {
	
	private static final int MAX_NUM_ARG = 1;


	public DeleteHandler(String commmandBody) throws ExcessArgumentException {
		super(commmandBody);
		if (commandBody.split("\\s+").length > MAX_NUM_ARG) {
			throw new ExcessArgumentException();
		}
	}

	@Override
	public Command generateCommand() throws NumberFormatException, NegativeIndexException {
		int indexToDelete = Integer.parseInt(Util.getFirstWord(commandBody));
		if (indexToDelete <= 0) {
			throw new NegativeIndexException();
		} else {
			return new DeleteCommand(indexToDelete);
		}	
	}

}
