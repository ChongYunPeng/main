import java.util.Date;
import java.util.ArrayList;
import java.io.IOException;

public class TaskFileIO implements FileIO {

	private final String MESSAGE_ADD = "Task %1$s added";
	private final String MESSAGE_UPDATE = "Task updated to %1$s";
	private final String MESSAGE_DELETE = "Task deleted";
	private final String MESSAGE_NO_TASK_TO_DELETE = "No Tasks to delete";
	private final String MESSAGE_NO_TASK_TO_UPDATE = "No Tasks to update";

	protected JsonFileIO jsonFileIO;
	private static TaskFileIO taskStorage;

	private TaskFileIO(){
		jsonFileIO = new JsonFileIO();
	}

	private TaskFileIO(String fileName){
		jsonFileIO = new JsonFileIO(fileName);
	}

	public static FileIO getInstance(){
		if(taskStorage == null){
			taskStorage = new TaskFileIO();
		}
		return taskStorage;
	}
	
	
	public String add(Task task){
		jsonFileIO.write(task);
		return String.format(MESSAGE_ADD, task);
	}

	public String add(String description, Date startDate, Date endDate) {
		Task task = createTask(description, startDate, endDate);
		return add(task);
	}


	public String update(Task taskToUpdate, String description, Date startDate, Date endDate) {
		Task updatedTask = createTask(description, startDate, endDate);
		return update(taskToUpdate, updatedTask);
	}
	
	public String update(Task taskToUpdate, Task updatedTask){
		try{
			jsonFileIO.update(taskToUpdate, updatedTask);
			return String.format(MESSAGE_UPDATE, updatedTask);
		}
		catch(EmptyTaskListException e){
			return MESSAGE_NO_TASK_TO_UPDATE;
		}
	}

	public String delete(Task taskToDelete){
		try{
			jsonFileIO.delete(taskToDelete);
			return MESSAGE_DELETE;
		}
		catch(EmptyTaskListException e){
			return MESSAGE_NO_TASK_TO_DELETE;
		}
	}
	
	public ArrayList<Task> read() throws IOException{
		ArrayList<Task> listTask = null;
		try{
			listTask = jsonFileIO.read();
		}
		catch (IOException e){
			throw e;
		}		
		assert(listTask!=null);
		return listTask;
	}

	private Task createTask(String description, Date startDate,
			Date endDate){
		Task task = null;

		if(description!=null && startDate==null && endDate==null){
			task = new FloatingTask(description);
		} else if(description!=null && startDate==null && endDate!=null){
			task = new DeadlineTask(description, endDate);
		} else if(description!=null && startDate!=null && endDate!=null){
			task = new EventTask(description, startDate, endDate);
		} else {
			assert (task != null):"Invalid Task parameters";
		}

		return task;
	}

}
