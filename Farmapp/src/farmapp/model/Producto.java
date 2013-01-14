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

public class Producto implements JSONAware {
	private long id;
	private String producto;
	private String tipo;
	private String cantidad;
	private String descripcion;
	private String receta;
	private String precio;
	private String farmacia;
	
	
	
	public String getFarmacia() {
		return farmacia;
	}

	public void setFarmacia(String farmacia) {
		this.farmacia = farmacia;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getProducto() {
		return producto;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setProducto(String nombre) {
		this.producto = nombre;
	}
	
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setTipo(String tipo) {
		this.tipo= tipo;
	}
	
	public String getcantidad() {
		return cantidad;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setcantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getdescripcion() {
		return descripcion;
	}
	
	public void setreceta(String receta) {
		this.receta = receta;
	}
	
	public String getreceta() {
		return receta;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setdescripcion(String descripcion) {
		this.descripcion = descripcion;
		
		}
	
	public String getprecio() {
		return precio;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setprecio(String precio) {
		this.precio = precio;
		
		}
	
	@Override
	public String toJSONString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");

		sb.append("\"" + JSONObject.escape("id_producto") + "\"");
		sb.append(":");
		sb.append(id);
		
		sb.append(",");


		sb.append("\"" + JSONObject.escape("farmacia") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(farmacia) + "\"");

		sb.append(",");


		sb.append("\"" + JSONObject.escape("producto") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(producto) + "\"");

		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("tipo") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(tipo) + "\"");

		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("cantidad") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(cantidad) + "\"");

		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("descripcion") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(descripcion) + "\"");
		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("precio") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(precio) + "\"");
		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("receta") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(receta) + "\"");

	
		sb.append("}");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public final static Producto jsonToProducto(String json) throws ParseException {
		Producto prod= new Producto();
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
				prod.setId(Long.parseLong(entry.getValue().toString()));
				else if (entry.getKey().equals("name")) {
				prod.setProducto(entry.getValue().toString());
				System.out.println(entry.getValue().toString());
			} else if (entry.getKey().equals("tipo"))
				prod.setTipo(entry.getValue().toString());
			else if (entry.getKey().equals("cantidad"))
				prod.setcantidad(entry.getValue().toString());
			else if (entry.getKey().equals("farmacia"))
				prod.setFarmacia(entry.getValue().toString());
			else if (entry.getKey().equals("descripcion"))
				prod.setdescripcion(entry.getValue().toString());	
			else if (entry.getKey().equals("receta"))
				prod.setreceta(entry.getValue().toString());
			else if (entry.getKey().equals("precio"))
				prod.setprecio(entry.getValue().toString());
			

			

		}
		return prod;
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
