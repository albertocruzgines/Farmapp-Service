
package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

public class updateproducto extends Handler {

	
	private Connection connection;
	private Statement statement;
	
	public updateproducto(){
		super();
	}
	
	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		// TODO Auto-generated method stub
		
		String idproducto = request.getParameter("id_producto");
		
		String nombre = request.getParameter("producto"); //El email del admin de farmacia para comprobar su idtipo
		String tipo = request.getParameter("tipo");
		String descripcion = request.getParameter("descripcion");
		//Esto es si esque se puede pasar el id_usuario por web
		
		
		try{
		
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = null;
			statement.executeUpdate("UPDATE productos SET nombre='"+nombre+"', tipo='"+tipo+"', descripcion='"+descripcion+"' WHERE id_producto='"+idproducto+"';");
			
			//statement.executeUpdate("UPDATE usuarios SET email='"+email+"', password='"+password+"', nombre='"+nombre+"', apellidos='"+apellidos+"', direccion='"+direccion+"', ciudad='"+ciudad+"', telefono='"+telefono+"' WHERE id_usuario='"+idusuario+"';");
			
		} catch (SQLException e) {
			// e.printStackTrace();
			return "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos.\"}";
		}
	
		String result = "{\"status\":\"OK\", \"result\": \"Datos del producto actualizados correctamente\"}";
		return result.toString();
	}
	
	

}
