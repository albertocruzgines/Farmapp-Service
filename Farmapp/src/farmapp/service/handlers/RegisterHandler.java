package farmapp.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import farmapp.model.KeyFinder;
import farmapp.model.User;

public class RegisterHandler extends Handler {
	private Connection connection;
	private Statement statement;

	public RegisterHandler() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String email = request.getParameter("email");
		
		int idtipo = 3;
		String direccion = request.getParameter("direccion");
		String ciudad = request.getParameter("ciudad");
		String telefono = request.getParameter("telefono");
		if (password == null || email == null) {
			throw new MissingRequiredParameter();
		}
		CheckUserHandler checkUserHandler = new CheckUserHandler();
		String jsonResult = checkUserHandler.process(request);
		JSONParser parser = new JSONParser();
		KeyFinder finder = new KeyFinder();
		finder.setMatchKey("result");
		boolean alreadyExists = true;
		try {
			while (!finder.isEnd()) {
				parser.parse(jsonResult, finder, true);
				if (finder.isFound()) {
					finder.setFound(false);
					alreadyExists = Boolean.parseBoolean(finder.getValue()
							.toString());
				}
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		if (alreadyExists)
			return "{\"status\":\"KO\", \"result\": \"El usuario ya existe.\"}";

		User user = new User();
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();

			/*StringBuffer update = new StringBuffer(
					"INSERT INTO usuarios (email, password, nombre, apellidos,direccion,ciudad,telefono,id_tipo) VALUES (");
			
			update.append("'" + email + "', ");
			update.append("'" + password + "', ");
			update.append("'" + name + "', ");
			update.append("'" + surname + "', ");
			update.append("'" + direccion + "', ");
			update.append("'" + ciudad + "', ");
			update.append("'" + telefono + "', ");
			update.append("'" + idtipo + "', ");
			
			update.append(")");
			
			System.out.println(update);

			statement.executeUpdate(update.toString());*/
			
			
			statement.execute("insert into usuarios (email, password, nombre, apellidos,direccion,ciudad,telefono,id_tipo) values ('"+email+"', '"+password+"','"+name+"','"+surname+"','"+direccion+"','"+ciudad+"','"+telefono+"','"+idtipo+"');");

			//ResultSet rs = statement.getGeneratedKeys();
			//if (rs.next())
				//user.setId(rs.getInt(1));
		} catch (SQLException e) {
			// e.printStackTrace();
			return "{\"status\":\"KO\", \"result\": \"Error en el acceso a la base de datos.\"}";
		}

		
		user.setIdtipo(idtipo);
		user.setName(name);
		user.setSurname(surname);
		user.setEmail(email);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		String result = "{\"status\":\"OK\", \"result\":" + user.toJSONString()
				+ "}";
		return result;
	}
}
