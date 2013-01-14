package farmapp.service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import farmapp.model.Error;
import farmapp.service.handlers.Handler;
import farmapp.service.handlers.HandlerException;
import farmapp.service.handlers.HandlerFactory;
import farmapp.service.handlers.MissingRequiredParameter;

/**
 * Servlet implementation class ServiceServlet
 */
@WebServlet("/ServiceServlet")
public class ServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServiceServlet() {
        // TODO Auto-generated constructor stub
    	super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
		// TODO Auto-generated method stub
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response ) throws IOException{
		Handler handler = null;
		try {
			handler = HandlerFactory.getInstance().createHandler(
					request.getParameter("action"));
		} catch (HandlerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/result.jsp");
			request.setAttribute("result", handler.process(request));
			rd.forward(request, response);
		} catch (MissingRequiredParameter e) {
			Error error = new Error(400,
					"Missing required parameters for action "
							+ request.getParameter("action"));
			if (request.getParameter("callback") != null) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher(
						"/result.jsp");
				request.setAttribute("result", "{\"status\":\"KO\", \"result\":" + error.toJSONString() + "}");
				try {
					rd.forward(request, response);
				} catch (ServletException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else
				response.sendError(error.getCode(), error.toJSONString());
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

}
