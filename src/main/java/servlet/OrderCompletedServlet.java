package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import dao.OrderCompletedDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.DBUtil;

/**
 * Servlet implementation class OrderCompletedServlet
 */

@WebServlet("/OrderCompletedServlet")
public class OrderCompletedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {

            String[] order_ids = request.getParameterValues("order_id_arr");
            String[] product_id = request.getParameterValues("product_id_arr");
            String[] product_quantity = request.getParameterValues("product_quantity_arr");
            String[][] topping_ids = new String[order_ids.length][];
            String[][] topping_quantities = new String[order_ids.length][];
            String[] order_price = request.getParameterValues("subtotal_arr");
            
            for (int i = 0; i < order_ids.length; i++) {
                topping_ids[i] = request.getParameterValues("topping_id_" + i);
                String[] topping_quantity = request.getParameterValues("topping_quantity_" + i);

                if (topping_quantity != null) {
                    topping_quantities[i] = Arrays.copyOf(topping_quantity, topping_quantity.length);
                } else {
                    topping_quantities[i] = new String[0]; // トッピングなしの場合は空配列
                    System.out.println("トッピングがありません");
                }

                if (topping_ids[i] == null) {
                    topping_ids[i] = new String[0]; // トッピングが存在しない場合も空配列
                    System.out.println("トッピングが存在しません");
                }
            }

            HttpSession session = request.getSession();

//            String sessionNumber = (String) session.getAttribute("session_id");
//            int sessionId = 0;
//            if (sessionNumber != null || !sessionNumber.isEmpty()) {
//                try {
//                    sessionId = Integer.parseInt(sessionNumber);
//                } catch (NumberFormatException e) {
//                    System.out.println("無効な数値: " + sessionNumber);
//                    request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
//                    throw new IOException("数値変換エラー");
//                }
//            } else {
//                request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
//                return;
//            }
            int sessionId = (int)session.getAttribute("session_id");

            boolean success = completedOrder(product_id, product_quantity, order_price, 
                    topping_ids, topping_quantities, sessionId);

            if (success) {
                request.getSession().removeAttribute("orderListInfo");
                request.getRequestDispatcher("WEB-INF/jsp/order_completed.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
        }
	}

    private boolean completedOrder(String[] productIds, String[] productQuantities, String[] productPrices,
            String[][] toppingIds, String[][] toppingQuantities, int sessionId) {
        Connection connection = null;
        try {
            OrderCompletedDAO dao = new OrderCompletedDAO();
            
            connection = DBUtil.getConnection();
            connection.setAutoCommit(false); // トランザクション開始

            // 1. 商品詳細を登録
            List<Integer> orderIds = dao.insertProductDetails(connection, productIds);

            // 2. 注文詳細を登録
            boolean orderInserted = dao.insertOrderDetails(
                    connection,
                    orderIds.stream().map(String::valueOf).toArray(String[]::new),
                    productQuantities,
                    productPrices,
                    sessionId);

            // 3. トッピング詳細を登録
            boolean toppingInserted = dao.insertToppingDetails(
                    connection, toppingIds, toppingQuantities,
                    orderIds.stream().map(String::valueOf).toArray(String[]::new));

            // 4. 在庫を更新
            dao.updateProductStock(connection, productIds, productQuantities);
            dao.updateToppingStock(
                    connection,
                    Arrays.stream(toppingIds).flatMap(Arrays::stream).toArray(String[]::new),
                    Arrays.stream(toppingQuantities).flatMap(Arrays::stream).toArray(String[]::new));

            // すべて成功したら commit
            connection.commit();
            return orderInserted && toppingInserted;

        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // エラーならロールバック
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close(); // 必ず閉じる
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }
}
