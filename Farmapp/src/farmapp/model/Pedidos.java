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

public class Pedidos implements JSONAware {
	private long idpedido;
	private String nombrefarm;
	private String cantidad;
	private long idfarmprod;
	private String precio;
	private String nombreuser;
	private String fecha;
	private String nombreprod;
	
	public String getNameProd() {
		return nombreprod;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setNameProd(String nombreprod) {
		this.nombreprod= nombreprod;
	}
	
	
	
	public long getIdpedido(){
		return idpedido;
	}
	
	public void setIdpedido(long idpedido){
		this.idpedido = idpedido;
	}
	
	public long getIdfarmprod(){
		return idfarmprod;
	}
	
	public void setIdfarmprod(long idfarmprod){
		this.idfarmprod = idfarmprod;
	}
	
	public String getNameFarm() {
		return nombrefarm;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setNameFarm(String nombrefarm) {
		this.nombrefarm= nombrefarm;
	}
	
	public String getNameUser() {
		return nombreuser;
	}
	
	public void setcantidad(String cantidad) {
		this.cantidad= cantidad;
	}
	
	public String getcantidad() {
		return cantidad;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setNameUser(String nombreuser) {
		this.nombreuser= nombreuser;
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
	
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setfecha(String fecha) {
		this.fecha = fecha;
		
		}
	
	@Override
	public String toJSONString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");

		sb.append("\"" + JSONObject.escape("idpedido") + "\"");
		sb.append(":");
		sb.append(idpedido);

		sb.append(",");
		
		

	
		
		sb.append("\"" + JSONObject.escape("producto") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(nombreprod) + "\"");

		sb.append(",");

		sb.append("\"" + JSONObject.escape("nombre") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(nombrefarm) + "\"");

		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("cantidad") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(cantidad) + "\"");

		sb.append(",");
		
		
		sb.append("\"" + JSONObject.escape("idfarmprod") + "\"");
		sb.append(":");
		sb.append(idfarmprod);

		sb.append(",");
		
		
		sb.append("\"" + JSONObject.escape("precio") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(precio) + "\"");
		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("emaileuser") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(nombreuser) + "\"");
		sb.append(",");
		
		sb.append("\"" + JSONObject.escape("fecha") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(fecha) + "\"");
		sb.append(",");
		
		sb.append("}");

	
		
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public final static Pedidos jsonToPedidos(String json) throws ParseException {
		Pedidos ped= new Pedidos();
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
			if (entry.getKey().equals("id_pedido"))
				ped.setIdpedido(Long.parseLong(entry.getValue().toString()));
				else if (entry.getKey().equals("namefarm")) {
				ped.setNameFarm(entry.getValue().toString());
				System.out.println(entry.getValue().toString());
			} else if (entry.getKey().equals("cantidad"))
				ped.setcantidad(entry.getValue().toString());
			else if (entry.getKey().equals("id_farmprod"))
				ped.setIdfarmprod(Long.parseLong(entry.getValue().toString()));
			else if (entry.getKey().equals("precio"))
				ped.setprecio(entry.getValue().toString());	
			else if (entry.getKey().equals("nameuser"))
				ped.setNameUser(entry.getValue().toString());
			else if (entry.getKey().equals("fecha"))
				ped.setfecha(entry.getValue().toString());
			

			

		}
		return ped;
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
