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



public class User implements JSONAware  {
	private long id;
	private String email;
	private String username;
	private String name;
	private String surname;
	private String direccion;
	private String telefono;
	private String ciudad;
	private String password;
	private long idtipo;
	
	public long getIdtipo(){
		return idtipo;
	}
	
	public void setIdtipo(long idtipo){
		this.idtipo = idtipo;
	}
	
	

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	public String password() {
		return password;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	public String getCiudad() {
		return ciudad;
	}
	
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	

	/**
	 * @param name
	 *            the name to set
	 */
	

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		this.password = password;
	}
	@Override
	public String toJSONString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");

		sb.append("\"" + JSONObject.escape("id") + "\"");
		sb.append(":");
		sb.append(id);

		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("idtipo") + "\"");
		sb.append(":");
		sb.append(idtipo);

		sb.append(",");

		sb.append("\"" + JSONObject.escape("direccion") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(direccion) + "\"");

		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("ciudad") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(ciudad) + "\"");

		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("telefono") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(telefono) + "\"");

		sb.append(",");
		sb.append("\"" + JSONObject.escape("password") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(password) + "\"");

		sb.append(",");

		if (name != null) {
			sb.append("\"" + JSONObject.escape("name") + "\"");
			sb.append(":");
			sb.append("\"" + JSONObject.escape(name) + "\"");

			sb.append(",");
		}

		if (surname != null) {
			sb.append("\"" + JSONObject.escape("surname") + "\"");
			sb.append(":");
			sb.append("\"" + JSONObject.escape(surname) + "\"");

			sb.append(",");
		}

		sb.append("\"" + JSONObject.escape("email") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(email) + "\"");

		sb.append("}");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public final static User jsonToUser(String json) throws ParseException {
		User user = new User();
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
			if (entry.getKey().equals("id"))
				user.setId(Long.parseLong(entry.getValue().toString()));
				else if (entry.getKey().equals("name")) {
				user.setName(entry.getValue().toString());
				System.out.println(entry.getValue().toString());
			} else if (entry.getKey().equals("surname"))
				user.setSurname(entry.getValue().toString());
			else if (entry.getKey().equals("email"))
				user.setEmail(entry.getValue().toString());

		}
		return user;
	}

	public static void main(String[] args) throws ParseException {
		// String json =
		// "{\"id\":1,\"username\":\"alicia\",\"name\":\"null\",\"surname\":\"null\",\"email\":\"alicia@foo.com\"}";
		String json = "{\"id\":1,\"username\":\"alicia\",\"email\":\"alicia@foo.com\"}";
		User u = User.jsonToUser(json);
		//System.out.println(u.getId() + " " + u.getUsername() + " "
			//	+ u.getName() + " " + u.getSurname() + " " + u.getEmail());
	}

	
}
