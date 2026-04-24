package servlet;

import java.io.IOException;
import java.util.List;

import dao.ToppingListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ChangedItemInfo;
import model.ToppingInfo;

/**
 * Servlet implementation class ItemDetailsChangeServlet
 */

@WebServlet("/ItemDetailsChangeServlet")
public class ItemDetailsChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        
            String o_id = request.getParameter("order_id");
            String p_id = request.getParameter("product_id");
            String product_name = request.getParameter("product_name");
            String category_name = request.getParameter("category_name");
            String p_price = request.getParameter("product_price");
            String[] t_id = request.getParameterValues("topping_id_arr");
            String[] topping_name = request.getParameterValues("topping_name_arr");
            String[] t_price = request.getParameterValues("topping_price_arr");
            String[] t_quantity = request.getParameterValues("topping_quantity_arr");
            String[] t_stock = request.getParameterValues("topping_stock_arr");
            String subtotalstr = request.getParameter("subtotal");
            
            if (o_id == null || o_id.isEmpty() || p_id == null || p_id.isEmpty() ) {
                request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
                return;
            }

            int order_id = Integer.parseInt(o_id);
            int product_id = Integer.parseInt(p_id);
            int product_price = Integer.parseInt(p_price);
            int subtotal = Integer.parseInt(subtotalstr);

            int[] topping_id;
            int[] topping_price;
            int[] topping_quantity;
            int[] topping_stock;
            topping_id = safeParseIntArray(t_id);
            topping_price = safeParseIntArray(t_price);
            topping_quantity = safeParseIntArray(t_quantity);
            topping_stock= safeParseIntArray(t_stock);

            ChangedItemInfo itemInfo = new ChangedItemInfo(order_id, product_id, product_name, category_name, product_price,
                    topping_id, topping_name, topping_price, topping_quantity, topping_stock, subtotal);

            ToppingListDAO dao = new ToppingListDAO();
            List<ToppingInfo> toppingInfo = dao.selectToppingList(product_id);

            request.setAttribute("item_info", itemInfo);
            request.setAttribute("topping_info", toppingInfo);

            request.getRequestDispatcher("WEB-INF/jsp/item_details.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
        }
	}

    public static int[] safeParseIntArray(String[] arr) {
        if (arr == null || arr.length == 0)
            return new int[0];
        return java.util.Arrays.stream(arr)
                .mapToInt(s -> Integer.parseInt(s))
                .toArray();
    }
}
