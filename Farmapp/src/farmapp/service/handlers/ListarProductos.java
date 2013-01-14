package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;

import farmapp.model.Producto;
import farmapp.model.User;



public class ListarProductos extends Handler {

	
	private Connection connection;
	private Statement statement;

	public ListarProductos() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		ResultSet resultSet = null;
		

		//String result = null;
		JSONArray result = new JSONArray();
		//String result;
		
		try {
			// Get Connection and Statement
			connection = dataSource.getConnection();
			statement = connection.createStatement();
						
				String query = "SELECT * FROM productos";
				resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				
				Producto prod = new Producto();
							
				prod.setProducto(resultSet.getString("nombre"));
				prod.setTipo(resultSet.getString("tipo"));
				prod.setcantidad(resultSet.getString("cantidad"));
				prod.setdescripcion(resultSet.getString("descripcion"));
				prod.setreceta(resultSet.getString("receta"));
				prod.setprecio(resultSet.getString("precio"));

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
