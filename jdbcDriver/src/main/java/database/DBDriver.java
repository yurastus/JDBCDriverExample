package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.mysql.jdbc.ResultSetMetaData;

public class DBDriver {
	
	private Connection conn = null;
	private String url = "jdbc:mysql://localhost:3306/JAVA_DATA";
	private String user = "user";
	private String passwd = "user";
	
	
	public void getCollectionTablevalue (String table) throws SQLException {
		String query = String.format("SELECT %s FROM %s", "*", table);
		try {
			conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			
			ResultSet res = null;
			ArrayList<HashMap<String, Object>> arr;

			boolean isResult = state.execute(query);
			if (isResult)
				res = state.getResultSet();
			
			ResultSetMetaData meta = (ResultSetMetaData) res.getMetaData();
			int columnCount = meta.getColumnCount();
			
			ArrayList<String> colls = new ArrayList<String>();
			ArrayList<HashMap<String,Object>> Rows = new ArrayList<HashMap<String,Object>>();
			for (int i = 1;i<=columnCount;i++) {
				colls.add(meta.getColumnName(i));
			}
			for (String coll :colls) {
				
				
				while (res.next()) {
					HashMap<String,Object> Row = new HashMap<String,Object>();
					for (String colName : colls) {
						Object value = res.getObject(colName);
						Row.put(colName, value);		
					}
					Rows.add(Row);
				}
			}
			System.out.println("\nRows:\t" + Rows);
			System.out.print(Rows.get(1).get("Address"));
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			conn.close();
		}
}

	
	
	public void getListOfColumnTable(String table) throws SQLException {
		String query = String.format("SELECT %s FROM %s", "*", table);
		try {
			conn = DriverManager.getConnection(url, user, passwd);
			Statement state = conn.createStatement();
			
			ResultSet res = null;
			ArrayList<HashMap<String, Object>> arr;

			boolean isResult = state.execute(query);
			if (isResult)
				System.out.printf("%s\n", isResult);
				res = state.getResultSet();

			ResultSetMetaData meta = (ResultSetMetaData) res.getMetaData();
			int columnCount = meta.getColumnCount();
			
			ArrayList<String> colls = new ArrayList<String>();
			
			for (int i = 1;i<=columnCount;i++) {
				colls.add(meta.getColumnName(i));
			}
			System.out.printf("list of collumns in %s", table + colls);
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			conn.close();
		}
	}
	
	public void printCollumnNamesAndValues (String table) throws SQLException {
		String query = String.format("SELECT %s FROM %s", "*", table);
		
		try {
			conn = DriverManager.getConnection(url, user, passwd);
			
			Statement state = conn.createStatement();
			ResultSet res = state.executeQuery(query);
			
			ResultSetMetaData meta = (ResultSetMetaData) res.getMetaData();
			int columnCount = meta.getColumnCount();
			
			for (int i=1;i<columnCount;i++) {
				System.out.println("Collumn:\t" + meta.getColumnName(i));

				ResultSet resValue = state.executeQuery(query);
				while (resValue.next()) {
					System.out.println("\tValue:\t" + resValue.getString(meta.getColumnName(i)));
				}
		}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			conn.close();
		}
	}
	
	
	public void dataBaseExecute (String query) throws SQLException {
		try {
			// 1. get connection:
			conn = DriverManager.getConnection(url, user, passwd);
			
			// 2. create statement:
			Statement state = conn.createStatement();
			
			// 3. execute SQL query:
			ResultSet res = state.executeQuery(query);
			
			// 4. process the result set:
			while (res.next()) {
				System.out.println("Found value:\t" + res.getString(1));
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			conn.close();
		}
	}
	
	
	public void dataBaseUpdate (String query) throws SQLException {
		
		try {
			// 1. get connection:
			conn = DriverManager.getConnection(url, user, passwd);
			
			// 2. create statement:
			Statement state = conn.createStatement();
			
			// 3. execute SQL query:
			int res = state.executeUpdate(query);
			
			// 4. process the result set:
			System.out.println("Updated rows:\t" + res);
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			conn.close();
		}
	}
}