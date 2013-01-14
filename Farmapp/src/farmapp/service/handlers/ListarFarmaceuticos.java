package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;

import farmapp.model.User;



public class ListarFarmaceuticos extends Handler {

	
	private Connection connection;
	private Statement statement;

	public ListarFarmaceuticos() {
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
						
				String query = "SELECT * FROM usuarios WHERE id_tipo='2'";
				resultSet = statement.executeQuery(query);
				//resultSet.next();
			
				// System.out.println("Query executed");
			//if (resultSet.next()) {
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id_usuario"));
				user.setIdtipo(resultSet.getInt("id_tipo"));
				if (resultSet.getString("nombre") != null)
					user.setName(resultSet.getString("nombre"));
				if (resultSet.getString("apellidos") != null)
					user.setSurname(resultSet.getString("apellidos"));
				user.setEmail(resultSet.getString("email"));
				
				
				//result = "{\"status\":\"OK\", \"result\":"
					//	+ farm.toJSONString() + "}";
				result.add(user);
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
