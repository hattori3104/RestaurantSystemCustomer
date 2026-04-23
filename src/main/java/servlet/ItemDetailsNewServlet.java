package servlet;

import java.io.IOException;
import java.util.List;

import dao.ToppingListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ItemInfo;
import model.ToppingInfo;

/**
 * Servlet implementation class ItemDetailsNewServlet
 */

@WebServlet("/ItemDetailsNewServlet")

public class ItemDetailsNewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	   protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        try {
	            String p_id = request.getParameter("product_id");
	            String name = request.getParameter("product_name");
	            String category = request.getParameter("category_name");
	            String p_price = request.getParameter("product_price");
	            String p_stock = request.getParameter("product_stock");

	            if (p_id == null || p_id.isEmpty()) {
	                request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
	                return;
	            }

	            int id = Integer.parseInt(p_id);
	            int price = Integer.parseInt(p_price);
	            int stock = Integer.parseInt(p_stock);

	            ItemInfo itemInfo = new ItemInfo(id, name, category, price, stock);

	            ToppingListDAO dao = new ToppingListDAO();
	            List<ToppingInfo> toppingInfo = dao.selectToppingList(id);

	            request.setAttribute("item_info", itemInfo);
	            request.setAttribute("topping_info", toppingInfo);

	            request.getRequestDispatcher("WEB-INF/jsp/item_details.jsp").forward(request, response);
	        } catch (Exception e) {
	            //ログに出力
	            e.printStackTrace();
	            request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
	        }
	    }

}
