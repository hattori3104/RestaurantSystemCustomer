package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ToppingInfo;
import util.DBUtil;

public class ToppingListDAO {

    public List<ToppingInfo> selectToppingList(int product_id) {
        List<ToppingInfo> toppingInfoList = new ArrayList<>();
        String selectToppingSql = "SELECT tp.topping_id, tp.topping_name, tp.topping_price, tp.topping_stock, tp.topping_display_flag " +
                "FROM topping tp " +
                "INNER JOIN product_topping pt ON tp.topping_id = pt.topping_id " +
                "WHERE pt.product_id = ? AND tp.topping_delete_flag = 1";
        try (Connection connection = DBUtil.getConnection();
                PreparedStatement selectStmt = connection.prepareStatement(selectToppingSql)) {

            selectStmt.setInt(1, product_id);

            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    int topping_id = resultSet.getInt("topping_id");
                    String topping_name = resultSet.getString("topping_name");
                    int topping_price = resultSet.getInt("topping_price");
                    int topping_stock = resultSet.getInt("topping_stock");
                    int topping_display_flag = resultSet.getInt("topping_display_flag");

                    toppingInfoList.add(
                     new ToppingInfo(topping_id, topping_name, topping_price, topping_stock, topping_display_flag));
                }
            }

        } catch (SQLException e) {
            System.err.println("データベースからトッピング情報の取得中にエラーが発生しました。");
            System.err.println("トッピング情報取得中にSQLエラーが発生しました: " + e.getMessage());
            System.err.println("SQL状態コード: " + e.getSQLState());
            System.err.println("エラーコード: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("トッピング情報取得中に予期しないエラーが発生しました。");
            e.printStackTrace();
        }

        return toppingInfoList;
    }
}
