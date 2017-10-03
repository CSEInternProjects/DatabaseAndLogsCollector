package ConnDbClass;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnToMysql {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");

		// change user and password as you need it
		Connection con = DriverManager.getConnection ("jdbc:mysql://localhost:3306", "root", "root");

		ResultSet rs = con.getMetaData().getCatalogs();

		while (rs.next()) {
		    System.out.println("TABLE_CAT = " + rs.getString("TABLE_CAT") );
		}
		System.out.println("\n\n");
		//con.close();
		
		//con= DriverManager.getConnection("jdbc:mysql://localhost:3306//konyadmindb1","root", "root");
	   Statement st= con.createStatement();
	   st.executeQuery("USE konyadmindb1");
	   rs=st.executeQuery("show tables");
	   
	   int columnIndex=1;
	   while(rs.next())
		{
			
			
			System.out.println(rs.getString(columnIndex));
			//columnIndex++;
		}
	}

}
