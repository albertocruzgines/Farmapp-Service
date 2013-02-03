package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import farmapp.model.User;

public class InfoHandler extends Handler {
	private Connection connection;
	private Statement statement;

	public InfoHandler() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		ResultSet resultSet = null;
		String email = request.getParameter("email");
		if (email == null) {
			throw new MissingRequiredParameter();
		}

		String result = null;
		try {
			// Get Connection and Statement
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			String query = "SELECT * FROM usuarios WHERE email='" + email
					+ "'";
			resultSet = statement.executeQuery(query);
			// System.out.println("Query executed");
			if (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id_usuario"));
				if (resultSet.getString("nombre") != null)
					user.setName(resultSet.getString("nombre"));
				if (resultSet.getString("apellidos") != null)
					user.setSurname(resultSet.getString("apellidos"));
				user.setEmail(resultSet.getString("email"));
				user.setTelefono(resultSet.getString("telefono"));
				user.setCiudad(resultSet.getString("ciudad"));
				user.setDireccion(resultSet.getString("direccion"));
				user.setPassword(resultSet.getString("password"));
				result = "{\"status\":\"OK\", \"result\":"
						+ user.toJSONString() + "}";
			} else {
				result = "{\"status\":\"KO\", \"result\": \"Usuario desconocido.\"}";
			}
		} catch (SQLException e) {
			result = "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos.\"}";
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

}

