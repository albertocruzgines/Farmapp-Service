package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import farmapp.model.User;
//import dsa.mus.service.util.SHA1;
import farmapp.service.util.Util;

public class LoginHandler extends Handler {

	private Connection connection;
	private Statement statement;

	public LoginHandler() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		ResultSet resultSet = null;
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		
		
		
		//if (username == null || password == null) {
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
				/*if (SHA1.getInstance()
						.digestToString(password)
						.equals(Util.toHexString(resultSet.getBytes("password")))) {*/
				
					User user = new User();
					user.setId(resultSet.getInt("id_usuario"));
					user.setIdtipo(resultSet.getInt("id_tipo"));
					if (resultSet.getString("nombre") != null)
						user.setName(resultSet.getString("nombre"));
					if (resultSet.getString("apellidos") != null)
						user.setSurname(resultSet.getString("apellidos"));
					user.setEmail(resultSet.getString("email"));

					HttpSession session = request.getSession();
					session.setAttribute("user", user);
					result = "{\"status\":\"OK\", \"result\":"
							+ user.toJSONString() + "}";
					System.out.println(result);
					
				/*} else {
					result = "{\"status\":\"KO\", \"result\": \"Contraseña incorrecta.\"}";
				}*/
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
