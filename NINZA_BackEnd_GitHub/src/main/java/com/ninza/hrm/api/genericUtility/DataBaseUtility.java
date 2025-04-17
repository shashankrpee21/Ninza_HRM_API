package com.ninza.hrm.api.genericUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class DataBaseUtility {

	static Connection con = null;
	static ResultSet resultSet = null;
	static FileUtility flib = new FileUtility();

	public void connectionToDB() throws Throwable {
		Driver driverRef;
		try {
			driverRef = new Driver();
			DriverManager.registerDriver(driverRef);
			//con = DriverManager.getConnection(flib.getDataFromPropertiesFile("Db_Url"), flib.getDataFromPropertiesFile("Db_UN"), flib.getDataFromPropertiesFile("Db_Pwd"));

			con = DriverManager.getConnection(IPathConstants.dbURL, IPathConstants.dbUN, IPathConstants.dbPWD);
			System.out.println("----Connection Successful----");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeDBConnection() throws SQLException {
		try {
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean executeQueryVerifyAndGetData(String query, int columnIndex, String expectedData) throws Throwable {
		boolean flag = false;
		resultSet = con.createStatement().executeQuery(query);

		while (resultSet.next()) {
			if (resultSet.getString(columnIndex).equals(expectedData)) {
				flag = true;
				break;
			}
		}

		if (flag) {
			System.out.println(expectedData + " ===> Data verified in data base table");
			return true;
		} else {
			System.out.println(expectedData + " ===> Data not verified in data base table");
			return false;
		}
	}

	public ResultSet executeSelectQuery(String query) {
		try {
			Statement stat = con.createStatement();
			resultSet = stat.executeQuery(query);
			return resultSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}

}