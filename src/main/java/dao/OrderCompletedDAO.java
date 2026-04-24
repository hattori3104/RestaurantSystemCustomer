package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderCompletedDAO {

    public List<Integer> insertProductDetails(Connection connection, String[] product_ids) {
        List<Integer> generatedOrderIds = new ArrayList<>();
        String insertProductDetailsSql = "INSERT INTO product_details (product_id) VALUES (?)";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertProductDetailsSql, Statement.RETURN_GENERATED_KEYS)) {

            for (String product_id : product_ids) {
                insertStmt.setInt(1, Integer.parseInt(product_id));
                insertStmt.executeUpdate();

                try (ResultSet resultSet = insertStmt.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        int generatedId = resultSet.getInt(1);
                        generatedOrderIds.add(generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("データベースに商品詳細情報登録中にエラーが発生しました。");
            System.err.println("商品詳細情報登録中にSQLエラーが発生しました: " + e.getMessage());
            System.err.println("SQL状態コード: " + e.getSQLState());
            System.err.println("エラーコード: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("商品詳細情報登録中に予期しないエラーが発生しました。");
            e.printStackTrace();
        }

        return generatedOrderIds;
    }

    //注文詳細挿入
    public boolean insertOrderDetails(Connection connection, String[] order_id, String[] product_quantity, String[] order_price, int session_id) {
        String insertOrderDetailsSql = "INSERT INTO order_details (order_id, product_quantity, order_price, session_id, order_time) VALUES (?, ?, ?, ?, ?)";
        int[] rowsAffected = new int[0];
        try (PreparedStatement insertStmt = connection.prepareStatement(insertOrderDetailsSql)) {

            for (int i = 0; i < order_id.length; i++) {
                insertStmt.setInt(1, Integer.parseInt(order_id[i]));
                insertStmt.setInt(2, Integer.parseInt(product_quantity[i]));
                insertStmt.setDouble(3, Double.parseDouble(order_price[i]));
                insertStmt.setInt(4, session_id);
                insertStmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));

                insertStmt.addBatch();
            }

            rowsAffected = insertStmt.executeBatch();

        } catch (SQLException e) {
            System.err.println("データベースに注文詳細情報登録中にエラーが発生しました。");
            System.err.println("注文詳細情報登録中にSQLエラーが発生しました: " + e.getMessage());
            System.err.println("SQL状態コード: " + e.getSQLState());
            System.err.println("エラーコード: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("注文詳細情報登録中に予期しないエラーが発生しました。");
            e.printStackTrace();
        }

        boolean success = Arrays.stream(rowsAffected).anyMatch(count -> count > 0);
        return success;
    }

    //トッピング詳細挿入
    public boolean insertToppingDetails(Connection connection, String[][] topping_id, String[][] topping_quantity, String[] order_id) {
        String insertToppingDetailsSql = "INSERT INTO multiple_toppings (topping_id, topping_quantity, order_id) VALUES (?, ?, ?)";
        int[] rowsAffected = new int[0];
        try (PreparedStatement insertStmt = connection.prepareStatement(insertToppingDetailsSql)) {

            for (int i = 0; i < order_id.length; i++) {
                for (int j = 0; j < topping_id[i].length; j++) {
                    int toppingQuantity = Integer.parseInt(topping_quantity[i][j]);

                    if (toppingQuantity == 0) {
                        continue;
                    }
                    insertStmt.setInt(1, Integer.parseInt(topping_id[i][j]));
                    insertStmt.setInt(2, toppingQuantity);
                    insertStmt.setInt(3, Integer.parseInt(order_id[i]));

                    insertStmt.addBatch();
                }
            }

            rowsAffected = insertStmt.executeBatch();

        } catch (SQLException e) {
            System.err.println("データベースにトッピング詳細情報登録中にエラーが発生しました。");
            System.err.println("トッピング詳細情報登録中にSQLエラーが発生しました: " + e.getMessage());
            System.err.println("SQL状態コード: " + e.getSQLState());
            System.err.println("エラーコード: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("トッピング詳細情報登録中に予期しないエラーが発生しました。");
            e.printStackTrace();
        }
        boolean success = rowsAffected.length == 0 || Arrays.stream(rowsAffected).anyMatch(count -> count > 0);
        return success;
    }

    //商品在庫更新
    public void updateProductStock(Connection connection, String[] product_ids, String[] product_quantities) {
        String updateStockSql = "UPDATE product SET product_stock = product_stock - ? WHERE product_id = ? AND product_stock >= ?";
        try (PreparedStatement updateStmt = connection.prepareStatement(updateStockSql)) {
            for (int i = 0; i < product_ids.length; i++) {
                int product_id = Integer.parseInt(product_ids[i]);
                int quantity = Integer.parseInt(product_quantities[i]);

                if (quantity <= 0) {
                    continue;
                }

                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, product_id);
                updateStmt.setInt(3, quantity);
                updateStmt.addBatch();
            }

            int[] updateCounts = updateStmt.executeBatch();
            System.out.println("更新件数: " + java.util.Arrays.toString(updateCounts));

        } catch (SQLException e) {
            System.err.println("データベースの商品在庫更新中にエラーが発生しました。");
            System.err.println("商品在庫更新中にSQLエラーが発生しました: " + e.getMessage());
            System.err.println("SQL状態コード: " + e.getSQLState());
            System.err.println("エラーコード: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("商品在庫更新中に予期しないエラーが発生しました。");
            e.printStackTrace();
        }
    }

    //トッピング在庫更新
    public void updateToppingStock(Connection connection, String[] topping_ids, String[] topping_quantities) {
        String updateStockSql = "UPDATE topping SET topping_stock = topping_stock - ? WHERE topping_id = ? AND topping_stock >= ?";
        try (PreparedStatement updateStmt = connection.prepareStatement(updateStockSql)) {
            for (int i = 0; i < topping_ids.length; i++) {
                int topping_id = Integer.parseInt(topping_ids[i]);
                int quantity = Integer.parseInt(topping_quantities[i]);

                if (quantity <= 0) {
                    continue;
                }

                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, topping_id);
                updateStmt.setInt(3, quantity);
                updateStmt.addBatch();
            }

            int[] updateCounts = updateStmt.executeBatch();
            System.out.println("更新件数: " + java.util.Arrays.toString(updateCounts));

        } catch (SQLException e) {
            System.err.println("データベースのトッピング在庫更新中にエラーが発生しました。");
            System.err.println("トッピング在庫更新中にSQLエラーが発生しました: " + e.getMessage());
            System.err.println("SQL状態コード: " + e.getSQLState());
            System.err.println("エラーコード: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("トッピング在庫更新中に予期しないエラーが発生しました。");
            e.printStackTrace();
        }
    }
}
