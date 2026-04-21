package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ProductInfo;
import util.DBUtil;

public class OrderMenuDAO {

    public List<ProductInfo> selectProductList() {
        List<ProductInfo> productInfoList = new ArrayList<>();
        String selectProductSql = "SELECT * FROM product WHERE product_delete_flag = 1";
        
        try (Connection connection = DBUtil.getConnection();
                PreparedStatement selectStmt = connection.prepareStatement(selectProductSql)) {
            
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    int product_id = resultSet.getInt("product_id");
                    String product_name = resultSet.getString("product_name");
                    String category_name = resultSet.getString("category_name");
                    int product_price = resultSet.getInt("product_price");
                    int product_stock = resultSet.getInt("product_stock");
                    int product_display_flag = resultSet.getInt("product_display_flag");
                    ProductInfo info = new ProductInfo(product_id, product_name, category_name, 
                            product_price, product_stock, product_display_flag);
                    productInfoList.add(info);
                }
            }
        } catch (SQLException e) {
            System.err.println("OrderMenuDAO: selectProductInfoList" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productInfoList;
    }

    public void updateGuestCount(int guest, int sessionId) {
        String updateGuestSql = "UPDATE table_sessions SET guest_count = ? WHERE session_id = ?";
        try (Connection connection = DBUtil.getConnection();
                PreparedStatement updateStmt = connection.prepareStatement(updateGuestSql)) {

            updateStmt.setInt(1, guest);
            updateStmt.setInt(2, sessionId);

            updateStmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("データベースの人数情報更新中にエラーが発生しました。");
            System.err.println("人数情報更新中にSQLエラーが発生しました: " + e.getMessage());
            System.err.println("SQL状態コード: " + e.getSQLState());
            System.err.println("エラーコード: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
