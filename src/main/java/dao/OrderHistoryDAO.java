package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.MultipleToppingInfo;
import model.OrderHistoryInfo;
import util.DBUtil;

public class OrderHistoryDAO {

    public List<OrderHistoryInfo> selectOrderHistory(int session_id) {
        List<OrderHistoryInfo> orderHistoryInfoList = new ArrayList<>();
        Map<Integer, OrderHistoryInfo> orderHistoryMap = new HashMap<>();

        String selectHistorySql = "SELECT o.order_id, o.product_quantity, o.order_price, o.order_flag, " +
                "p.product_name, m.topping_quantity, t.topping_name " +
                "FROM order_details o " +
                "JOIN product_details d ON o.order_id = d.order_id " +
                "JOIN product p ON d.product_id = p.product_id " +
                "LEFT JOIN multiple_toppings m ON o.order_id = m.order_id " +
                "LEFT JOIN topping t ON m.topping_id = t.topping_id " +
                "WHERE o.session_id = ? AND o.accounting_flag = 0";

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement selectStmt = connection.prepareStatement(selectHistorySql)) {

            selectStmt.setInt(1, session_id);

            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    int order_id = resultSet.getInt("order_id");
                    int product_quantity = resultSet.getInt("product_quantity");
                    int order_price = resultSet.getInt("order_price");
                    int order_flag = resultSet.getInt("order_flag");
                    String product_name = resultSet.getString("product_name");
                    int topping_quantity = resultSet.getInt("topping_quantity");
                    String topping_name = resultSet.getString("topping_name");

                    // すでに同じ order_id の OrderHistoryInfo があるか確認
                    OrderHistoryInfo orderInfo = orderHistoryMap.get(order_id);
                    if (orderInfo == null) {
                        orderInfo = new OrderHistoryInfo(
                                order_price,
                                order_flag,
                                product_name,
                                product_quantity,
                                new ArrayList<>());
                        orderHistoryMap.put(order_id, orderInfo);
                        orderHistoryInfoList.add(orderInfo);
                    }

                    // トッピングがある場合は追加
                    if (topping_name != null) {
                        orderInfo.addTopping(new MultipleToppingInfo(topping_name, topping_quantity));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("データベースの注文情報取得中にエラーが発生しました。");
            System.err.println("注文情報取得中にSQLエラーが発生しました: " + e.getMessage());
            System.err.println("SQL状態コード: " + e.getSQLState());
            System.err.println("エラーコード: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("注文情報取得中に予期しないエラーが発生しました。");
            e.printStackTrace();
        }

        return orderHistoryInfoList;
    }
}
