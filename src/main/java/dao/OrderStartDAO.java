package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.TableInfo;
import util.DBUtil;

public class OrderStartDAO {

    public String selectSessionStatus(String url_token) {
        String selectStatusSql = "SELECT session_status FROM table_sessions WHERE url_token = ?";
        String sessionStatus = null;
        try (Connection connection = DBUtil.getConnection();
                PreparedStatement selectStmt = connection.prepareStatement(selectStatusSql)) {

            selectStmt.setString(1, url_token);

            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    sessionStatus = rs.getString("session_status");
                }
            }
        } catch (SQLException e) {
            System.err.println("OrderStateDAO: selectSessionStatus" + e.getMessage());
            e.printStackTrace();
        }

        return sessionStatus;
    }

    public String getTableToken(int table_id) {
        
        String url_token = null;
        String selectTokenSql = "SELECT url_token FROM table_sessions WHERE table_id = ?";
        
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectTokenSql)){
            selectStmt.setInt(1, table_id);
            
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    url_token = rs.getString("url_token");
                }
            }
        } catch (SQLException e) {
            System.err.println("OrderStateDAO: getTableToken" + e.getMessage());
            e.printStackTrace();
        }
        return url_token;
    }

    public void updateSession(String table_token) {

        String updateSessionSql = "UPDATE table_sessions AS s JOIN table_master AS m ON s.table_id = m.table_id SET s.session_status = 'active', s.start_time = NOW(), m.table_status = 'active', m.updated_at = NOW() WHERE s.url_token = ? AND s.session_status <> 'closed'";
        try (Connection connection = DBUtil.getConnection();
                PreparedStatement updateStmt = connection.prepareStatement(updateSessionSql)) {

            updateStmt.setString(1, table_token);
            updateStmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("OrderStateDAO: updateSession" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    public TableInfo selectTableInfo(int table_id, String url_token) {
        
        TableInfo table_info = null;
        String selectTokenSql = "SELECT session_id, session_status FROM table_sessions WHERE url_token = ?";
        
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectTokenSql)){
            selectStmt.setString(1, url_token);
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    table_info = new TableInfo(rs.getInt("session_id"), table_id, rs.getString("session_status"));
                }
            }

        } catch (SQLException e) {
            System.err.println("OrderStateDAO: selectTableInfo" + e.getMessage());
            e.printStackTrace();
        }
        return table_info;
    }

}
