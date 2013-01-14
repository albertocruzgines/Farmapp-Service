package farmapp.service.handlers;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;



public abstract class Handler {
	protected DataSource dataSource;
	protected Handler(){
		super();
		
		try {
			// Get DataSource
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/farmapp");

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public abstract String process(HttpServletRequest request) throws MissingRequiredParameter;

}
