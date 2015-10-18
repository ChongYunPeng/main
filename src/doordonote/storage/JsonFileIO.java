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

/*
 * 
 *  @author: Chen Yongrui
 * 
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

	private Type type = new TypeToken<HashMap<String, Set<Task>>>(){}.getType();
	private Set<Task> setFloating = new HashSet<Task>(HASHSET_SIZE);
	private Set<Task> setDeadline = new HashSet<Task>(HASHSET_SIZE);
	private Set<Task> setEvent = new HashSet<Task>(HASHSET_SIZE);
	private Set<Task> setDelete = new HashSet<Task>(HASHSET_SIZE);
	private Map<String, Set<Task>> map = new HashMap<String, Set<Task>>(HASHMAP_SIZE);

	private ArrayList<Task> listTask = null;


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
				map = jsonToMap();
				if(!map.isEmpty()){
					listTask = read();	
				}
				undo.push(getFileString(currentFile));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	protected void write(Task task){

		if(task instanceof FloatingTask){
			setFloating.add(task);
			map.put("FLOATING_TASK", setFloating);
		} else if(task instanceof DeadlineTask){
			setDeadline.add(task);
			map.put("DEADLINE_TASK", setDeadline);
		} else if(task instanceof EventTask){
			setEvent.add(task);
			map.put("EVENT_TASK", setEvent);
		}

		String json = gson.toJson(map, type);
		try{
			writeToFile(json);
		}

		catch(IOException e){
			e.printStackTrace();
		}
		//		undo.push(json);
	}


	protected void clear(){
		try{
			FileWriter fw = new FileWriter(currentFile);
			fw.write("{}");
			fw.close();
			undo.push("");
		}

		catch(IOException e){
			e.printStackTrace();
		}
	}

	protected void delete(Task taskToDelete) throws EmptyTaskListException{

		if(!map.isEmpty()){
			String taskType = taskToDelete.getType();
			Set<Task> set = map.get(taskType);
			set.remove(taskToDelete);
			setDelete.add(taskToDelete);
			map.put("DELETED_TASK", setDelete);
			String json = gson.toJson(map, type);
			try{
				writeToFile(json);
			}

			catch(IOException e){
				e.printStackTrace();
			}
		}

		else{
			throw  new EmptyTaskListException();
		}

	}

	protected void update(Task taskToUpdate, Task newUpdatedTask) 
			throws EmptyTaskListException{
			delete(taskToUpdate);
			write(newUpdatedTask);
	}

	//This method reads the current json file and returns an
	//arraylist of Task sorted by Date. FloatingTasks are
	// at the back of this ArrayList
	protected ArrayList<Task> read() throws IOException{
		setFloating = map.get("FLOATING_TASK");
		setDeadline = map.get("DEADLINE_TASK");
		setEvent = map.get("EVENT_TASK");
		listTask = new ArrayList<Task>();
		if(setDeadline!=null && !setDeadline.isEmpty()){
			ArrayList<Task> listDeadline = new ArrayList<Task>(setDeadline);
			listTask.addAll(listDeadline);
		}
		if(setEvent!=null && !setEvent.isEmpty()){
			ArrayList<Task> listEvent = new ArrayList<Task>(setEvent);
			listTask.addAll(listEvent);
		}

		Collections.sort(listTask);
		if(setFloating!=null && !setFloating.isEmpty()){
			listTask.addAll(setFloating);
		}
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
	private HashMap<String, Set<Task>> jsonToMap() throws IOException {
		String json = getFileString(currentFile);
		HashMap<String, Set<Task>> jsonMap = gson.fromJson(json, type);
		return jsonMap;
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
