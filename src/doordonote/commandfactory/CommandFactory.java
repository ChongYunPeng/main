package doordonote.commandfactory;

import com.joestelmach.natty.Parser;

import doordonote.command.*;
import doordonote.common.Util;

public class CommandFactory {
	private static final String INVALID_COMMAND = " is not a valid command type!";

	Parser dateParser;

	public CommandFactory() {
		dateParser = new Parser();
	}

	public Command parse(String userInput) throws InvalidCommandException, EmptyCommandBodyException,
			ExcessArgumentException, NumberFormatException, NegativeIndexException {

		String commandType = getCommandType(userInput);
		String commandBody = Util.removeFirstWord(userInput);
		AbstractCommandHandler handler = null;

		switch (commandType) {

		case "add":
			// TODO addm and addh
			handler = new AddHandler(commandBody, dateParser);
			break;

		case "delete":
			handler = new DeleteHandler(commandBody);
			break;

		case "update":
			handler = new UpdateHandler(commandBody, dateParser);
			break;

		case "undo":
			handler = new UndoHandler(commandBody);
			break;

		case "redo":
			handler = new RedoHandler(commandBody);
			break;

		case "find":
			handler = new FindHandler(commandBody);
			break;

		case "finish":
			handler = new FinishHandler(commandBody);
			break;

		case "help":
			handler = new HelpHandler(commandBody);
			break;
		case "home":
			handler = new HomeHandler(commandBody);
			break;
		// case "get" :
		// case "restore" :
		// case "path" :

		default:
			throw new InvalidCommandException("Invalid command format. " + "\'" + commandType + "\'" + INVALID_COMMAND);
		}

		return handler.generateCommand();
	}

	private static String getCommandType(String userCommand) {
		String commandType = Util.getFirstWord(userCommand).toLowerCase();
		return commandType;
	}
	//
	// private static Command parseAdd(String command) {
	//
	// String commandBody = removeFirstWord(command);
	// String[] parameters = commandBody.split(" ");
	// Command commandAddObj = null;
	// boolean haveEvents = false;
	// boolean haveDeadlines = false;
	// for (int i = 0; i < parameters.length; i++) {
	// if (parameters[i].toLowerCase().equals("from")) {
	// haveEvents = true;
	// String[] parameters2 = commandBody.split("from ");
	// String task = parameters2[0];
	//
	// String[] time = parameters2[1].split("to ");
	// Date startDate = parseDate(time[0]);
	// Date endDate = parseDate(time[1]);
	//
	// commandAddObj = new AddCommand(task, startDate, endDate);
	// return commandAddObj;
	// }
	// }
	// if(haveEvents == false) {
	// for (int i = 0; i < parameters.length; i++) {
	// if (parameters[i].toLowerCase().equals("by")) {
	// haveDeadlines = true;
	// String[] parameters2 = commandBody.split("by ");
	// String task = parameters2[0];
	// Date endDate = parseDate(parameters2[1]);
	//
	// commandAddObj = new AddCommand(task, endDate);
	// return commandAddObj;
	// }
	// }
	// }
	// if(haveEvents == false && haveDeadlines == false) {
	// String task = commandBody;
	// commandAddObj = new AddCommand(task);
	//
	// return commandAddObj;
	// }
	// }
	//
	// private static Command parseDelete(String command) {
	//
	// String taskIndex = removeFirstWord(command);
	// Command commandDeleteObj = new
	// DeleteCommand(Integer.parseInt(taskIndex));
	// return commandDeleteObj;
	//
	// }
	//
	// private static Command parseUpdate(String command) {
	//
	// String commandBody = removeFirstWord(command);
	// String[] parameters = commandBody.split(" ");
	//
	// Command commandUpdateObj = null;
	// int indexOfTaskToUpdate;
	// try {
	// indexOfTaskToUpdate = Integer.parseInt(parameters[0]);
	// } catch (NumberFormatException e) {
	// // TODO Auto-generated catch block
	// return null;
	// }
	//
	// commandBody = removeFirstWord(commandBody);
	// boolean haveEvents = false;
	// boolean haveDeadlines = false;
	// for (int i = 1; i < parameters.length; i++) {
	// if (parameters[i].toLowerCase().equals("from")) {
	// haveEvents = true;
	// String[] parameters2 = commandBody.split("from ");
	// String task = parameters2[0];
	//
	// String[] time = parameters2[1].split("to ");
	// Date startDate = parseDate(time[0]);
	// Date endDate = parseDate(time[1]);
	//
	// commandUpdateObj = new UpdateCommand(indexOfTaskToUpdate, task,
	// startDate, endDate);
	// return commandUpdateObj;
	// }
	// }
	// if(haveEvents == false) {
	// for (int i = 0; i < parameters.length; i++) {
	// if (parameters[i].toLowerCase().equals("by")) {
	// haveDeadlines = true;
	// String[] parameters2 = commandBody.split("by ");
	// String task = parameters2[0];
	// Date endDate = parseDate(parameters2[1]);
	//
	// commandUpdateObj = new UpdateCommand(indexOfTaskToUpdate, task, endDate);
	// return commandUpdateObj;
	// }
	// }
	// }
	// if(haveEvents == false && haveDeadlines == false) {
	// String task = commandBody;
	// commandUpdateObj = new UpdateCommand(indexOfTaskToUpdate, task);
	//
	// return commandUpdateObj;
	// }
	// }
	//
	// private static Date parseDate(String dateInString) {
	//
	// SimpleDateFormat formatter = new SimpleDateFormat("dd/MM hh:mm");
	// Date date = null;
	//
	// try {
	// date = formatter.parse(dateInString);
	// } catch (ParseException e) {
	// ;
	// }
	//
	// return date;
	//
	// }

}
