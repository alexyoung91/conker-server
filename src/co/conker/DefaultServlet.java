package co.conker;

public class DefaultServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("default");
		//response.getWriter().println("session=" + request.getSession(true).getId());
	}
}