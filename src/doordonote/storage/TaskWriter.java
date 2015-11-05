package doordonote.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.HashSet;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import doordonote.common.Task;



/**
 * @@author A0131716M
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
				//				toUndoStack(INITIAL_JSONSTRING);
			}	else{
				currentJsonString = TaskReader.getFileString(currentFile);
				//			toUndoStack(currentJsonString);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeToSettings(String file) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(SETTINGS_FILE);
		writer.println(file);
		writer.close();
	}

	protected void add(Task task) throws IOException, DuplicateTaskException{
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
		if(json != null){
			toUndoStack(json);
		}
	}

	private String writeTask(Task task)throws IOException, DuplicateTaskException{
		Set<Task> set = reader.jsonToSet();
		if(set.contains(task)){
			for(Task t : set){
				if(t.equals(task) && !t.isDeleted() && !task.isDeleted()){
					throw new DuplicateTaskException();
				} else if(t.equals(task) && t.isDeleted()){
					set.remove(task);
					task.setNotDeleted();
					set.add(task);
					String json = gson.toJson(set, type);
					writeToFile(json);
					throw new DuplicateTaskException(String.format(MESSAGE_DUPLICATE_DELETE, task), 0);
				} else if(t.equals(task) && t.isDone()){
					set.remove(task);
					task.setNotDone();
					set.add(task);
					String json = gson.toJson(set, type);
					writeToFile(json);
					throw new DuplicateTaskException(String.format(MESSAGE_DUPLICATE_DONE, task), 1);
				}
			}
		}
		set.add(task);
		String json = gson.toJson(set, type);
		writeToFile(json);
		return json;
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

	protected void delete(Task task) throws EmptyTaskListException, IOException{
		String json = writeDeleteTask(task);
		if(json!=null){
			toUndoStack(json);
		}
	}

	protected void remove(Task task) throws IOException{
		Set<Task> set = reader.jsonToSet();
		set.remove(task);
		String json = gson.toJson(set,type);
		try{
			writeToFile(json);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		toUndoStack(json);
	}

	private String writeDeleteTask(Task task) throws EmptyTaskListException, IOException {
		// Remove EmptyTaskListException
		// Throw assertion here

		Set<Task> set = reader.jsonToSet();
		if(!set.isEmpty()){
			set.remove(task);
			task.setDeleted();
			set.add(task);
			String json = gson.toJson(set, type);
			try{
				writeToFile(json);
			}
			catch(IOException e){
				e.printStackTrace();
			}
			return json;
		}

		else{
			throw  new EmptyTaskListException();
		}
	}

	protected void restore(Task task) throws IOException, DuplicateTaskException{
		Set<Task> set = reader.jsonToSet();
		set.remove(task);
		if(task.isDeleted()){
			task.setNotDeleted();
		} else if(task.isDone()){
			task.setNotDone();
		}
		set.add(task);
		String json = gson.toJson(set, type);
		writeToFile(json);
		toUndoStack(json);
	}

	protected void setDone(Task task)throws IOException, DuplicateTaskException{
		Set<Task> set = reader.jsonToSet();
		set.remove(task);
		task.setDone();
		set.add(task);
		String json = gson.toJson(set, type);
		writeToFile(json);
		toUndoStack(json);
	}

	protected void setNotDone(Task task) throws IOException, DuplicateTaskException {
		Set<Task> set = reader.jsonToSet();
		set.remove(task);
		task.setNotDone();
		set.add(task);
		String json = gson.toJson(set, type);
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
			// throw exception here
		}
		catch (DuplicateTaskException e){
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


		if(json!=null){
			toUndoStack(json);
		}
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
