package servlet;

import java.io.IOException;

import dao.OrderStartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TableInfo;

@WebServlet("/OrderStartServlet")
public class OrderStartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    OrderStartDAO dao = new OrderStartDAO();
	    
	    int table_id = Integer.parseInt(request.getParameter("table_id"));
	    String table_token = dao.getTableToken(table_id);
	    String InitialState = dao.selectSessionStatus(table_token);
	    if (InitialState.equals("inactive")) {
            dao.updateSession(table_token);
        }
	    
        TableInfo tableInfo = dao.selectTableInfo(table_id, table_token);
        request.setAttribute("table_info", tableInfo);

        if (InitialState.equals("active")) {
            request.getRequestDispatcher("WEB-INF/jsp/order_start.jsp").forward(request, response);
        } else if (InitialState.equals("inactive")) {
            request.getRequestDispatcher("WEB-INF/jsp/order_start_skip.jsp").forward(request, response);
        }
	}
}
