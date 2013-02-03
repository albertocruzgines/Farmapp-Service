package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;

import farmapp.model.Farmacia;
import farmapp.model.Pedidos;


public class ListPedidosUser extends Handler {

	
	private Connection connection;
	private Statement statement;

	public ListPedidosUser() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		ResultSet resultSet = null;
		String idusuario = request.getParameter("idusuario");
		
		if (idusuario== null) {
			throw new MissingRequiredParameter();
		}

		//String result = null;
		JSONArray result = new JSONArray();
		//String result;
		
		try {
			// Get Connection and Statement
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
	
			String query ="SELECT farmacias.nombre, pedidos.estado, pedidos.id_pedido, productos.nombre, pedidos_farm_prod.cantidad, farm_prod.id_farmprod, farm_prod.precio, farmacias.nombre, pedidos.fecha FROM farmacias, pedidos, pedidos_farm_prod, farm_prod, productos  WHERE farm_prod.id_producto=productos.id_producto AND farm_prod.id_farmprod=pedidos_farm_prod.id_farmprod AND pedidos.id_pedido=pedidos_farm_prod.id_pedido  AND farm_prod.id_farmacia=farmacias.id_farmacia AND pedidos.id_usuario= '" + idusuario + "'";
				
				resultSet = statement.executeQuery(query);
				
				
				while (resultSet.next()) {
					Pedidos ped = new Pedidos();
					
					
					ped.setIdpedido(resultSet.getInt("id_pedido"));
					if (resultSet.getString("nombre") != null)
						ped.setNameFarm(resultSet.getString("nombre"));
					
					ped.setcantidad(resultSet.getString("cantidad"));
					ped.setIdfarmprod(resultSet.getInt("id_farmprod"));
					ped.setprecio(resultSet.getString("precio"));
					ped.setfecha(resultSet.getString("fecha"));
					ped.setestado(resultSet.getString("estado"));
					ped.setNameProd(resultSet.getString("productos.nombre"));
					
				

					//result = "{\"status\":\"OK\", \"result\":"
						//	+ farm.toJSONString() + "}";
					result.add(ped);
				}
			
		
		
			// System.out.println("Query executed");
			//if (resultSet.next()) {
			
				
			//} else {
				//return "{\"status\":\"KO\", \"result\": \"Farmacia desconocida.\"}";
			//}
		} catch (SQLException e) {
			return "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos.\"}";
		} finally {
			try {
				if (null != resultSet)
					resultSet.close();
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
