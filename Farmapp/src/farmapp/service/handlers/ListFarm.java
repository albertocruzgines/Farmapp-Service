package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;

import farmapp.model.Farmacia;


public class ListFarm extends Handler {
	
	private Connection connection;
	private Statement statement;

	public ListFarm() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		ResultSet resultSet = null;
		String ciudad = request.getParameter("ciudad");
		if (ciudad == null) {
			throw new MissingRequiredParameter();
		}

		//String result = null;
		JSONArray result = new JSONArray();
		//String result;
		
		try {
			// Get Connection and Statement
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			String query2= "SELECT COUNT(*)  FROM farmacias WHERE (ciudad)='" + ciudad + "'";
			resultSet = statement.executeQuery(query2);
			resultSet.next();
			if(resultSet.getInt("COUNT(*)")>0){
				String query = "SELECT * FROM farmacias WHERE ciudad='" + ciudad
						+ "'";
				resultSet = statement.executeQuery(query);
				
			}else{
				return "{\"status\":\"KO\", \"result\": \"Farmacia desconocida.\"}";
				
			}
			
			
			
			
			
		
			// System.out.println("Query executed");
			//if (resultSet.next()) {
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
