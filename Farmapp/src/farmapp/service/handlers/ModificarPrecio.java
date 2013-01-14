package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ModificarPrecio extends Handler {

	
	

	private Connection connection;
	private Statement statement;

	public ModificarPrecio() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		
		String id_usuario = request.getParameter("id_usuario"); //El email del admin de farmacia para comprobar su idtipo
		String nombreprod = request.getParameter("nombre");
		//String id_producto = request.getParameter("id_producto");
		String precio = request.getParameter("precio");
	
	

		
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = null;
			

			
			 String query = "select * from farmacias where id_admin = '" + id_usuario + "'";
			resultSet = statement.executeQuery(query);
			resultSet.next();
			int idfarmacia=resultSet.getInt("id_farmacia");
			
			
			
			String query2 = "select * from productos where nombre = '"+nombreprod+"'";
			resultSet = statement.executeQuery(query2);
			resultSet.next();
			int id_producto= resultSet.getInt("id_producto");
			
			
			
			
			

				statement.executeUpdate("update farm_prod set precio='"+precio+"' where id_farmacia = '"+idfarmacia+"' AND id_producto = '"+id_producto+"'");
				
			
				
				
			
			
			
			

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
		String result = "{\"status\":\"OK\", \"result\": \"Precio actualizado correctamente\"}";
		return result;
	}



	
	

}
