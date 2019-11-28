package emyu.learning.models;

import emyu.learning.AppConstants;

import java.sql.*;

public class Db {
    protected Connection conn;
    protected Statement statement;
    protected PreparedStatement preparedStatement;
    protected ResultSet rs;

    private void connect() throws SQLException {
        conn = DriverManager.getConnection(AppConstants.DB_URL);
    }

    protected void prepare(String sql) throws SQLException{
        if (conn == null) {
            connect();
        }
        preparedStatement = conn.prepareStatement(sql);
    }

    protected void setInt(int index, int val) throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.setInt(index, val);
        }
    }

    protected void setString(int index, String val) throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.setString(index, val);
        }
    }

    protected void setLong(int index, long val) throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.setLong(index, val);
        }
    }

    protected int executeUpdate() throws SQLException {
        if (preparedStatement != null) {
            return preparedStatement.executeUpdate();
        }
        return -1;
    }
}
