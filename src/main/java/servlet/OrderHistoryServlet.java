package servlet;

import java.io.IOException;
import java.util.List;

import dao.OrderHistoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrderHistoryInfo;

/**
 * Servlet implementation class OrderHistoryServlet
 */

@WebServlet("/OrderHistoryServlet")
public class OrderHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
          
            HttpSession session = request.getSession();
            
//            String sessionNumberStr = (String) session.getAttribute("session_id");
//            if (sessionNumberStr == null || sessionNumberStr.isEmpty()) {
//                request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
//                return;
//            }

            int sessionId = (int) session.getAttribute("session_id");

            OrderHistoryDAO dao = new OrderHistoryDAO();
            List<OrderHistoryInfo> orderHistoryInfo = dao.selectOrderHistory(sessionId);

            if (orderHistoryInfo == null || orderHistoryInfo.isEmpty()) {
                System.out.println("注文履歴が見つかりませんでした。(sessionId=" + sessionId + ")");
            }

            request.setAttribute("order_history_info", orderHistoryInfo);
            request.getRequestDispatcher("WEB-INF/jsp/order_history.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
        }
	}

}
