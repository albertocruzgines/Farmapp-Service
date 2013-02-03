package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

public class SearchHandler extends Handler {
	private Connection connection;
	private Statement statement;

	public SearchHandler() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		String query = request.getParameter("query");
		ResultSet resultSet = null;
		StringBuffer result = null;
		if (query == null)
			return "{\"status\":\"OK\", \"result\": []}";

		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			String sql = "select * from farmacias where nombre like '" + query
					+ "%'";
			resultSet = statement.executeQuery(sql);
			result = new StringBuffer("{\"status\":\"OK\", \"result\": [");
			while (resultSet.next()) {
				String next = resultSet.getString("nombre");
				result.append("\"" + next + "\",");
			}
			if (result.charAt(result.length() - 1) == ',')
				result.deleteCharAt(result.length() - 1);
			result.append("]}");
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

}
