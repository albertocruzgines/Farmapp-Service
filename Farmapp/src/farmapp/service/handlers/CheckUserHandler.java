package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

public class CheckUserHandler extends Handler {
	private Connection connection;
	private Statement statement;

	public CheckUserHandler() {
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
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			String query = "SELECT * FROM usuarios WHERE email='" + email
					+ "'";

			resultSet = statement.executeQuery(query);
			result = "{\"status\":\"OK\", \"result\":" + resultSet.next() + "}";
		} catch (SQLException e) {
			e.printStackTrace();
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

		return result;
	}

}
