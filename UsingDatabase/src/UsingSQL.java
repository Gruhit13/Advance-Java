import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

import javax.print.DocFlavor.STRING;

public class UsingSQL implements DBConstants {
	
		public static void main(String[] args) {
			Scanner inp = new Scanner(System.in);
			String choice = "y";
			try {
				while(true) {
					Class.forName(SQLDriver);
					Connection con = DriverManager.getConnection(SQLDB+"college", username, password);
					
					Statement stmt = con.createStatement();
					
					String id, name;
					System.out.println("Enter Data:");
					System.out.print("Enter ID: "); id = inp.nextLine();
					
					System.out.print("Enter Name: "); name = inp.nextLine();
					
					String query = "insert into student values('" + id +"', '" + name + "')";
					int cnt = stmt.executeUpdate(query);
					
					if(cnt > 0) {
						System.out.println("Number of rows affected: " + cnt);
					}else {
						System.out.println("LOG: Error in inserting data..");
					}
					
					System.out.print("Want to add another data? (y/n):- ");
					choice = inp.nextLine();
					System.out.println("\n");
					if(choice.equals("n")) {
						stmt.close();
						con.close();
						break;
					}
				}
			}catch(Exception e) {
				System.out.println("Error: " + e);
				e.printStackTrace();
			}
		}

}
