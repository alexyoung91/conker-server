public class ProjectImageServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("projectImage");
		//response.getWriter().println("session=" + request.getSession(true).getId());
	}
}