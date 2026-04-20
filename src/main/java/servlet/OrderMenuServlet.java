package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OrderMenuServlet
 */
public class OrderMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    System.out.println("session_id: " + request.getParameter("session_id"));
	    System.out.println("table_id: " + request.getParameter("table_id"));
	    System.out.println("session_status: " + request.getParameter("session_status"));
	    System.out.println("guest_count: " + request.getParameter("guest_count"));
	}

}
