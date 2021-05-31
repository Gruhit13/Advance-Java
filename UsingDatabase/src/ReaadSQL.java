import java.sql.DriverManager;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class ReaadSQL implements DBConstants {

	public static void main(String[] args) {
		
		try {
			Class.forName(SQLDriver);
			Connection con = DriverManager.getConnection(SQLDB+"college", username, password);
			
			Statement stmt = con.createStatement();
			String query = "Select * from student";
			
			ResultSet result = stmt.executeQuery(query);
			
			
			System.out.println("ID \t Name");
			for(int i = 0; i < 15; i++) System.out.print("-");
			
			System.out.println();
			
			while(result.next()) {
				System.out.println(""+result.getString(1) + "\t" + result.getString(2));
			}
			
			stmt.close();
			con.close();
		}catch(Exception e) {
			System.out.print("ERROR: " + e);
			e.printStackTrace();
		}

	}

}
