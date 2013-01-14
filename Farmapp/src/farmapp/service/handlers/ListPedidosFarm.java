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
		ResultSet resultSet = null;
		String idfarmacia = request.getParameter("idfarmacia");
		if (idfarmacia == null) {
			throw new MissingRequiredParameter();
		}

		//String result = null;
		JSONArray result = new JSONArray();
		//String result;
		
		try {
			// Get Connection and Statement
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			
			
			String query ="SELECT pedidos.id_pedido, productos.nombre, pedidos_farm_prod.cantidad, farm_prod.id_farmprod, farm_prod.precio, usuarios.email, pedidos.fecha FROM usuarios, pedidos, pedidos_farm_prod, farm_prod, productos WHERE farm_prod.id_producto=productos.id_producto AND pedidos.id_usuario=usuarios.id_usuario AND farm_prod.id_farmprod=pedidos_farm_prod.id_farmprod AND pedidos.id_pedido=pedidos_farm_prod.id_pedido AND farm_prod.id_farmacia= '" + idfarmacia + "'";
			

			
			resultSet = statement.executeQuery(query);
			
			
			while (resultSet.next()) {
				Pedidos ped = new Pedidos();
				ped.setIdpedido(resultSet.getInt("id_pedido"));
				if (resultSet.getString("nombre") != null)
					ped.setNameProd(resultSet.getString("nombre"));
				
				ped.setcantidad(resultSet.getString("cantidad"));
				ped.setIdfarmprod(resultSet.getInt("id_farmprod"));
				ped.setprecio(resultSet.getString("precio"));
				ped.setNameUser(resultSet.getString("email"));
				ped.setfecha(resultSet.getString("fecha"));
				
				
				
				//result = "{\"status\":\"OK\", \"result\":"
					//	+ farm.toJSONString() + "}";
				result.add(ped);
			}
				
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
