package farmapp.model;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class Error implements JSONAware  {
	private int code;
	private String message;

	public Error() {
		super();
	}

	public Error(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toJSONString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");

		sb.append("\"" + JSONObject.escape("code") + "\"");
		sb.append(":");
		sb.append(code);

		sb.append(",");

		sb.append("\"" + JSONObject.escape("message") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(message) + "\"");

		sb.append("}");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public final static Error jsonToError(String json) throws ParseException {
		Error error = new Error();
		JSONParser parser = new JSONParser();
		ContainerFactory containerFactory = new ContainerFactory() {
			public List<String> creatArrayContainer() {
				return new LinkedList<String>();
			}

			public Map<String, String> createObjectContainer() {
				return new LinkedHashMap<String, String>();
			}

		};

		Map map = (Map) parser.parse(json, containerFactory);
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			if (entry.getKey().equals("code"))
				error.setCode(Integer.parseInt(entry.getValue().toString()));
			else if (entry.getKey().equals("message"))
				error.setMessage(entry.getValue().toString());

		}
		return error;
	}

	public static void main(String[] args) throws ParseException {
		String json = "{\"code\":400,\"message\":\"Missing required parameters for action login\"}";
		Error e = Error.jsonToError(json);
		System.out.println(e.getCode() + " " + e.getMessage());
	}
}
