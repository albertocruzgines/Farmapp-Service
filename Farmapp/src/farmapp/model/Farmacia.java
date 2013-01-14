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

public class Farmacia implements JSONAware  {
	private long id;
	private String nombre;
	private String direccion;
	private String horario;
	private String ciudad;
	private long idadmin;
	private String latitud;
	private String longitud;
	private String telefono;
	
	public void printFarmacia(){
		System.out.println("Farmacia ID: "+this.id+
							"\nNombre: "+this.nombre+
							"\nDireccion:"+this.direccion+
							"\nHorario:"+this.horario);
	}
	
	public long getIdadmin(){
		return idadmin;
	}
	
	public void setIdtipo(long idadmin){
		this.idadmin = idadmin;
	}
	
	

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
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
		return nombre;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the direccion
	 */
	public String getdireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public void setdireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the horario
	 */
	public String gethorario() {
		return horario;
	}

	/**
	 * @param horario
	 *            the horario to set
	 */
	public void sethorario(String horario) {
		this.horario = horario;
	}
	
	public String getciudad() {
		return ciudad;
	}

	/**
	 * @param horario
	 *            the horario to set
	 */
	public void setciudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	public String getlatitud() {
		return latitud;
	}

	/**
	 * @param horario
	 *            the horario to set
	 */
	public void setlatitud(String latitud) {
		this.latitud = latitud;
	}
	
	public String getlongitud() {
		return longitud;
	}

	/**
	 * @param horario
	 *            the horario to set
	 */
	public void setlongitud(String longitud) {
		this.longitud = longitud;
	}
	


	@Override
	public String toJSONString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");

		sb.append("\"" + JSONObject.escape("id") + "\"");
		sb.append(":");
		sb.append(id);

		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("idadmin") + "\"");
		sb.append(":");
		sb.append(idadmin);

		sb.append(",");

		sb.append("\"" + JSONObject.escape("nombre") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(nombre) + "\"");

		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("ciudad") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(ciudad) + "\"");

		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("direccion") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(direccion) + "\"");

		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("horario") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(horario) + "\"");
		
		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("latitud") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(latitud) + "\"");

		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("longitud") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(longitud) + "\"");

		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("telefono") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(telefono) + "\"");
		

		sb.append("}");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public final static Farmacia jsonToFarmacia(String json) throws ParseException {
		Farmacia farm = new Farmacia();
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
				farm.setId(Long.parseLong(entry.getValue().toString()));
				else if (entry.getKey().equals("name")) {
				farm.setName(entry.getValue().toString());
				System.out.println(entry.getValue().toString());
			} else if (entry.getKey().equals("ciudad"))
				farm.setciudad(entry.getValue().toString());
			else if (entry.getKey().equals("direccion"))
				farm.setdireccion(entry.getValue().toString());
			else if (entry.getKey().equals("longitud"))
				farm.setlongitud(entry.getValue().toString());	
			else if (entry.getKey().equals("latitud"))
				farm.setlatitud(entry.getValue().toString());
			else if (entry.getKey().equals("horario"))
				farm.sethorario(entry.getValue().toString());
			else if (entry.getKey().equals("id_admin"))
				farm.setIdtipo(Long.parseLong(entry.getValue().toString()));

			

		}
		return farm;
	}

	public static void main(String[] args) throws ParseException {
		// String json =
		// "{\"id\":1,\"username\":\"alicia\",\"name\":\"null\",\"direccion\":\"null\",\"horario\":\"alicia@foo.com\"}";
		//String json = "{\"id\":1,\"username\":\"alicia\",\"horario\":\"alicia@foo.com\"}";
		//User u = User.jsonToUser(json);
		//System.out.println(u.getId() + " " + u.getUsername() + " "
			//	+ u.getName() + " " + u.getdireccion() + " " + u.gethorario());
	}
}