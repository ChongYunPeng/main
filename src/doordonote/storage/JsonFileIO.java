package doordonote.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.reflect.Type;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import doordonote.common.DeadlineTask;
import doordonote.common.EventTask;
import doordonote.common.FloatingTask;
import doordonote.common.Task;

/*
 * 
 *  @author: Chen Yongrui
 *  Undo and redo needs work
 *  
 */


public class JsonFileIO {

	private static final String DEFAULT_NAME = "data.json";
	private static final String FILE_TYPE = ".json";
	private static final String INITIAL_JSONSTRING = "{}";
	private static final int HASHSET_SIZE = 4099;
	private static final int HASHMAP_SIZE = 13;

	private final Gson gson = new GsonBuilder().registerTypeAdapter(Task.class, 
			new TaskClassAdapter<Task>()).create();

	private Type type = new TypeToken<HashSet<Task>>(){}.getType();
	private Set<Task> set = new HashSet<Task>(HASHSET_SIZE);
	


	private final Stack<String> undo = new Stack<String>();
	private final Stack<String> redo = new Stack<String>();

	private static String currentFile;



	public JsonFileIO(){
		currentFile = DEFAULT_NAME;
		initialize();
	}

	public JsonFileIO(String name){
		if(!name.contains(FILE_TYPE)){
			name += FILE_TYPE;
		}
		currentFile = name;
		initialize();
	}

	public String getFileName() {
		return currentFile;
	}


	public void setFile(String fileName){
		currentFile = fileName;
	}

	private void initialize(){
		try {
			File file = new File(currentFile);
			if(!file.exists()){
				file.createNewFile();
				writeToFile(INITIAL_JSONSTRING);
				undo.push(INITIAL_JSONSTRING);
			}	else{
				set = jsonToSet();
				undo.push(getFileString(currentFile));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	protected void add(Task task){
		String json = writeTask(task);
		if(json!=null){
			undo.push(json);
		}
	}

	private String writeTask(Task task) {
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
			fw.write("{}");
			fw.close();
			undo.push("{}");
		}

		catch(IOException e){
			e.printStackTrace();
		}
	}

	protected void delete(Task task) throws EmptyTaskListException{
		String json = writeDeleteTask(task);
		if(json!=null){
			undo.push(json);
		}

	}

	private String writeDeleteTask(Task task) throws EmptyTaskListException {
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
			undo.push(json);
		}
	}

	//This method reads the current json file and returns an
	//arraylist of Task sorted by Date. FloatingTasks are
	// at the back of this ArrayList
	protected ArrayList<Task> readTasks() throws IOException{
		ArrayList<Task> listTask = new ArrayList<Task>();
		for(Task t : set){
			if(!t.isDeleted()){
			    listTask.add(t);
			}
		}
		Collections.sort(listTask);
		return listTask;
	}
	
	protected ArrayList<Task> readDeletedTasks() throws IOException{
		ArrayList<Task> listTask = new ArrayList<Task>();
		for(Task t : set){
			if(t.isDeleted()){
			    listTask.add(t);
			}
		}
		Collections.sort(listTask);
		return listTask;
	}


	public boolean undo() {
		if(!undo.isEmpty()){
			redo.push(undo.pop());
			if(!undo.isEmpty()){
				try{
					writeToFile(undo.peek());
				}
				catch (IOException e){
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}


	public boolean redo(){
		if(!redo.isEmpty()){
			try{
				writeToFile(redo.peek());
				undo.push(redo.pop());
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
		return false;
	}

	// This method gets json string from currentFile and map it
	private HashSet<Task> jsonToSet() throws IOException {
		String json = getFileString(currentFile);
		HashSet<Task> set = gson.fromJson(json, type);
		return set;
	}

	private void writeToFile(String json) throws IOException{
		FileWriter writer = new FileWriter(currentFile);
		writer.write(json);
		writer.close();	
	}


	// This method reads strings from a file
	protected static String getFileString(String fileName) throws IOException{

		byte[] encoded = Files.readAllBytes(Paths.get(fileName));
		return new String(encoded);
	}


}
