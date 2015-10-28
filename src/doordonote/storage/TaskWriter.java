package doordonote.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;

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
	private static final Gson gson = new GsonBuilder().registerTypeAdapter(Task.class, 
			new TaskClassAdapter<Task>()).create();
	private static final Type type = new TypeToken<HashSet<Task>>(){}.getType();
	
	private String currentJsonString = INITIAL_JSONSTRING;

	private final Stack<String> undo = new Stack<String>();
	private Stack<String> redo = new Stack<String>();

	private static String currentFile;
	TaskReader reader;


	public TaskWriter(){
		currentFile = DEFAULT_NAME;
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


	public void setFile(String fileName){
		currentFile = fileName;
	}

	public String getCurrentFileString(){
		return currentJsonString;
	}

	private void initialize(){
		try {
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

	protected void add(Task task){
		String json = writeTask(task);
		if(json!=null){
			toUndoStack(json);
		}
	}

	private String writeTask(Task task) {
		Set<Task> set = reader.getJsonSet();
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

	protected void delete(Task task) throws EmptyTaskListException{
		String json = writeDeleteTask(task);
		if(json!=null){
			toUndoStack(json);
		}
	}
	
	protected void remove(Task task){
		Set<Task> set = reader.getJsonSet();
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

	private String writeDeleteTask(Task task) throws EmptyTaskListException {
		Set<Task> set = reader.getJsonSet();
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

	protected void update(Task taskToUpdate, Task newUpdatedTask) 
			throws EmptyTaskListException{
		writeDeleteTask(taskToUpdate);
		String json = writeTask(newUpdatedTask);
		if(json!=null){
			toUndoStack(json);
		}
	}


	public boolean undo() {
		if(!undo.isEmpty()){
			try{
				writeToFile(undo.peek());
			}
			catch (IOException e){
				e.printStackTrace();
			}
			currentJsonString = undo.peek();
			redo.push(undo.pop());
			return true;
		}
		return false;
	}


	public boolean redo(){
		if(!redo.isEmpty()){
			try{
				writeToFile(redo.peek());
			}
			catch (IOException e){
				e.printStackTrace();
			}
			currentJsonString = redo.peek();
			undo.push(redo.pop());
			return true;
		}
		return false;
	}

	private void writeToFile(String json) throws IOException{
		FileWriter writer = new FileWriter(currentFile);
		writer.write(json);
		writer.close();	
	}

	private void toUndoStack(String str) {
		undo.push(currentJsonString);
		currentJsonString = str;
		redo = new Stack<String>();
	}

}
