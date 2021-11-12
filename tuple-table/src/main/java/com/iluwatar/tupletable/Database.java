package com.iluwatar.tupletable;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Database {

  /**
   * Method for connecting to database
   */
  public Connection getConnection() throws SQLException, ClassNotFoundException, IOException {
    Class.forName("org.sqlite.JDBC");
    String url = "jdbc:sqlite::memory:test.db";
    Connection conn = DriverManager.getConnection(url);
    LOGGER.info("Connected database successfully...");
    return conn;
  }

  /**
   * Close the prepared statement and the connection in a proper way
   *
   * @param conn
   * @should close the connection and prepared statement
   */
  public void closeConnection(Connection conn) {
    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      LOGGER.error(e.getMessage());
    }
  }

  /**
   * Close the prepared statement and the connection in a proper way
   *
   * @param ps
   * @should close the connection and prepared statement
   */
  public void closeStatement(PreparedStatement ps) {
    try {
      if (ps != null) {
        ps.close();
      }
    } catch (SQLException e) {
      LOGGER.error(e.getMessage());
    }
  }
}