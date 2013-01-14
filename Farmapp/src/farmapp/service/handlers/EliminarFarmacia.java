package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EliminarFarmacia extends Handler {
	
	private Connection connection;
	private Statement statement;

	public EliminarFarmacia() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		
		String nombre = request.getParameter("nombre"); //El email del admin de farmacia para comprobar su idtipo
	

	
	

		
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = null;

			String query = "SELECT * FROM farmacias WHERE nombre='" + nombre + "'";
			resultSet = statement.executeQuery(query);
			resultSet.next();
			int idfarmacia=resultSet.getInt("id_farmacia");
			if (idfarmacia!= 0){
				statement.execute("delete from farmacias where id_farmacia ='"+idfarmacia+"';");
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
		String result = "{\"status\":\"OK\", \"result\": \"Farmacia eliminada correctamente.\"}";
		return result;
	}



	


	
	


	
	

}
