package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.DBUtil;

class OrderStartDaoTest {

    private Connection connection;

    @BeforeEach
    public void init() throws SQLException, ClassNotFoundException {
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DBUtil.getConnection();
              
    }
    
//    @Test
//    void testSelectSessionStatus() {
//        fail("Not yet implemented.");
//    }

    @Test
    void testGetTableToken() throws SQLException {
        
        OrderStartDAO dao = new OrderStartDAO();
        int table_id = 3;
        String table_token = dao.getTableToken(table_id);
        
        String selectTokenSql = "SELECT url_token FROM table_sessions "
                + "WHERE table_id = ? AND session_status <> 'closed'";
        
        PreparedStatement selectStmt = connection.prepareStatement(selectTokenSql);
        selectStmt.setInt(1, table_id);
        ResultSet rs = selectStmt.executeQuery();
        String url_token = null;
        if (rs.next()) {
            url_token = rs.getString("url_token");
        }
        assertEquals(url_token, table_token);
    }

//    @Test
//    void testUpdateSession() {
//        fail("Not yet implemented.");
//    }
//
//    @Test
//    void testSelectTableInfo() {
//        fail("Not yet implemented.");
//    }

}
