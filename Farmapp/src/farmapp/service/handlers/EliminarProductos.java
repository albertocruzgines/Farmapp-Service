package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EliminarProductos extends Handler {
	


	private Connection connection;
	private Statement statement;

	public EliminarProductos() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		
		String nombre = request.getParameter("producto"); //El email del admin de farmacia para comprobar su idtipo
	System.out.println("producto: "+nombre);

		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = null;

			String query = "SELECT * FROM productos WHERE nombre='" + nombre + "'";
			resultSet = statement.executeQuery(query);
			resultSet.next();
			int id_producto=resultSet.getInt("id_producto");
			if (id_producto != 0){
				statement.execute("delete from productos where id_producto ='"+id_producto+"';");
			}
		
				
				
				
			
			
			
			

			//ResultSet rs = statement.getGeneratedKeys();
			//if (rs.next())
				//user.setId(rs.getInt(1));
		} catch (SQLException e) {
			// e.printStackTrace();
			return "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos.\"}";
		}

		
		//user.setIdtipo(idtipo);
		//user.setName(name);
		//user.setSurname(surname);
		//user.setEmail(email);
		//HttpSession session = request.getSession();
		//session.setAttribute("user", user);
		String result = "{\"status\":\"OK\", \"result\": \"Producto eliminado correctamente.\"}";
		return result;
	}



	


	
	

}
