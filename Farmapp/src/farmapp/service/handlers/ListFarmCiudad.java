package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;

import farmapp.model.Farmacia;
import farmapp.model.User;



public class ListFarmCiudad extends Handler {

	private Connection connection;
	private Statement statement;

	public ListFarmCiudad() {
		super();
	}
	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		ResultSet resultSet = null;
		String ciudad = request.getParameter("ciudad");
		
		JSONArray result = new JSONArray();
		//String result;
				try {
			// Get Connection and Statement
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
				String query = "SELECT * FROM farmacias WHERE ciudad = '"+ciudad+"'";
				resultSet = statement.executeQuery(query);
							
				while (resultSet.next()) {
									
					Farmacia farm = new Farmacia();
					
					farm.setId(resultSet.getInt("id_farmacia"));
					if (resultSet.getString("nombre") != null)
					farm.setName(resultSet.getString("nombre"));
					
					farm.setciudad(resultSet.getString("ciudad"));
					farm.setdireccion(resultSet.getString("direccion"));
					farm.sethorario(resultSet.getString("horario"));
				//	farm.setlongitud(resultSet.getString("longitud"));
				//	farm.setlatitud(resultSet.getString("latitud"));
			
		
					result.add(farm);
				}
	
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
