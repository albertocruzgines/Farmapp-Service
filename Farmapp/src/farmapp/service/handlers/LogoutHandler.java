package farmapp.service.handlers;

import javax.servlet.http.HttpServletRequest;

import farmapp.service.handlers.Handler;
import farmapp.service.handlers.MissingRequiredParameter;

public class LogoutHandler extends Handler {

	public LogoutHandler() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		request.getSession().invalidate();
		return "{\"status\":\"OK\", \"result\": \"Sesión cerrada con éxito.\"}";
	}

}

