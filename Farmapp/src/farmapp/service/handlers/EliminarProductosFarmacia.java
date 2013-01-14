package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EliminarProductosFarmacia extends Handler {
	



	
	

	private Connection connection;
	private Statement statement;

	public EliminarProductosFarmacia() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		
		String id_usuario = request.getParameter("id_usuario"); //El email del admin de farmacia para comprobar su idtipo
		String nombreprod = request.getParameter("nombre");
		//String id_producto = request.getParameter("id_producto");
		
		
		
	
	

		
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = null;
			

			
			 String query = "select * from farmacias where id_admin = '" + id_usuario + "'";
				resultSet = statement.executeQuery(query);
				resultSet.next();
				int idfarmacia=resultSet.getInt("id_farmacia");
				System.out.println(idfarmacia);
				
			
				
				String query2 = "select * from productos where nombre = '"+nombreprod+"'";
				resultSet = statement.executeQuery(query2);
				resultSet.next();
				int id_producto= resultSet.getInt("id_producto");
				System.out.println(id_producto);
			
			
			
			if(idfarmacia != 0 && id_producto != 0){
				statement.execute("delete from farm_prod where id_producto ='"+id_producto+"';");
			}else{
				return "{\"status\":\"KO\", \"result\": \"Proceso incorrecto\"}";
			}
							
				
			//statement.execute("insert into farm_prod values (null, '1', '4', '4.55', '2256');");	
			
				
				
			
			
			
			

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
		String result = "{\"status\":\"OK\", \"result\": \"Producto eliminado en farmacia correctamente\"}";
		return result;
	}



	
	


	
	


	
	

}
