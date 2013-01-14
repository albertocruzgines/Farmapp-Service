package farmapp.service.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoggedHandler extends Handler {

	public LoggedHandler() {
		super();
	}

	@Override
	public String process(HttpServletRequest request)
			throws MissingRequiredParameter {
		
		HttpSession session = request.getSession(false);// don't create if it
														// doesn't exist
		String result = "{\"status\":\"OK\", \"result\":"
				+ (session != null && !session.isNew() && session
						.getAttribute("user") != null) + "}";
		return result;
	}

}