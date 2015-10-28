package doordonote.commandfactory;

import doordonote.command.Command;
import doordonote.common.Util;

public class CommandFactory {
	DateParser dateParser;

	public CommandFactory() {
		dateParser = new DateParser();
	}

	public Command parse(String userInput) throws Exception {

		String commandType = getCommandType(userInput);
		String commandBody = Util.removeFirstWord(userInput);
		AbstractCommandHandler handler = null;

		switch (commandType) {

		case "add" :
			// TODO addm and addh
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
			handler = new FindHandler(commandBody);
			break;

		case "finish" :
			// fallthrough
		case "complete" :
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
		case "display" :
			handler = new DisplayHandler(commandBody);
			break;
		// case "get" :
		// case "path" :

		default:
			// treats user input as adding a new task by default if input does not match any command type
			handler = new AddHandler(userInput, dateParser);
		}

		return handler.generateCommand();
	}

	private static String getCommandType(String userCommand) {
		String commandType = Util.getFirstWord(userCommand).toLowerCase();
		return commandType;
	}


}
