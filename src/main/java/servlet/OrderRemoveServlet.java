package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrderListInfo;

/**
 * Servlet implementation class OrderRemoveServlet
 */

@WebServlet("/OrderRemoveServlet")
public class OrderRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    try {
            HttpSession session = request.getSession();
            List<OrderListInfo> orderList = (List<OrderListInfo>) session.getAttribute("order_list");
            int order_id = Integer.valueOf(request.getParameter("order_id"));
            //削除対象のデバッグ確認
            System.out.println("削除対象：" + order_id + "目の注文");
            if (null == orderList) {
                orderList = new ArrayList<>();
            }
            //セッションから対象のorder_idを削除する
            if (orderList != null) {

                Iterator<OrderListInfo> iterator = orderList.iterator();
                while (iterator.hasNext()) {
                    OrderListInfo order = iterator.next();
                    if (order.getOrder_id() == order_id) {
                        iterator.remove();
                        break;
                    }
                }

                session.setAttribute("order_list", orderList);
            }

//            response.sendRedirect(request.getContextPath() + "/WEB-INF/jsp/order_list.jsp");
            request.getRequestDispatcher("WEB-INF/jsp/order_list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
        }
	}
}
