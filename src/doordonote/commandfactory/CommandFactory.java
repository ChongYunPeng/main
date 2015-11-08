package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.common.Util;

//@@author A0131436N

public class CommandFactory {
	DateParser dateParser;

	public CommandFactory() {
		dateParser = new DateParser();
	}

	public Command parse(String userInput) throws Exception {

		String trimmedInput = userInput.trim();
		String commandType = getCommandType(trimmedInput);
		String commandBody = Util.removeFirstWord(trimmedInput);
		CommandHandler handler = null;

		switch (commandType) {

		case "add" :
			handler = new AddHandler(commandBody, dateParser);
			break;

		case "delete" :
			// fallthrough
		case "del":
			handler = new DeleteHandler(commandBody);
			break;

		case "update" :
			handler = new UpdateHandler(commandBody, dateParser);
			break;

		case "undo" :
			handler = new UndoHandler(commandBody);
			break;

		case "redo" :
			handler = new RedoHandler(commandBody);
			break;

		case "find" :
			handler = new FindHandler(commandBody, dateParser);
			break;

		case "finish" :
			// fallthrough
		case "fin" :
			handler = new FinishHandler(commandBody);
			break;

		case "help" :
			handler = new HelpHandler(commandBody);
			break;
		case "home" :
			handler = new HomeHandler(commandBody);
			break;
		case "restore" :
			handler = new RestoreHandler(commandBody);
			break;
		case "view" :
			handler = new ViewHandler(commandBody);
			break;
		 case "readfrom" :
			 handler = new ReadPathHandler(commandBody);
			 break;
		 case "save" :
			 handler = new SetPathHandler(commandBody);
			 break;
		default:
			// treats user input as adding a new task by default if input does not match any command type
			handler = new AddHandler(trimmedInput, dateParser);
		}

		return handler.generateCommand();
	}

	protected static String getCommandType(String userCommand) {
		assert(userCommand != null);
		String commandType = Util.getFirstWord(userCommand).toLowerCase();
		return commandType;
	}


}
