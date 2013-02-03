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


public class Registrarfarmacia extends Handler {
	

	private Connection connection;
	private Statement statement;

	public Registrarfarmacia() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		
		String email = request.getParameter("email"); //El email del admin de farmacia para comprobar su idtipo
		String name = request.getParameter("name");
		String ciudad = request.getParameter("ciudad");
		String latitud = request.getParameter("latitud");
		String longitud = request.getParameter("longitud");
		String direccion = request.getParameter("direccion");
		String horario = request.getParameter("horario");
		String telefono = request.getParameter("telefono");

		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = null;

			String query = "select * from usuarios where email = '" + email + "'";
			resultSet = statement.executeQuery(query);
			resultSet.next();
			int idtipo=resultSet.getInt("id_tipo");
			int id_admin=resultSet.getInt("id_usuario");
			if(idtipo==2){
				
				statement.execute("insert into farmacias " +
						"(nombre, ciudad, latitud, longitud,direccion,horario,telefono,id_admin) " +
						"values ('"+name+"', '"+ciudad+"','"+latitud+"','"+longitud+"','"+direccion+"','"
						+horario+"','"+telefono+"','"+id_admin+"');");
				
			}else{
				return "{\"status\":\"KO\", \"result\": \"El usuario introducido no es administrador de farmacias\"}";
				
			}
			
		} catch (SQLException e) {
			// e.printStackTrace();
			return "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos.\"}";
		}
		
		InfoFarmacia info = new InfoFarmacia();
		String result = "{\"status\":\"OK\", \"result\": \""+info.getData(email)+"\"}";
		return result;
	}


}
