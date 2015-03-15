package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import com.mysqitl.jdbc.ResultSetMetaData;

/**
 * Simple JDBC example
 */

public class DataBaseExecute {

	public static void main(String[] args) throws SQLException {
		DBDriver dataDriver = new DBDriver();

		//update data base:
		String insertIn = "insert into Persons (PersonID, LastName, FirstName, City) values (3, 'Tesla', 'Nicola', 'California');";
		String createTab = "CREATE TABLE Emails(id int, UserName varchar(250), UserEmail varchar(250))";
		String inEmails = "INSERT INTO Emails (id, UserName, UserEmail) values (1, 'Foo', 'foo@email.com')";
		String left_join  = "select persons.LastName, persons.Address, emails.UserName from persons left join emails on persons.PersonID=emails.id";

		//read from data base:
		String show = "UPDATE Emails SET id=12 WHERE UserEmail='ben@email.com'";

		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/JAVA_DATA";
		String user = "user";
		String passwd = "user";
		
		String query = "select persons.LastName, persons.Address, emails.UserName from persons left join emails on persons.PersonID=emails.id";

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
				
				for (int i=1;i<=columnCount;i++) {
					System.out.println(meta.getColumnName(i));

					ResultSet resValue = state.executeQuery(query);
					while (resValue.next()) {
						System.out.println("\t" + resValue.getString(meta.getColumnName(i)));
					}
				}
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
			finally {
				conn.close();
			}
			//dataDriver.getCollectionTablevalue("persons");
	}
}