package servlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrderListInfo;

@WebServlet("/OrderListServlet")
public class OrderListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // セッションから orderList を取得（なければ新規作成）
            HttpSession session = request.getSession();
            List<OrderListInfo> orderList = (List<OrderListInfo>) session.getAttribute("order_list");
            if (orderList == null) {
                orderList = new ArrayList<>();
            }

            // 遷移元ページ名を取得
            String previousState = "";
            String url = request.getHeader("REFERER");
            if (url != null) {
                try {
                    URL parsedUrl = new URL(url);
                    String path = parsedUrl.getPath();
                    previousState = path.substring(path.lastIndexOf('/') + 1);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
//            System.out.println("previousState: "+previousState);
            
            // 「追加ページ」の場合
            if (previousState.equals("ItemDetailsNewServlet")) {

                int maxOrderId = orderList.stream().mapToInt(OrderListInfo::getOrder_id).max().orElse(0);
                int order_id = maxOrderId + 1;

                String product_name = request.getParameter("product_name");
                String category_name = request.getParameter("category_name");
//                System.out.println("Here:"+product_name+" "+category_name);
                int product_price_int = Integer.parseInt(request.getParameter("product_price"));
                int menu_quantity = 1;
                int menu_stock_int = 0;
                if (request.getParameter("product_stock") != null) {
                    menu_stock_int = Integer.parseInt(request.getParameter("product_stock"));
                }

                // トッピング情報
                int[] topping_id_arr = safeParseIntArray(request.getParameterValues("topping_id"));
                String[] topping_name_arr = request.getParameterValues("topping_name");
//                for (int i = 0; i < topping_name_arr.length; i++) {
//                    System.out.println("topping_name: "+topping_name_arr[i]);
//                }
                int[] topping_price_arr = safeParseIntArray(request.getParameterValues("topping_price"));
                int[] topping_quantity_arr = safeParseIntArray(request.getParameterValues("topping_quantity"));
                int[] topping_stock_arr = safeParseIntArray(request.getParameterValues("topping_stock"));

                // menu_subtotal 計算（商品価格 + トッピング合計）
                int subtotal = product_price_int;
                if (topping_id_arr != null) {
                    for (int i = 0; i < topping_id_arr.length; i++) {
                        subtotal += topping_price_arr[i] * topping_quantity_arr[i];
                    }
                }
//                System.out.println("subtotal:"+subtotal);

                // OrderListInfo 作成して追加
                orderList.add(new OrderListInfo(order_id,
                        Integer.parseInt(request.getParameter("product_id")),
                        product_name,
                        category_name,
                        product_price_int,
                        menu_quantity,
                        menu_stock_int,
                        toList(topping_id_arr),
                        toList(topping_name_arr),
                        toList(topping_price_arr),
                        toList(topping_quantity_arr),
                        toList(topping_stock_arr),
                        subtotal // menu_subtotal に設定
                ));

            } else if (previousState.equals("ItemDetailsChangeServlet")) {
                // 「変更ページ」の場合
                int order_id = Integer.parseInt(request.getParameter("order_id"));
                int product_id = Integer.parseInt(request.getParameter("product_id"));
                int product_price = Integer.parseInt(request.getParameter("product_price"));

                int[] topping_id_arr = safeParseIntArray(request.getParameterValues("topping_id_arr"));
                String[] topping_name_arr = request.getParameterValues("topping_name_arr");
                int[] topping_price_arr = safeParseIntArray(request.getParameterValues("topping_price_arr"));
                int[] topping_quantity_arr = safeParseIntArray(request.getParameterValues("topping_quantity_arr"));
                int[] topping_stock_arr = safeParseIntArray(request.getParameterValues("topping_stock_arr"));

                for (OrderListInfo order : orderList) {
                    if (order.getOrder_id() == order_id && order.getProduct_id() == product_id) {
                        order.setTopping_id(toList(topping_id_arr));
                        order.setTopping_name(toList(topping_name_arr));
                        order.setTopping_price(toList(topping_price_arr));
                        order.setTopping_quantity(toList(topping_quantity_arr));
                        order.setTopping_stock(toList(topping_stock_arr));

                        // menu_subtotal 更新
                        int subtotal = product_price;
                        if (topping_id_arr != null) {
                            for (int i = 0; i < topping_id_arr.length; i++) {
                                subtotal += topping_price_arr[i] * topping_quantity_arr[i];
                            }
                        }
//                        System.out.println("subtotal:"+subtotal);
                        order.setMenu_subtotal(subtotal);
                    }
                }
            }

            session.setAttribute("order_list", orderList);
            request.getRequestDispatcher("WEB-INF/jsp/order_list.jsp").forward(request, response);
            
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

    public static List<Integer> toList(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int v : arr)
            list.add(v);
        return list;
    }

    public static List<String> toList(String[] arr) {
        List<String> list = new ArrayList<>();
        if (arr != null) {
            for (String s : arr)
                list.add(s);
        }
        return list;
    }
}
