package farmapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FarmaProductos implements JSONAware {
	private int id_farmacia;
	private String nombre_farmacia;
	private ArrayList<Producto> lista_productos;

	public int getId_farmacia() {
		return id_farmacia;
	}

	public void setId_farmacia(int id_farmacia) {
		this.id_farmacia = id_farmacia;
	}

	public String getNombre_farmacia() {
		return nombre_farmacia;
	}

	public void setNombre_farmacia(String nombre_farmacia) {
		this.nombre_farmacia = nombre_farmacia;
	}

	public ArrayList<Producto> getLista_productos() {
		return lista_productos;
	}

	public void setLista_productos(ArrayList<Producto> lista_productos) {
		this.lista_productos = lista_productos;
	}

	@Override
	public String toJSONString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");

		sb.append("\"" + JSONObject.escape("id_farmacia") + "\"");
		sb.append(":");
		sb.append(id_farmacia);
		
		sb.append(",");

		sb.append("\"" + JSONObject.escape("nombre_farmacia") + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(nombre_farmacia) + "\"");

		sb.append(",");

		sb.append("\"" + JSONObject.escape("lista_productos") + "\"");
		sb.append(":");
		
		sb.append("[");
		
		for(int i=0; i<lista_productos.size(); i++){
			if(i>0) sb.append(",");
			sb.append("{");
			sb.append("\"" + JSONObject.escape("producto") + "\"");
			sb.append(":");
			sb.append("\"" + JSONObject.escape(lista_productos.get(i).getProducto()) + "\"");
			
			sb.append(",");
			
			sb.append("\"" + JSONObject.escape("tipo") + "\"");
			sb.append(":");
			sb.append("\"" + JSONObject.escape(lista_productos.get(i).getProducto()) + "\"");

			sb.append(",");
			
			sb.append("\"" + JSONObject.escape("cantidad") + "\"");
			sb.append(":");
			sb.append("\"" + JSONObject.escape(lista_productos.get(i).getcantidad()) + "\"");

			sb.append(",");
			
			sb.append("\"" + JSONObject.escape("decripcion") + "\"");
			sb.append(":");
			sb.append("\"" + JSONObject.escape(lista_productos.get(i).getdescripcion()) + "\"");

			sb.append(",");
			
			sb.append("\"" + JSONObject.escape("receta") + "\"");
			sb.append(":");
			sb.append("\"" + JSONObject.escape(lista_productos.get(i).getreceta()) + "\"");

			sb.append(",");
			
			sb.append("\"" + JSONObject.escape("precio") + "\"");
			sb.append(":");
			sb.append("\"" + JSONObject.escape(lista_productos.get(i).getprecio()) + "\"");
			sb.append("}");
		}
		
		//sb.append("\"" + JSONObject.escape(lista_productos.toString()) + "\"");
		
		sb.append("]");
		
		sb.append("}");
		return sb.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public final static FarmaProductos jsonToFarmaProductos(String json) throws ParseException {
		FarmaProductos farma_prod= new FarmaProductos();
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
			
			if (entry.getKey().equals("id_farmacia"))
				farma_prod.setId_farmacia(Integer.parseInt(entry.getValue().toString()));
			
			else if (entry.getKey().equals("nombre_farmacia")) {
				farma_prod.setNombre_farmacia(entry.getValue().toString());
				System.out.println(entry.getValue().toString());
			
			} else if (entry.getKey().equals("tipo"))
				farma_prod.setLista_productos((JSONArray)entry.getValue());		

		}
		return farma_prod;
	}
	
	
}
