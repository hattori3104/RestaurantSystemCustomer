package servlet;

import java.io.IOException;

import dao.AccountingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.AccountingInfo;

/**
 * Servlet implementation class AccountingServlet
 */

@WebServlet("/AccountingServlet")
public class AccountingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
            HttpSession session = request.getSession();

//            String sessionNumberStr = (String) session.getAttribute(ServletUtil.Param.SESSION_ID);
//            String strTableNo = request.getParameter(ServletUtil.Param.TABLE_ID); // テーブル番号
//            String strTotalPrice = request.getParameter(ServletUtil.Param.TOTAL); // 総額

//            int sessionId = ServletUtil.parseOrDefault(sessionNumberStr, 0);
//            int totalPrice = ServletUtil.parseOrDefault(strTotalPrice, 0);
//            int tableNo = ServletUtil.parseOrForward(strTableNo, request, response);

            int session_id = (int)session.getAttribute("session_id");
            String sessionId = String.valueOf(session_id);
            String tableId = (String)session.getAttribute("table_id");
            int table_id = Integer.parseInt(tableId);
            String totalPrice = request.getParameter("total");
            int total_price = Integer.parseInt(totalPrice);
            
            AccountingDAO dao = new AccountingDAO();
            dao.processAccountingData(session_id, total_price);
            AccountingInfo accountingInfo = new AccountingInfo(table_id, total_price);

            // 卓番セッション
            dao.processSession(table_id, sessionId);
            System.out.println("現在の画面：会計確定画面");

            // セッションの取得と情報削除
            request.getSession().invalidate();

            // JSPへフォワード
            request.setAttribute("accounting_info", accountingInfo);
            request.getRequestDispatcher("WEB-INF/jsp/accounting.jsp").forward(request, response);
        } catch (Exception e) {
            //ログに出力
            e.printStackTrace();

            //エラー画面に戻す
            request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
        }
	}

}
