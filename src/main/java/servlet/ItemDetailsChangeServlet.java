package servlet;

import java.io.IOException;
import java.util.List;

import dao.ToppingListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ItemChangeInfo;
import model.ToppingInfo;

/**
 * Servlet implementation class ItemDetailsChangeServlet
 */

@WebServlet("/ItemsDetailChangeServlet")
public class ItemDetailsChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        
            //パラメータを取得
            String o_id = request.getParameter("order_id");
            String p_id = request.getParameter("product_id");
            String product_name = request.getParameter("product_name");
            String category_name = request.getParameter("category_name");
            String p_price = request.getParameter("product_price");
            String[] t_id = request.getParameterValues("topping_id[]");
            String[] topping_name = request.getParameterValues("topping_name[]");
            String[] t_price = request.getParameterValues("topping_price[]");
            String[] t_quantity = request.getParameterValues("topping_quantity[]");
            String[] t_stock = request.getParameterValues("topping_stock");
            String subtotalstr = request.getParameter("subtotal");

            if (o_id == null || o_id.isEmpty() || p_id == null || p_id.isEmpty() ) {
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            //int型への変換処理
            int order_id = Integer.parseInt(o_id);
            int product_id = Integer.parseInt(p_id);
            int product_price = Integer.parseInt(p_price);
            int subtotal = Integer.parseInt(subtotalstr);

            int[] topping_id;
            int[] topping_price;
            int[] topping_quantity;
            int[] topping_stock;
            //int型[]への変換処理
            topping_id = safeParseIntArray(t_id);
            topping_price = safeParseIntArray(t_price);
            topping_quantity = safeParseIntArray(t_quantity);
            topping_stock= safeParseIntArray(t_stock);

            //詳細変更リストオブジェクトの作成
            ItemChangeInfo itemInfo = new ItemChangeInfo(order_id, product_id, product_name, category_name, product_price,
                    topping_id, topping_name, topping_price, topping_quantity, topping_stock, subtotal);

            // トッピング情報の取得
            ToppingListDAO dao = new ToppingListDAO();
            List<ToppingInfo> toppingInfo = dao.selectToppingList(product_id);

            request.setAttribute("details_change", itemInfo);
            request.setAttribute("topping_info", toppingInfo);

            request.getRequestDispatcher("item_details_change.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
