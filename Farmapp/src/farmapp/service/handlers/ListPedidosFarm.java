package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;

import farmapp.model.Pedidos;

public class ListPedidosFarm extends Handler {

	
	private Connection connection;
	private Statement statement;

	public ListPedidosFarm() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		ResultSet resultSet = null, resultSet2 = null, resultSet3 = null, resultSet4 = null, resultSet5 = null;
		String idadmin = request.getParameter("idadmin");
		if (idadmin == null) {
			throw new MissingRequiredParameter();
		}

		//String result = null;
		JSONArray result = new JSONArray();

		try {
			// Get Connection and Statement
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			// Obtengo todas las farms del admin
			String query ="SELECT id_farmacia, nombre FROM farmacias WHERE id_admin = "+idadmin;
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				
				// Obtengo todos los farm_prod de esta farmacia
				query ="SELECT id_farmprod, precio, id_producto FROM farm_prod WHERE id_farmacia = "+resultSet.getInt("id_farmacia");
				statement = connection.createStatement();
				resultSet2 = statement.executeQuery(query);
				
				while (resultSet2.next()) {
					// Obtengo nombre del producto
					query ="SELECT nombre FROM productos "+"WHERE id_producto  = "+resultSet2.getInt("id_producto");
					statement = connection.createStatement();
					resultSet3 = statement.executeQuery(query);
					String nombre_producto = "Desconocido";
					if(resultSet3.next()) nombre_producto = resultSet3.getString("nombre");
					
					// Obtengo todos los pedidos_farm_prod de este farm_prod
					query ="SELECT id_pedido, cantidad FROM pedidos_farm_prod "+"WHERE id_farmprod  = "+resultSet2.getInt("id_farmprod");
					statement = connection.createStatement();
					resultSet3 = statement.executeQuery(query);
					
					while (resultSet3.next()) {
						// 
						query ="SELECT id_usuario, fecha, estado FROM pedidos "+"WHERE id_pedido  = "+resultSet3.getInt("id_pedido");
						statement = connection.createStatement();
						resultSet4 = statement.executeQuery(query);
						
						while (resultSet4.next()) {
							// 
							query ="SELECT email FROM usuarios " +
									"WHERE id_usuario  = "+resultSet4.getInt("id_usuario");
							statement = connection.createStatement();
							resultSet5 = statement.executeQuery(query);
							
							if(resultSet5.next()){
								Pedidos ped = new Pedidos();
								ped.setIdpedido(resultSet3.getInt("id_pedido"));
								ped.setNameProd(nombre_producto);
								ped.setcantidad(resultSet3.getString("cantidad"));
								ped.setNameFarm(resultSet.getString("nombre"));
								ped.setprecio(resultSet2.getString("precio"));
								ped.setNameUser(resultSet5.getString("email"));
								ped.setfecha(resultSet4.getString("fecha"));
								ped.setestado(resultSet4.getString("estado"));
	
								result.add(ped);
							}
						}
					}
				}
				
				
				/*Pedidos ped = new Pedidos();
				ped.setIdpedido(resultSet.getInt("id_pedido"));
				if (resultSet.getString("productos.nombre") != null)
					ped.setNameProd(resultSet.getString("productos.nombre"));
				
				ped.setcantidad(resultSet.getString("cantidad"));
				ped.setNameFarm(resultSet.getString("nombre"));
				ped.setprecio(resultSet.getString("precio"));
				ped.setNameUser(resultSet.getString("email"));
				ped.setfecha(resultSet.getString("fecha"));
				ped.setestado(resultSet.getString("estado"));

				result.add(ped);*/
			}
				
			//} else {
				//return "{\"status\":\"KO\", \"result\": \"Farmacia desconocida.\"}";
			//}
		} catch (SQLException e) {
			return "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos: "+e.getMessage()+"\"}";
		} finally {
			try {
				if (null != resultSet) resultSet.close();
				if (null != resultSet2) resultSet2.close();
				if (null != resultSet3) resultSet3.close();
				if (null != resultSet4) resultSet4.close();
				if (null != resultSet5) resultSet5.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (null != statement)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (null != connection)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return "{\"status\":\"OK\", \"result\":" +result.toString() + "}";
	}




}
