package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

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
				
		String id_usuario = request.getParameter("id_usuario");
		String nombre = request.getParameter("nombre");
		
		String[] productos = request.getParameterValues("producto");
		String[] cantidades = request.getParameterValues("cantidad");
		
		
	
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = null;
			
			
			
			//recogemos también el id_farmacia para después
			String query2 = "select * from farmacias where nombre  = '" + nombre + "'";
			resultSet = statement.executeQuery(query2);
			resultSet.next();
			int idfarmacia=resultSet.getInt("id_farmacia");
			
			//Ahora creamos el pedido
			statement.execute("insert into pedidos (id_usuario,estado) values ('"+id_usuario+"', 'no entregado');");
			String query3="select * from pedidos";
			resultSet = statement.executeQuery(query3);
			resultSet.last();
			int idpedido=resultSet.getInt("id_pedido");
			
			//Y finalmente metemos todos los productos en su tablica
			for(int i=0; i< productos.length; i++)
			{
				//Aqui dentro ya tengo las cantidades y los productos
				
				//busco id producto
				String query4 = "select id_producto from productos where nombre='"+productos[i]+"'";
				resultSet = statement.executeQuery(query4);
				resultSet.next();
				int idproducto=resultSet.getInt("id_producto");
				
				//busco id farm prod
				String query5 = "select id_farmprod from farm_prod where id_farmacia='"+idfarmacia+"' and id_producto='"+idproducto+"';";
				resultSet = statement.executeQuery(query5);
				resultSet.next();
				int idfarmprod=resultSet.getInt("id_farmprod");
				
				//Compruebo todo
				System.out.println("Id producto: "+idproducto);
				System.out.println("Producto: "+productos[i]);
				System.out.println("Cantidad: "+cantidades[i]);
				System.out.println("Id Farmacia_producto: "+idfarmprod);
				
				//HAGO EL INSERT
				statement.execute("insert into pedidos_farm_prod values ('"+idpedido+"','"+idfarmprod+"','"+cantidades[i]+"');");
				
				
			}
			
			
			//System.out.println("el ultimo pedido es el numero"+id_pedido);
			
			
					

			
		
				

				
			
			
			
			

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
