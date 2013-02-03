
package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

public class modificarusuario extends Handler {

	
	private Connection connection;
	private Statement statement;
	
	public modificarusuario(){
		super();
	}
	
	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		// TODO Auto-generated method stub
		
		String idusuario = request.getParameter("id_usuario");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String nombre = request.getParameter("nombre");
		String apellidos = request.getParameter("apellidos");
		String direccion = request.getParameter("direccion");
		String ciudad = request.getParameter("ciudad");
		String telefono = request.getParameter("telefono");
		//Esto es si esque se puede pasar el id_usuario por web
		
		
		try{
		
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = null;
			
			statement.executeUpdate("UPDATE usuarios SET email='"+email+"', password='"+password+"', nombre='"+nombre+"', apellidos='"+apellidos+"', direccion='"+direccion+"', ciudad='"+ciudad+"', telefono='"+telefono+"' WHERE id_usuario='"+idusuario+"';");
			
		} catch (SQLException e) {
			// e.printStackTrace();
			return "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos.\"}";
		}
	
		String result = "{\"status\":\"OK\", \"result\": \"Datos de Usuario actualizados correctamente\"}";
		return result.toString();
	}
	
	

}
