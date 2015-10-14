package doordonote.commandfactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import doordonote.command.Command;
import doordonote.storage.Storage;

public class CommandManager {
	
	protected Storage storageObj = null;
	protected Stack<Command> commandStack = null;
	
	public CommandManager(Storage storageObj) {
		this.storageObj = storageObj;
		this.commandStack = new Stack<Command>();
	}
	
	public Command parse(String userInput) {

		String commandType = getCommandType(userInput);
		String commandBody = removeFirstWord(userInput);

		switch (commandType) {

		case "add":

//			return parseAdd(commandBody);
//
//		case "delete":
//
//			return parseDelete(commandBody);
//
//		case "update":
//
//			return parseUpdate(commandBody);

		default:

			// Invalid user input
			// Throw exception
			return null;

		}
	}

//	protected Command parseAdd(String userInput) {
//
//		String commandBody = removeFirstWord(userInput);
//		String[] parameters = commandBody.split(" ");
//		Command commandAddObj = null;
//		boolean haveEvents = false;
//		boolean haveDeadlines = false;
//		for (int i = 0; i < parameters.length; i++) {
//			if (parameters[i].toLowerCase().equals("from")) {
//				haveEvents = true;
//				String[] parameters2 = commandBody.split("from ");
//				String task = parameters2[0];
//
//				String[] time = parameters2[1].split("to ");
//				Date startDate = parseDate(time[0]);
//				Date endDate = parseDate(time[1]);
//
//				commandAddObj = new AddCommand(task, startDate, endDate);
//				return commandAddObj;
//			}
//		}
//		if(haveEvents == false) {
//			for (int i = 0; i < parameters.length; i++) {
//				if (parameters[i].toLowerCase().equals("by")) {
//					haveDeadlines = true;
//					String[] parameters2 = commandBody.split("by ");
//				        String task = parameters2[0];
//				        Date endDate = parseDate(parameters2[1]);
//
//				        commandAddObj = new AddCommand(task, endDate);
//				        return commandAddObj;
//			        }
//		        }
//		}
//		if(haveEvents == false && haveDeadlines == false) {
//			String task = commandBody;
//		        commandAddObj = new AddCommand(task);
//
//		        return commandAddObj;
//		}
//	}
//
//	private static Command parseDelete(String command) {
//
//		String taskIndex = removeFirstWord(command);
//		Command commandDeleteObj = new DeleteCommand(Integer.parseInt(taskIndex));
//		return commandDeleteObj;
//
//	}
//
//	private static Command parseUpdate(String command) {
//
//		String commandBody = removeFirstWord(command);
//		String[] parameters = commandBody.split(" ");
//
//		Command commandUpdateObj = null;
//		int indexOfTaskToUpdate;
//		try {
//			indexOfTaskToUpdate = Integer.parseInt(parameters[0]);
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			return null;
//		}
//
//		commandBody = removeFirstWord(commandBody);
//		boolean haveEvents = false;
//		boolean haveDeadlines = false;
//		for (int i = 1; i < parameters.length; i++) {
//			if (parameters[i].toLowerCase().equals("from")) {
//				haveEvents = true;
//				String[] parameters2 = commandBody.split("from ");
//				String task = parameters2[0];
//
//				String[] time = parameters2[1].split("to ");
//				Date startDate = parseDate(time[0]);
//				Date endDate = parseDate(time[1]);
//
//				commandUpdateObj = new UpdateCommand(indexOfTaskToUpdate, task, startDate, endDate);
//				return commandUpdateObj;
//			}
//		}
//                if(haveEvents == false) {
//			for (int i = 0; i < parameters.length; i++) {
//				if (parameters[i].toLowerCase().equals("by")) {
//					haveDeadlines = true;
//					String[] parameters2 = commandBody.split("by ");
//				        String task = parameters2[0];
//				        Date endDate = parseDate(parameters2[1]);
//
//				        commandUpdateObj = new UpdateCommand(indexOfTaskToUpdate, task, endDate);
//				        return commandUpdateObj;
//			        }
//		        }
//                }
//                if(haveEvents == false && haveDeadlines == false) {
//		        String task = commandBody;
//		        commandUpdateObj = new UpdateCommand(indexOfTaskToUpdate, task);
//
//		        return commandUpdateObj;
//                } 
//	}
//
//	private static Date parseDate(String dateInString) {
//
//		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM hh:mm");
//		Date date = null;
//
//		try {
//			date = formatter.parse(dateInString);
//		} catch (ParseException e) {
//			;
//		}
//
//		return date;
//
//	}

	protected String getCommandType(String command) {

		String commandType = getFirstWord(command).toLowerCase();
		return commandType;

	}

	protected String getFirstWord(String command) {

		String firstWord = command.trim().split("\\s+")[0];
		return firstWord;

	}

	protected String removeFirstWord(String command) {

		return command.replace(getFirstWord(command), "").trim();

	}
	
	
}