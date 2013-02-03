package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;

import farmapp.model.Farmacia;

public class ListarProdFarm extends Handler {

	
	private Connection connection;
	private Statement statement;

	public ListarProdFarm() {
		super();
	}
	
	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		
		ResultSet resultSet = null;
		ResultSet resultSet2 = null;
		
		String producto = request.getParameter("producto");
		
		if (producto == null) {
			throw new MissingRequiredParameter();
		}
		
		//String result = null;
		JSONArray result = new JSONArray();
		
		try{
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			String query = "SELECT * FROM productos WHERE nombre='"+producto+"';";
			resultSet = statement.executeQuery(query);
			resultSet.next();
			int idproducto=resultSet.getInt("id_producto");
			
			String query2 = "SELECT * FROM farmacias, farm_prod WHERE farm_prod.id_producto='"+idproducto+"' AND farm_prod.id_farmacia=farmacias.id_farmacia;";
			resultSet2 = statement.executeQuery(query2);
			
			while (resultSet2.next()) {
				
				Farmacia farm = new Farmacia();
				
				farm.setId(resultSet2.getInt("id_farmacia"));
				if (resultSet2.getString("nombre") != null)
				farm.setName(resultSet2.getString("nombre"));
				farm.setTelefono(resultSet2.getString("telefono"));
				farm.setciudad(resultSet2.getString("ciudad"));
				farm.setdireccion(resultSet2.getString("direccion"));
				farm.sethorario(resultSet2.getString("horario"));
				farm.setlongitud(resultSet2.getString("longitud"));
				farm.setlatitud(resultSet2.getString("latitud"));
		
	
				result.add(farm);
			}
			
		}	 catch (SQLException e) {
			return "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos\"}";
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
		
		
		
		
		
		
		
		
		
		
		// TODO Auto-generated method stub
		return "{\"status\":\"OK\", \"result\":" +result.toString() + "}";
	}

}
