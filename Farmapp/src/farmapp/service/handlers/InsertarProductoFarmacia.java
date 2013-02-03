package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InsertarProductoFarmacia extends Handler {


	
	

	private Connection connection;
	private Statement statement;

	public InsertarProductoFarmacia() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
				
		String nombreprod = request.getParameter("producto");
		String id_farmacia = request.getParameter("id_farmacia");
		String precio = request.getParameter("precio");
		String stock = request.getParameter("stock");
		
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = null;
			
			String query2 = "select * from productos where nombre = '"+nombreprod+"'";
			resultSet = statement.executeQuery(query2);
			resultSet.next();
			int id_producto= resultSet.getInt("id_producto");
					
			statement.execute("insert into farm_prod (id_farmacia, id_producto, precio, stock) " +
					"values ('"+id_farmacia+"', '"+id_producto+"','"+precio+"','"+stock+"');");
			

		} catch (SQLException e) {
			return "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos.\"}";
		}

		String result = "{\"status\":\"OK\", \"result\": \"Producto insertado en farmacia correctamente\"}";
		return result;
	}
	

}
