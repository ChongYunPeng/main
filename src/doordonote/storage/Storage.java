package doordonote.storage;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;

/*
 * 
 *  @author: Chen Yongrui
 *  Still need to resolve hash collision
 *  Undo/redo needs work
 *  Need more testing
 */

public class Storage {

	public String add(String cmdArguments) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("test.txt", true));
			writer.append(cmdArguments);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeResource(writer);
		}
		return cmdArguments + " added successfully";
	}

	private void closeResource(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
