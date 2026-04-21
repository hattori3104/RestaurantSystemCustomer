package servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import dao.OrderMenuDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ProductInfo;

/**
 * Servlet implementation class OrderMenuServlet
 */

@WebServlet("/OrderMenuServlet")
public class OrderMenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final List<String> CATEGORY_LIST = Arrays.asList(
            "お好み焼き", "もんじゃ焼き", "鉄板焼き", "サイドメニュー", "ソフトドリンク", "お酒", "ボトル");
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    try {
//        HttpSession session = request.getSession();
//        String session_id_string = (String) session.getAttribute("session_id");
//        int session_id = Integer.parseInt(session_id_string);
//        int table_id = (int)session.getAttribute("table_id");

        OrderMenuDAO dao = new OrderMenuDAO();
        List<ProductInfo> productInfoList = dao.selectProductList();

//        for (int i = 0; i<productInfoList.size(); i++) {
//            System.out.println(productInfoList.get(i));
//        }
        request.setAttribute("product_info_list", productInfoList);
        request.setAttribute("product_category", CATEGORY_LIST);

        request.getRequestDispatcher("WEB-INF/jsp/order_menu.jsp").forward(request, response);
        
    } catch (Exception e) {
        e.printStackTrace();
        request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
    }
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    try {
//    	    System.out.println("session_id: " + request.getParameter("session_id"));
//    	    System.out.println("table_id: " + request.getParameter("table_id"));
//    	    System.out.println("session_status: " + request.getParameter("session_status"));
//    	    System.out.println("guest_count: " + request.getParameter("guest_count"));
    	
    	    String session_id_string = request.getParameter("session_id");
    	    int session_id = Integer.parseInt(session_id_string);
            String table_id = request.getParameter("table_id");
            String session_status = request.getParameter("session_status");
            String form = request.getParameter("form");
    
            HttpSession session = request.getSession();
            session.setAttribute("session_id", session_id);
            session.setAttribute("table_id", table_id);
            session.setAttribute("session_status", session_status);
            session.setAttribute("form", form);
            
            OrderMenuDAO dao = new OrderMenuDAO();
            if (form.equals("order_start.jsp")) {
                int guest_count = Integer.parseInt(request.getParameter("guest_count"));
                session.setAttribute("guest_count", guest_count);
                dao.updateGuestCount(guest_count, session_id);
            }
    
            response.sendRedirect(request.getContextPath() + "/OrderMenuServlet");

	    } catch (Exception e) {
	        e.printStackTrace();
	        request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
	    }
	}
}
