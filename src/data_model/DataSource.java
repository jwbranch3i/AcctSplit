package data_model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource
{
	public static final String DB_NAME = "accounts.db";
	public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\VSCwork\\Acct\\"
					+ DB_NAME;
	// public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\VSCwork\\Acct\\"
	// 				+ DB_NAME;

	private static Connection conn;

	//private static final PreparedStatement ps_updateFieldDate;

	private static DataSource instance = new DataSource();

	/****************************************************************/
	private DataSource()
	{
	}

	/****************************************************************/
	public static DataSource getInstance()
	{
		return instance;
	}

	public static Connection getConn()
	{
		return conn;
	}

	/******************************************************************/
	public boolean open()
	{
		try
		{
			conn = DriverManager.getConnection(CONNECTION_STRING);
			
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Could not connect to database: " + e.getMessage());
			return false;
		}
	}

	/*******************************************************************/
	public void close()
	{
		try
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		catch (SQLException e)
		{
			System.out.println("Could not close connection" + e.getMessage());
		}

	}

}
