//@@author A0131716M
package doordonote.storage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
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
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import doordonote.common.Task;


/**
 * @author A0131716M
 *
 */
public class TaskReader {

	private static final String FILE_TYPE = ".json";
	private static final String INITIAL_JSONSTRING = "[]";
	private static final String SETTINGS_FILE = "settings.dodn";
	private static final Charset ENCODING = StandardCharsets.UTF_8;
	private static final int HASHSET_SIZE = 4099;
	private static final Gson gson = new GsonBuilder().registerTypeAdapter(Task.class, 
			new TaskClassAdapter<Task>()).create();
	private static final Type type = new TypeToken<HashSet<Task>>(){}.getType();

	private Set<Task> set = new HashSet<Task>(HASHSET_SIZE);
	
	//This parameter is to check if file is a valid Json file. Freeze operations if file is not valid.
	private boolean isValidJson;

	private static String currentFile;

	protected TaskReader(){
		try{
			currentFile = getFileString(SETTINGS_FILE).trim();
			set = jsonToSet();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	protected TaskReader(String name){
		currentFile = name;
		try{
			set = jsonToSet();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	protected String getFileName() {
		return currentFile;
	}

	protected static void setCurrentFile(String fileName){
		currentFile = fileName;
	}
	
	//This method switches to said fileName to read.
	protected String read(String fileName) throws FileNotFoundException{
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
			CareTaker careTaker = TaskWriter.getCareTaker();
			careTaker.resetUndo();
			return currentFile;
		}
	}

	protected Set<Task> getJsonSet(){
		return set;
	}


	//This method reads the current json file and returns an
	//arraylist of Task sorted by Date. FloatingTasks are
	//at the back of this ArrayList
	protected ArrayList<Task> readTasks() throws IOException{			
			set = jsonToSet();
			if(isValidJson){
			TaskWriter.setValidJson();
			ArrayList<Task> listTask = new ArrayList<Task>();
			if(set == null){
				FileWriter writer = new FileWriter(currentFile);
				writer.write(INITIAL_JSONSTRING);
				writer.close();
				return listTask;
			} else{
				for(Task t : set){
					if(!t.isDeleted() && !t.isDone()){
						listTask.add(t);
					}
				}
				Collections.sort(listTask);
				return listTask;
			}
		} else{
			TaskWriter.setInvalidJson();
			throw new IOException();
		}
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
			if(t.isDone() && !t.isDeleted()){
				listTask.add(t);
			}
		}
		Collections.sort(listTask);
		return listTask;
	}

	// This method gets json string from currentFile and map it
	protected HashSet<Task> jsonToSet() throws IOException {
		String json = getFileString(currentFile);
		HashSet<Task> jsonSet = null;
		try{
			new JsonParser().parse(json);
		}
		catch(JsonParseException e){
			isValidJson = false;
			return jsonSet;
		}
		isValidJson = true;		
		jsonSet = gson.fromJson(json, type);
		return jsonSet;
	}


	// This method reads strings from a file
	public static String getFileString(String fileName) throws IOException{
		byte[] encoded = Files.readAllBytes(Paths.get(fileName));
		return new String(encoded, ENCODING);
	}
}
