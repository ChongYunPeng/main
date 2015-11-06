package doordonote.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import doordonote.common.Task;

/**
 * @@author A0131716M
 *
 */

public class TaskReader {

	private static final String DEFAULT_NAME = "data.json";
	private static final String FILE_TYPE = ".json";
	private static final String SETTINGS_FILE = "settings.dodn";
	private static final Charset ENCODING = StandardCharsets.UTF_8;
	private static final int HASHSET_SIZE = 4099;
	private static final Gson gson = new GsonBuilder().registerTypeAdapter(Task.class, 
			new TaskClassAdapter<Task>()).create();
	private static final Type type = new TypeToken<HashSet<Task>>(){}.getType();

	private Set<Task> set = new HashSet<Task>(HASHSET_SIZE);

	private static String currentFile;

	public TaskReader(){
		currentFile = DEFAULT_NAME;
		try{
			set = jsonToSet();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	public TaskReader(String name){
		currentFile = name;
		try{
			set = jsonToSet();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	public String getFileName() {
		return currentFile;
	}
	
	public static void setCurrentFile(String fileName){
		currentFile = fileName;
	}

	public String read(String fileName) throws FileNotFoundException{
		if(!fileName.contains(FILE_TYPE)){
			fileName += FILE_TYPE;
		}
		File readFromFile = new File(fileName);
		if(!readFromFile.exists()){
			throw new FileNotFoundException();
		} else{
			currentFile = fileName;
			TaskWriter.writeToSettings(currentFile);
			TaskWriter.setReadFile(currentFile);
			return currentFile;
		}
	}

	public Set<Task> getJsonSet(){
		return set;
	}


	//This method reads the current json file and returns an
	//arraylist of Task sorted by Date. FloatingTasks are
	//at the back of this ArrayList
	protected ArrayList<Task> readTasks() throws IOException{
		set = jsonToSet();
		ArrayList<Task> listTask = new ArrayList<Task>();
		for(Task t : set){
			if(!t.isDeleted() && !t.isDone()){
				listTask.add(t);
			}
		}
		Collections.sort(listTask);
		return listTask;
	}

	protected ArrayList<Task> readDeletedTasks() throws IOException{
		set = jsonToSet();
		ArrayList<Task> listTask = new ArrayList<Task>();
		for(Task t : set){
			if(t.isDeleted()){
				listTask.add(t);
			}
		}
		Collections.sort(listTask);
		return listTask;
	}

	protected ArrayList<Task> readDoneTasks() throws IOException{
		set = jsonToSet();
		ArrayList<Task> listTask = new ArrayList<Task>();
		for(Task t : set){
			if(t.isDone()){
				listTask.add(t);
			}
		}
		Collections.sort(listTask);
		return listTask;
	}

	// This method gets json string from currentFile and map it
	protected HashSet<Task> jsonToSet() throws IOException {
		String json = getFileString(currentFile);
		HashSet<Task> jsonSet = gson.fromJson(json, type);
		return jsonSet;
	}


	// This method reads strings from a file
	protected static String getFileString(String fileName) throws IOException{
		byte[] encoded = Files.readAllBytes(Paths.get(fileName));
		return new String(encoded, ENCODING);
	}
}
