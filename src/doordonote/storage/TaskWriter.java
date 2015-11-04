package doordonote.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.HashSet;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import doordonote.common.Task;

/*
 * 
 *  @author: Chen Yongrui
 *  
 */


public class TaskWriter {

	private static final String DEFAULT_NAME = "data.json";
	private static final String FILE_TYPE = ".json";
	private static final String INITIAL_JSONSTRING = "[]";
	private static final String SETTINGS_FILE = "settings.dodn";
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
				toUndoStack(INITIAL_JSONSTRING);
			}	else{
				currentJsonString = TaskReader.getFileString(currentFile);
				toUndoStack(currentJsonString);
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

	protected void add(Task task) throws IOException{
		String json = writeTask(task);
		if(json!=null){
			toUndoStack(json);
		}
	}

	private String writeTask(Task task)throws IOException {
		Set<Task> set = reader.jsonToSet();
		set.remove(task);
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

	protected void restore(Task task) throws IOException{
		if(task.isDeleted()){
			task.setNotDeleted();
		} else if(task.isDone()){
			task.setNotDone();
		}
		add(task);
	}

	protected void setDone(Task task)throws IOException{
		task.setDone();
		add(task);
	}

	protected void setNotDone(Task task) throws IOException{
		task.setNotDone();
		add(task);
	}

	protected void update(Task taskToUpdate, Task newUpdatedTask) 
			throws EmptyTaskListException, IOException{
		Set<Task> set = reader.jsonToSet();
		set.remove(taskToUpdate);
		// throw exception here
		String json = writeTask(newUpdatedTask);
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
