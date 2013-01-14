package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;

import farmapp.model.FarmaProductos;
import farmapp.model.Farmacia;
import farmapp.model.Producto;

public class ListProd2 extends Handler {
	
	private Connection connection;
	private Statement statement;
	private Statement statement1;

	public ListProd2() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		String id_farmaceutico = request.getParameter("id_farmaceutico");
		if (id_farmaceutico == null) {
			throw new MissingRequiredParameter();
		}

		//String result = null;
		JSONArray result = new JSONArray();
		ArrayList<Producto> result2;
		
		try {
			// Get Connection and Statement
			connection = dataSource.getConnection();
			statement1 = connection.createStatement();
			statement = connection.createStatement();
			
			String query2= "SELECT id_farmacia, nombre  FROM farmacias WHERE (id_admin)='" + id_farmaceutico + "'";
			resultSet1 = statement1.executeQuery(query2);
			
			int id_farmacia;
			String nombre_farmacia;
			
			//resultSet1.next();
			while(resultSet1.next()){
				result2 = new ArrayList<Producto>();
				id_farmacia = resultSet1.getInt("id_farmacia");
				nombre_farmacia = resultSet1.getString("nombre");
				
				if(id_farmacia > 0){
					
					String query = "SELECT productos.nombre, productos.tipo, productos.cantidad, " +
							"productos.descripcion, productos.receta, farm_prod.precio " +
							"FROM productos, farm_prod " +
							"WHERE farm_prod.id_farmacia='"+id_farmacia+"' " +
							"AND farm_prod.id_producto=productos.id_producto";
				
					resultSet = statement.executeQuery(query);
					
					while (resultSet.next()) {
						Producto prod = new Producto();
						
						if (resultSet.getString("nombre") != null)
							prod.setProducto(resultSet.getString("nombre"));
						
						//prod.setFarmacia(nombre_farmacia);
						prod.setTipo(resultSet.getString("tipo"));
						prod.setcantidad(resultSet.getString("cantidad"));
						prod.setdescripcion(resultSet.getString("descripcion"));
						prod.setreceta(resultSet.getString("receta"));
						prod.setprecio(resultSet.getString("precio"));
						
						result2.add(prod);
					}
					
					FarmaProductos farmaProductos = new FarmaProductos();
					farmaProductos.setId_farmacia(id_farmacia);
					farmaProductos.setNombre_farmacia(nombre_farmacia);
					farmaProductos.setLista_productos(result2);
					result.add(farmaProductos);
					
				}
			}
				
			//} else {
				//return "{\"status\":\"KO\", \"result\": \"Farmacia desconocida.\"}";
			//}
		} catch (SQLException e) {
			return "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos:"+e.getMessage()+"\"}";
		} finally {
			try {
				if (null != resultSet)
					resultSet.close();
				if (null != resultSet1)
					resultSet1.close();
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
