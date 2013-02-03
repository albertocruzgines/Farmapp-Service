package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

/* 
 * <handler>
	    <action auth="true">updateestado</action>
	    <handler-class>farmapp.service.handlers.ProcesarPedido</handler-class>
	</handler>
 * 
 * */

public class ProcesarPedido extends Handler {

	
	private Connection connection;
	private Statement statement;
	
	public ProcesarPedido(){
		super();
	}
	
	
	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		// TODO Auto-generated method stub
		
		String idpedido = request.getParameter("id_pedido");
		String estado = request.getParameter("estado");
		
		try{
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			statement.executeUpdate("UPDATE pedidos SET estado='"+estado+"' WHERE id_pedido='"+idpedido+"';");
			
		} catch (SQLException e) {
			// e.printStackTrace();
			return "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos.\"}";
		}
		
		
		String result = "{\"status\":\"OK\", \"result\": \"Datos de Pedido actualizados correctamente\"}";
		return result;
	}
	

}
