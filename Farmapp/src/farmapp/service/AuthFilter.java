package farmapp.service;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import farmapp.model.Error;
import farmapp.model.User;
import farmapp.service.handlers.HandlerFactory;
import farmapp.service.handlers.HandlerInfo;

public class AuthFilter implements Filter {
	public AuthFilter() {
		super();
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String action = request.getParameter("action");
		if (action == null) {
			Error error = new Error(405, "Unknown method");
			((HttpServletResponse) response).sendError(error.getCode(),
					error.toJSONString());
			return;
		}
		HandlerInfo handler = HandlerFactory.getInstance().getHandlerInfo(
				action);
		if (handler == null) {
			Error error = new Error(405, "Unknown method");
			((HttpServletResponse) response).sendError(error.getCode(),
					error.toJSONString());
			return;
		}
		if (!handler.isAuth())
			chain.doFilter(request, response);
		else {
			HttpSession session = ((HttpServletRequest) request).getSession();
			User user = (User) session.getAttribute("user");
			if (user == null) {
				Error error = new Error(405, "Method not authorized");
				((HttpServletResponse) response).sendError(error.getCode(),
						error.toJSONString());
				return;
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
