package com.logistics.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnectionRest {
	public Connection getConnect() throws ClassNotFoundException, SQLException {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/logistics", "root", "root123");

			/*
			 * InitialContext ctx = null; ctx = new InitialContext(); DataSource ds =
			 * (DataSource) ctx.lookup("jboss/PMO"); //changing code as per thr datasource
			 * in dev
			 * 
			 * connection = ds.getConnection();
			 */
		} catch (SQLException sqlException) {
			// TODO: handle exception
			System.err.println("Exception caught " + sqlException.getMessage());
		} catch (Exception exception) {
			// TODO Auto-generated catch block
			System.err.println("Exception caught " + exception.getMessage());
			exception.printStackTrace();
		}

		return connection;
	}

	public static void closeQuietly(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Exception caught " + e.getMessage());
		}

	}

	public static void closeQuietly(PreparedStatement preparedStatement) {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Exception caught " + e.getMessage());
		}

	}

	public static void closeQuietly(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Exception caught " + e.getMessage());
		}

	}

	public static void closeQuietly(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Exception caught " + e.getMessage());
		}

	}

}
