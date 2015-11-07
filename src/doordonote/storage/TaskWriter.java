//@@author A0131716M
package doordonote.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.HashSet;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import doordonote.common.Task;
import doordonote.common.EventTask;



/**
 * @author A0131716M
 *
 */
public class TaskWriter {

	private static final String DEFAULT_NAME = "data.json";
	private static final String FILE_TYPE = ".json";
	private static final String INITIAL_JSONSTRING = "[]";
	private static final String SETTINGS_FILE = "settings.dodn";
	private static final String MESSAGE_DUPLICATE_DELETE = "Task %1$s exists in deleted list. Restoring from deleted list.";
	private static final String MESSAGE_DUPLICATE_DONE = "Task %1$s is marked as done. Restoring from Done list";
	private static final String MESSAGE_UPDATE_DUPLICATE = "Task %1$s already exists. Update will not be executed";
	private static final String MESSAGE_ASSERT_SET_EMPTY = "Nothing to remove!";
	private static final Gson gson = new GsonBuilder().registerTypeAdapter(Task.class, 
			new TaskClassAdapter<Task>()).create();
	private static final Type type = new TypeToken<HashSet<Task>>(){}.getType();
	private final Originator originator = new Originator();
	private final CareTaker careTaker = new CareTaker();

	private String currentJsonString = INITIAL_JSONSTRING;

	private static String currentFile;
	TaskReader reader;


	public TaskWriter(){
		initialize();
		reader = new TaskReader();
	}

	public TaskWriter(String name){
		if(!name.contains(FILE_TYPE)){
			name += FILE_TYPE;
		}
		currentFile = name;
		initialize();
		reader = new TaskReader(currentFile);
	}

	public String getFileName() {
		return currentFile;
	}

	public static void setReadFile(String fileName){
		currentFile = fileName;
	}


	public int path(String fileName){
		File file = new File(fileName);
		if(!file.exists()){
			file = new File(fileName);
			try{
				writeToSettings(fileName);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			currentFile = fileName;
			try{
				writeToFile(currentJsonString);

			}
			catch (IOException e){
				e.printStackTrace();
			}

			TaskReader.setCurrentFile(fileName);
			return 0;

		} else{
			try{
				file.createNewFile();
				writeToSettings(fileName);
				writeToFile(currentJsonString);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			currentFile = fileName;
			TaskReader.setCurrentFile(fileName);
			return 1;
		}

	}


	public String getCurrentFileString(){
		return currentJsonString;
	}

	private void initialize(){
		try {
			File settings = new File(SETTINGS_FILE);
			if(!settings.exists()){
				settings.createNewFile();
				FileWriter fw = new FileWriter(SETTINGS_FILE);
				fw.write(DEFAULT_NAME);
				fw.close();
				currentFile = DEFAULT_NAME;
			} else{
				currentFile = TaskReader.getFileString(SETTINGS_FILE).trim();
			}
			File file = new File(currentFile);
			if(!file.exists()){
				file.createNewFile();
				writeToFile(INITIAL_JSONSTRING);
			}	else{
				currentJsonString = TaskReader.getFileString(currentFile);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}



	protected void add(Task task) throws IOException, DuplicateTaskException, EventsClashException{
		String json = null;
		try{
			json = writeTask(task);
		}
		catch(DuplicateTaskException e){
			if(e.getValue()==0 || e.getValue()==1){
				toUndoStack(json);
				throw e;
			} else{
				throw e;
			}
		}
		catch(EventsClashException e){
			throw e;
		}
		if(json != null){
			toUndoStack(json);
		}
	}

	private String writeTask(Task task)throws IOException, DuplicateTaskException, EventsClashException{
		Set<Task> set = reader.jsonToSet();
		checkTaskClash(task, set);
		if(set.contains(task)){
			for(Task t : set){
				if(t.equals(task) && !t.isDeleted() && !task.isDeleted()){
					throw new DuplicateTaskException();
				} else if(t.equals(task) && t.isDeleted()){
					set.remove(task);
					task.setNotDeleted();
					addToSet(task, set);
					throw new DuplicateTaskException(String.format(MESSAGE_DUPLICATE_DELETE, task), 0);
				} else if(t.equals(task) && t.isDone()){
					set.remove(task);
					task.setNotDone();
					addToSet(task, set);
					throw new DuplicateTaskException(String.format(MESSAGE_DUPLICATE_DONE, task), 1);
				}
			}
		}
		String json = addToSet(task, set);
		return json;
	}
	
	private void checkTaskClash(Task task, Set<Task> set) throws IOException, EventsClashException{
		ArrayList<Task> arrlist = reader.readTasks();
		for(int i = 0; i < arrlist.size(); i++){
			if(arrlist.get(i) instanceof EventTask && task instanceof EventTask && checkClashCondition(arrlist.get(i), task)){
			    set = reader.jsonToSet();
				String json = addToSet(task, set);
				toUndoStack(json);
				throw new EventsClashException(arrlist.get(i), task);
			}
		}
	}

	private boolean checkClashCondition(Task originalTask, Task toCheckTask){
		if((toCheckTask.getStartDate().after(originalTask.getStartDate()) 
				&& toCheckTask.getStartDate().before(originalTask.getEndDate()) 
				|| (toCheckTask.getEndDate().after(originalTask.getStartDate()) 
						&& toCheckTask.getEndDate().before(originalTask.getEndDate())))){
			return true;
		} else{
			return false;
		}
	}


	protected void clear(){
		try{
			FileWriter fw = new FileWriter(currentFile);
			fw.write(INITIAL_JSONSTRING);
			fw.close();
			toUndoStack(INITIAL_JSONSTRING);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	protected void delete(Task task) throws IOException{
		Set<Task> set = removeFromSet(task);
		task.setDeleted();
		String json = addToSet(task, set);
		toUndoStack(json);
	}

	protected void remove(Task task) throws IOException{
		Set<Task> set = removeFromSet(task);
		String json = gson.toJson(set,type);
		writeToFile(json);
		toUndoStack(json);
	}

	protected void update(Task taskToUpdate, Task newUpdatedTask) 
			throws EmptyTaskListException, IOException, DuplicateTaskException{
		Set<Task> set = reader.jsonToSet();
		String json = null;
		try{
			json = writeTask(newUpdatedTask);
			set.remove(taskToUpdate);
			set.add(newUpdatedTask);
			json = gson.toJson(set, type);
			writeToFile(json);
		}
		catch(DuplicateTaskException e){
			if(e.getValue() == -1){
				throw new DuplicateTaskException(String.format(MESSAGE_UPDATE_DUPLICATE, newUpdatedTask));
			} else{
				set.remove(taskToUpdate);
				set.remove(newUpdatedTask);
				set.add(newUpdatedTask);
				json = gson.toJson(set, type);
				writeToFile(json);
				throw e;
			}
		}
		catch(EventsClashException e){
			set.remove(taskToUpdate);
			json = addToSet(newUpdatedTask, set);
		}


		if(json!=null){
			toUndoStack(json);
		}
	}


	protected void restore(Task task) throws IOException, DuplicateTaskException{
		Set<Task> set = removeFromSet(task);
		if(task.isDeleted()){
			task.setNotDeleted();
		} else if(task.isDone()){
			task.setNotDone();
		}
		String json = addToSet(task, set);
		toUndoStack(json);
	}

	protected void setDone(Task task)throws IOException, DuplicateTaskException{
		Set<Task> set = removeFromSet(task);
		task.setDone();
		String json = addToSet(task, set);
		toUndoStack(json);
	}

	protected void setNotDone(Task task) throws IOException, DuplicateTaskException {
		Set<Task> set = removeFromSet(task);
		task.setNotDone();
		String json = addToSet(task, set);
		toUndoStack(json);
	}

	private Set<Task> removeFromSet(Task task) throws IOException {
		Set<Task> set = reader.jsonToSet();
		assert (set.isEmpty()) : MESSAGE_ASSERT_SET_EMPTY;
		set.remove(task);
		return set;
	}

	private String addToSet(Task task, Set<Task> set) throws IOException {
		set.add(task);
		String json = gson.toJson(set, type);
		writeToFile(json);
		return json;
	}


	public boolean undo(){
		originator.getStateFromMemento(careTaker.get());
		String state = originator.getState();
		if(state!=null){
			try{
				writeToFile(state);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			toRedoStack(currentJsonString);
			currentJsonString = state;
			careTaker.removeLast();
			return true;				
		}							
		return false;		
	}

	public boolean redo(){
		originator.setState(currentJsonString);
		careTaker.add(originator.saveStateToMemento());
		originator.getStateFromMemento(careTaker.restore());
		String state = originator.getState();
		if(state!=null){
			try{
				writeToFile(state);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			currentJsonString = state;
			return true;
		}
		return false;
	}

	public static void writeToSettings(String file) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(SETTINGS_FILE);
		writer.println(file);
		writer.close();
	}

	private void writeToFile(String json) throws IOException{
		FileWriter writer = new FileWriter(currentFile);
		writer.write(json);
		writer.close();	
	}

	private void toUndoStack(String json) {
		originator.setState(currentJsonString);
		careTaker.add(originator.saveStateToMemento());
		careTaker.initRedoStack(originator.saveStateToMemento());
		currentJsonString = json;
	}

	private void toRedoStack(String json){
		originator.setState(currentJsonString);
		careTaker.toRedoStack(originator.saveStateToMemento());
	}

}
