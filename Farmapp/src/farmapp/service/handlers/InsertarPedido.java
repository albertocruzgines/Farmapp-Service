package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InsertarPedido extends Handler {


	
	

	private Connection connection;
	private Statement statement;

	public InsertarPedido() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		
		String email = request.getParameter("email");//El email del admin de farmacia para comprobar su idtipo
		String cantidad = request.getParameter("cantidad");
		

	
	

		
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = null;
			
			 String query = "select * from usuarios where email  = '" + email + "'";
				resultSet = statement.executeQuery(query);
				resultSet.next();
				int id_usuario=resultSet.getInt("id_usuario");
				int id_tipo=resultSet.getInt("id_tipo");
				if(id_tipo==3){
					statement.execute("insert into pedidos (id_usuario,estado) values ('"+id_usuario+"', 'no entregado');");
					String query2="select * from pedidos";
					resultSet = statement.executeQuery(query2);
					resultSet.last();
					int id_pedido=resultSet.getInt("id_pedido");
					//System.out.println("el ultimo pedido es el numero"+id_pedido);
					
				}else{
					return "{\"status\":\"KO\", \"result\": \"El usuario no tiene privilegios para crear pedidos\"}";
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
		String result = "{\"status\":\"OK\", \"result\": \"Pedido creado correctamente.\"}";
		return result;
	}



	



}
