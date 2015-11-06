package doordonote.storage;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

//This class extends Gson library to write sub classes onto json file.

/**
 * @@author A0131716M
 *
 */

public class TaskClassAdapter<Task> implements JsonSerializer<Task>, JsonDeserializer<Task> {

	@Override
	public final JsonElement serialize(final Task object, final Type interfaceType, final JsonSerializationContext context) 
	{
		final JsonObject obj = new JsonObject();
		obj.addProperty("type", object.getClass().getName());
		obj.add("data", context.serialize(object));
		return obj;
	}

	@Override
	public final Task deserialize(final JsonElement element, final Type interfaceType, final JsonDeserializationContext context) 
			throws JsonParseException {
		final JsonObject obj = (JsonObject) element;
		final JsonElement typeString = get(obj, "type");
		final JsonElement data = get(obj, "data");
		final Type type = typeForName(typeString);
		return context.deserialize(data, type);
	}

	private Type typeForName(final JsonElement typeElement) {
		try {
			return Class.forName(typeElement.getAsString());
		} 
		catch (ClassNotFoundException e) {
			throw new JsonParseException(e);
		}
	}

	private JsonElement get(final JsonObject wrapper, final String memberName) {
		final JsonElement element = wrapper.get(memberName);
		if (element == null) {
			throw new JsonParseException("No '" + memberName + "' member found in json file.");
		}
		return element;
	}

}
