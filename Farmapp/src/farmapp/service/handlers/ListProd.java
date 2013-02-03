package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;

import farmapp.model.Farmacia;
import farmapp.model.Producto;

public class ListProd extends Handler {
	
	private Connection connection;
	private Statement statement;

	public ListProd() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		ResultSet resultSet = null;
		String nombre = request.getParameter("nombre");
		if (nombre == null) {
			throw new MissingRequiredParameter();
		}

		//String result = null;
		JSONArray result = new JSONArray();
		//String result;
		
		try {
			// Get Connection and Statement
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			String query2= "SELECT * FROM farmacias WHERE (nombre)='" + nombre + "'";
			resultSet = statement.executeQuery(query2);
			resultSet.next();
			
			int id_farmacia =resultSet.getInt("id_farmacia");
			if(id_farmacia > 0){
				//String query = "SELECT productos.nombre, productos.tipo, productos.cantidad, productos.descripcion, productos.receta, farm_prod.precio FROM productos, farm_prod, farmacias " +
					//	"WHERE farm_prod.id_producto=productos.id_producto " +
						//"AND farm_prod.id_farmacia= '" + id_farmacia + "'"; 
				
				String query = "SELECT * FROM productos, farm_prod WHERE farm_prod.id_farmacia='"+id_farmacia+"' AND farm_prod.id_producto=productos.id_producto";

				
				resultSet = statement.executeQuery(query);
				
			}else return "{\"status\":\"KO\", \"result\": \"Farmacia no existe.\"}";
			
		
		
			// System.out.println("Query executed");
			//if (resultSet.next()) {
			while (resultSet.next()) {
				Producto prod = new Producto();
				
				if (resultSet.getString("nombre") != null)
					prod.setProducto(resultSet.getString("nombre"));
				
				prod.setId(resultSet.getInt("id_producto"));
				prod.setFarmacia(String.valueOf(id_farmacia));
				prod.setTipo(resultSet.getString("tipo"));
				prod.setcantidad(resultSet.getString("cantidad"));
				prod.setdescripcion(resultSet.getString("descripcion"));
				prod.setprecio(resultSet.getString("precio"));
				prod.setreceta(resultSet.getString("receta"));
				
				//result = "{\"status\":\"OK\", \"result\":"
					//	+ farm.toJSONString() + "}";
				result.add(prod);
			}
				
			//} else {
				//return "{\"status\":\"KO\", \"result\": \"Farmacia desconocida.\"}";
			//}
		} catch (SQLException e) {
			return "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos.\"}";
		} finally {
			try {
				if (null != resultSet)
					resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (null != statement)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (null != connection)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return "{\"status\":\"OK\", \"result\":" +result.toString() + "}";
	}

}
