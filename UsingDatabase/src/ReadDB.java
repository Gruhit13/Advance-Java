import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadDB implements DBConstants {

	public static void main(String[] args) {
		try {
			Class.forName(MSAccessDriver);
			try {
				Scanner inp = new Scanner(System.in);
				
				//	Obtained Connection
				Connection con = DriverManager.getConnection(MsAccessDB + "Database1.accdb");
				
				//	Insert data in DB
				PreparedStatement ps = con.prepareStatement("insert into Table1 values(?, ?, ?)");
				
				String name;
				int age, id;
				
				System.out.print("Enter Name:- "); name = inp.nextLine();
				System.out.print("Enter ID: " ); id = inp.nextInt();
				System.out.print("Enter age:- "); age = inp.nextInt();
				
				ps.setInt(1, id);
				ps.setString(2, name);
				ps.setInt(3, age);
				
				int r = ps.executeUpdate();
				
				if(r != 0) System.out.println("Data stored Successful");
				else System.out.println("Failed to stored Data");

				//	 Obtaining statement to execute query
				Statement stmt = con.createStatement();
				String query = "Select * from Table1";
				
				//	Executing query and storing result in ResultSet object
				ResultSet res = stmt.executeQuery(query);
				
				System.out.println("\nDisplaying Data");
				while(res.next()) {
					System.out.println("ID: " + res.getInt(1) +" Name = " + res.getString("Name") + " Age = " + res.getInt("Age"));
				}
				
				stmt.close();
				con.close();
				
			} catch(SQLException e) {
				System.out.println("Error: " + e);
			}
		}catch(ClassNotFoundException e) {
			System.out.println("Class not found: " + e );
		}
	}

}
