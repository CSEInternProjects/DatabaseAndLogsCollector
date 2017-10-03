package ConnDbClass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ZipTeatPack.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.mysql.jdbc.ResultSetMetaData;

public class ConnDbClassCode {
	static int flagForReExe = 0;
	static Connection con = null;
	static String pathWhereAllTablesAreGonnaStore=null;

	public static void main(String args[]) {
		try {
			// if(flagForReExe==0)
			// {
			Scanner sc = new Scanner(System.in);
			System.out.println("If you gonna deal with data bases and zipping logs enter\"database\" or with \n"
					+ "only zipping logs enter\"logs\"");
			ConnDbClassCode cdc = new ConnDbClassCode();
			if (sc.next().trim().equals("logs")) {
				cdc.callZipClass(sc);
				System.exit(0);
			}
			System.out.println("please type kind of database you are using and press enter\n"+
			"Example: \"mysql\" or \"oracle\"");
			String DBType = sc.next();
			System.out.println(DBType);
			DBType = DBType.trim();

			if (DBType.equals("mysql")) {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Type mysql port.\n"+
				" Example: jdbc:mysql://localhost:3306");
			} else if (DBType.equals("oracle")) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println("Enter oracle DB port \n"+
				"Example: jdbc:oracle:thin:@localhost:1521:xe");
			} else {
				System.out.println("invalide DB name ");
				System.exit(0);
			}
			// if(DBType=="mysql"||DBType="oracle"){
			String[] mysql_Details = new String[5];

			mysql_Details[0] = sc.next();
			System.out.println("Type sql user name");
			mysql_Details[1] = sc.next();
			System.out.println("Type sql password");
			mysql_Details[2] = sc.next();
			con = DriverManager.getConnection(mysql_Details[0], mysql_Details[1], mysql_Details[2]);// "jdbc:mysql://localhost:3306/konyadmindb1"
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();
			List<String> arrayList = new ArrayList<String>();
			ConnDbClassCode cd = new ConnDbClassCode();
			if(DBType.equals("mysql"))
			arrayList.addAll(cd.mySqlCodeForReUsingDb(sc,stmt,con));	
			if(DBType.equals("oracle"))
			{
            	DatabaseMetaData md = con.getMetaData();
				ResultSet rs1 = md.getTables(null, null, "%", null);
				while (rs1.next()) {
					System.out.println(rs1.getString(3));
					arrayList.add(rs1.getString(3));
				}
				
				// System.out.println("Enter table name of Choosen DB");
				// mysql_Details[3]=sc.nextLine();
	
				
				cd.tablesPrint(stmt, arrayList);
			}
			System.out.println("Wish for update?\n"+
			"type \"y\" for yes or \"n\" for no");
			if (sc.next().equals("y")) {
				cd.upDate(stmt, arrayList);
			} else {
				System.out.println("Thank you!!");
			}
			con.close();

			// sc.close();
			// }
			System.out.println("now you are entering to zipping log files ");
			cdc.callZipClass(sc);
		} catch (NoSuchElementException e) {
			System.out.println(e);
			System.out.println("type correct path to save");
			// cd.tablesPrint(stmt, arrayList);
			main(null);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("some thing gone wrong once try again");
			// ConnDbClassCode cdcc= new ConnDbClassCode();
			main(null);
		}
	}
	static Map<Integer, String> map= new HashMap<Integer,String>();
	public List<String> mySqlCodeForReUsingDb(Scanner sc, Statement stmt,Connection con)  {
		// TODO Auto-generated method stub
		try {
		 
			ResultSet rs = con.getMetaData().getCatalogs();
		System.out.println("List of all databases names");
		int CountForDb=1;
		while (rs.next()) {
		    map.put(CountForDb,rs.getString("TABLE_CAT") );
			System.out.println( CountForDb+". "+map.get(CountForDb)  );
			CountForDb++;
		}
		System.out.println("Type corresponding number to the required database and press enter");
	    String providedDbName=map.get(Integer.parseInt(sc.next()));
		rs=stmt.executeQuery("use "+providedDbName);
		System.out.println("the database you have selected is "+providedDbName);
		System.out.println("Here the list of tables in this database "+providedDbName);
		rs=stmt.executeQuery("show tables");
		Map<Integer, String> map2= new HashMap<Integer,String>();
		List<String> arrayList= new ArrayList<String>();
		int countForTables=1;
		while(rs.next())
		{
			map2.put(countForTables,rs.getString(1) );
			System.out.println(map2.get(countForTables)  );
			arrayList.add(map2.get(countForTables));
			countForTables++;
		}
		tablesPrint(stmt, arrayList);
		System.out.println("Do you want to capture other database type \"1\" or move on to next step type \"2\"");
		String st=sc.next();
		if(st.equalsIgnoreCase("1"))
			mySqlCodeForReUsingDb(sc, stmt, con);
		if(st.equalsIgnoreCase("2"))
			return arrayList;
		}
		catch (Exception e) {
			// TODO: handle exception
		  System.err.println(e);
		  System.out.println("you went some where wrong please try again");
		  mySqlCodeForReUsingDb(sc, stmt, con);
		}
		return null;
	}
	public void callZipClass(Scanner sc) {
		// TODO Auto-generated method stub
		try {
			ZipTestClass.mains(null, sc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void upDate(Statement stmt, List<String> arrayList) {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Need guidance for updation: \n "+
			"type \"y\" for yes or \"n\" for no");
			if (sc.next().equals("y")) {
				System.out.println("here the list of table names:\n choose one");
				for (String st : arrayList) {
					System.out.println(st);
				}

				String temp = sc.next();
				int flag;
				for (flag = 0; flag < arrayList.size(); flag++) {
					if (temp.equals(arrayList.get(flag))) {
						ResultSet rs = stmt.executeQuery("select * from " + temp);// "process_info";
						java.sql.ResultSetMetaData metaData = rs.getMetaData();
						int count = metaData.getColumnCount(); // number of column
						String columnName[] = new String[count];
						System.out.println("Columns in that table");
						for (int i1 = 1; i1 <= count; i1++) {
							columnName[i1 - 1] = metaData.getColumnLabel(i1);
							System.out.println(columnName[i1 - 1]);
						}
						while (rs.next()) {
							int i = 1;
							System.out.println();
							while (i <= count)// rs.getString(i) != null
							{
								System.out.println(columnName[i - 1] + "=");
								// bw.write(columnName[i-1]+" = ");
								int type = metaData.getColumnType(i);
								// if (type == Types.VARCHAR||type == Types.BIGINT||type == Types.TIMESTAMP) {
								if (type != Types.LONGVARBINARY) {
									if (rs.getString(i) != null) {
										System.out.print(rs.getString(i) + type + "    ");
										// bw.write(rs.getString(i)+type+" ");
									} else {
										System.out.print("null" + "    ");
										// bw.write("null"+" ");
									}
								} else {
									// bw.write("BLOB"+" ");
									System.out.println("BLOB" + "    ");
								}
								i++;
							}
							// bw.write("\n");
							System.out.println("\n");
						}

					}
				}
			} else {
				System.out.println("thank you!!");
			}
			String pathForUpdation = null;
			while (true) {
				Scanner sc2 = new Scanner(System.in);

				// FileReader fr= new FileReader("C:\\Users\\KH9331\\Desktop\\update.txt");
				System.out
						.println("Now give the path of file where update content is written EX :C:\\Users\\update.txt");
				pathForUpdation = sc2.next().trim();

				System.out.println("Example how you need to fill the above file for update: \r\n" + "tableName\r\n"
						+ "colName=\"kabulChange\"\r\n" + "District=\"kabolChange\"\r\n" + "where \r\n" + "ID=\"1\"\r\n"
						+ "Name=\"kabul\"\r\n" + "");
				System.out.println("have u filled update content in .txt file:\nclick \"y\" for yes or \"n\" for no"
						+ " type \"moveToLogs\" to jump to zipping log files "
						+ "\nelse type \"exit\" to exit the program ");
				String st = sc.next().trim();
				if (st.equalsIgnoreCase("moveToLogs")) {
					ConnDbClassCode cdc = new ConnDbClassCode();
					cdc.callZipClass(sc);
				}
				if (st.equals("y"))
					break;
				else if (st.equals("exit"))
					System.exit(0);

			}
			FileReader fr = new FileReader(pathForUpdation);
			Scanner sc1 = new Scanner(fr);
			HashMap<String, String> hm = new HashMap<String, String>();
			// Pattern p= new PatternSyntaxException(desc, regex, index)
			int p = 0;
			String whereClause = "", setClause = "";
			while (sc1.hasNextLine()) {
				String temp = sc1.next().trim();
				if (temp.equals(""))
					continue;

				if (temp.equalsIgnoreCase("where") || p != 0) {
					p++;
					if (temp.equalsIgnoreCase("where"))
						continue;

					whereClause = whereClause + " and " + temp;
					continue;
				} else if (p == 0) {
					if (hm.isEmpty())
						hm.put("table", temp);
					else if (hm.get("table") != null) {
						setClause = setClause + "," + temp;
					}
				}
			}
			whereClause = whereClause.substring(5, whereClause.length()).trim();
			setClause = setClause.substring(1, setClause.length()).trim();

			if (p > 2) {
				// setClause
				whereClause = "(" + whereClause + ")";
			}
			System.out.println(" set " + setClause + " where " + whereClause);
			hm.put("set", setClause);
			hm.put("where", whereClause);
			int i = stmt.executeUpdate(
					"update " + hm.get("table") + " set " + hm.get("set") + " where " + hm.get("where") + ";");
			System.out.println(i + " no of rows effected");
			sc1.close();
			System.out.println("Do you want another update: type \"y\" for yes");
			Scanner sc3 = new Scanner(System.in);
			if (sc3.next().trim().equals("y"))
				upDate(stmt, arrayList);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
			System.out.println("once try again with the updation process");
			upDate(stmt, arrayList);
		}
	}

	void tablesPrint(Statement stmt, List<String> arrayList) throws Exception {
		try {
			ResultSet rs;
			Scanner sc1 = new Scanner(System.in);
			// FileWriter fw= new FileWriter("C:\\Users\\KH9331\\Desktop\\creater.txt");
			System.out.println(
					"Please type path, there these tables will be stored \n"+
			"ex C:\\Users\\KH9331\\Desktop\\AllTablesInProvidedDB.txt");
			String tempPathForAllTales=sc1.next().trim();
			FileWriter fw = new FileWriter(tempPathForAllTales);
			// sc1.close();
			BufferedWriter bw = new BufferedWriter(fw);
			// ResultSet rs;
			java.sql.ResultSetMetaData metaData;
			for (int i2 = 0; i2 < arrayList.size(); i2++) {
				System.out.println(arrayList.get(i2));

				rs = stmt.executeQuery("select * from " + arrayList.get(i2));// "process_info"
				metaData = rs.getMetaData();
				int count = metaData.getColumnCount(); // number of column
				String columnName[] = new String[count];

				for (int i1 = 1; i1 <= count; i1++) {
					columnName[i1 - 1] = metaData.getColumnLabel(i1);
					// System.out.println(columnName[i1-1]);
				}
				bw.write("      The table that have choosen is " + arrayList.get(i2));
				bw.write("\n");
				while (rs.next()) {// System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
					int i = 1;
					System.out.println();
					while (i <= count)// rs.getString(i) != null
					{
						// System.out.println(columnName[i-1]+"=");
						bw.write(columnName[i - 1] + "  =  ");
						int type = metaData.getColumnType(i);
						// if (type == Types.VARCHAR||type == Types.BIGINT||type == Types.TIMESTAMP) {
						if (type != Types.LONGVARBINARY) {
							if (rs.getString(i) != null) {
								// System.out.print(rs.getString(i)+type+" ");
								bw.write(rs.getString(i) + type + "    ");
							} else {
								// System.out.print("null"+" ");
								bw.write("null" + "    ");
							}
						} else {
							bw.write("BLOB" + "    ");
						}
						i++;
					}
					bw.write("\n");

				}
				bw.write("\n");
			}
			bw.close();
        ConnDbClassCode.pathWhereAllTablesAreGonnaStore=tempPathForAllTales;
		ZipTestClass.SettingStaticVar(pathWhereAllTablesAreGonnaStore);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			System.out.println("you might not gave a valid path pls check and give path again ");
			this.tablesPrint(stmt, arrayList);
		}
	}

}
