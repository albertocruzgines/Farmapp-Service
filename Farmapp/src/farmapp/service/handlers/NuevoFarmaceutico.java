

package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import farmapp.model.KeyFinder;


public class NuevoFarmaceutico extends Handler {
	

	private Connection connection;
	private Statement statement;

	public NuevoFarmaceutico() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		
		String nombre = request.getParameter("nombre"); //El email del admin de farmacia para comprobar su idtipo
		String apellidos = request.getParameter("surname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String direccion = request.getParameter("direccion");
		String ciudad = request.getParameter("ciudad");
		String telefono = request.getParameter("telefono");
		

		try{
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = null;
			
			String query = "INSERT INTO `farmapp`.`usuarios` " +
					"(`email`, `password`, `nombre`, `apellidos`, `direccion`, `ciudad`, `telefono`, `id_tipo`) " +
					"VALUES ('"+email+"', '"+password+"', '"+nombre+"', '"+apellidos+"', '"+direccion+"', '"+ciudad+"', " +
							"'"+telefono+"', '2');";
			System.out.println ("query: "+query);
			statement.execute(query);
			
			
		} catch (SQLException e) {
			// e.printStackTrace();
			return "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos.\"}";
		}
	
		String result = "{\"status\":\"OK\", \"result\": \"Nuevo farmaceutico a&ntilde;adido\"}";
		return result.toString();
	}
	
	

}
