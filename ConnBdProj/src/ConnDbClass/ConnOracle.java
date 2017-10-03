package ConnDbClass;

import java.sql.*;
import java.util.Scanner;

import oracle.*;
import oracle.jdbc.connector.*;
import oracle.sql.*;
public class ConnOracle {

	public static void main(String args[]){  
		try{  
		
			String host,userName,passWord;
			System.out.println("Enter oracle DB port and DB. Example: jdbc:oracle:thin:@localhost:1521:xe");
			Scanner sc= new Scanner(System.in);
			host= sc.nextLine();
			System.out.println("Enter user name");
			userName=sc.nextLine();
			System.out.println("Enter password");
			passWord=sc.nextLine();
			//step1 load the driver class  
			Class.forName("oracle.jdbc.driver.OracleDriver");  
			//step2 create  the connection object  
			Connection con=DriverManager.getConnection(  
			host,userName,passWord);// common user name is system.Username konyidconfigdb1008171856  
			  // password root
			//step3 create the statement object  
			Statement stmt=con.createStatement();  
			  
			//step4 execute query  
			System.out.println("Enter table name");
			String tabName=sc.nextLine();
			ResultSet rs=stmt.executeQuery("select * from "+tabName);//acs_keystore  
			while(rs.next())  
			{
				System.out.println(rs.getString(1)+"  "+rs.getString(2));
				String s=rs.getString(1);
			}
			  
			//step5 close the connection object  
			con.close();  
		  
		}catch(Exception e){ System.out.println(e);}  
		  
		}  
}
