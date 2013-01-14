package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;

import farmapp.model.Farmacia;
import farmapp.model.User;

public class InfoFarmacia extends Handler {

	private Connection connection;
	private Statement statement;

	public InfoFarmacia() {
		super();
	}

	public String getData(String email){
		
		JSONArray result = new JSONArray();
		ResultSet resultSet = null;
		
		try {
			// Get Connection and Statement
			connection = dataSource.getConnection();
			statement = connection.createStatement();

			String query2 = "select * from usuarios where email= '" + email
					+ "'";
			resultSet = statement.executeQuery(query2);
			resultSet.next();
			int id_usuario =resultSet.getInt("id_usuario");

			String query = "SELECT * FROM farmacias WHERE id_admin='" + id_usuario+ "'";
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				Farmacia farm = new Farmacia();
				farm.setId(resultSet.getInt("id_farmacia"));
				if (resultSet.getString("nombre") != null)
					farm.setName(resultSet.getString("nombre"));

				farm.setciudad(resultSet.getString("ciudad"));
				farm.setdireccion(resultSet.getString("direccion"));
				farm.sethorario(resultSet.getString("horario"));
				farm.setlongitud(resultSet.getString("longitud"));
				farm.setlatitud(resultSet.getString("latitud"));


				//result = "{\"status\":\"OK\", \"result\":"
				//	+ farm.toJSONString() + "}";
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
		return result.toString();
	}


	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
			
		String email=request.getParameter("email");
		return "{\"status\":\"OK\", \"result\":" +getData(email) + "}";
	}

}
