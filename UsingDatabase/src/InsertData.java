import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertData implements DBConstants{
	
	public static void main(String[] args) {
		Scanner inp = new Scanner(System.in);
		
		try {
			Class.forName(MSAccessDriver);
			try {
				Connection con = DriverManager.getConnection(MsAccessDB + "Database1.accdb");
				int id, age, status;
				String name, choice;
				
				System.out.print("Enter id to be updated:- "); id = inp.nextInt();
				
				inp.nextLine();
				System.out.print("want to update Age or Name? (A/N)"); choice = inp.nextLine();
				
				if(choice.toLowerCase().equals("a")) {
					PreparedStatement ps = con.prepareStatement("Update Table1 set age = ? where id = ?");
					System.out.print("Enter the new age:- "); age = inp.nextInt();
					
					ps.setInt(1, age);
					ps.setInt(2, id);
					status = ps.executeUpdate();
					ps.close();
				}
				else {
					PreparedStatement ps = con.prepareStatement("update Table1 set name=? where id=?");
					System.out.print("Enter the new name:- "); name = inp.nextLine();
					
					ps.setString(1,  name);
					ps.setInt(2, id);
					status = ps.executeUpdate();
					ps.close();
				}
				
				if(status != 0) System.out.print("Record Updated Successfully");
				else System.out.println("Failed to Update Record");
				con.close();
				
			}catch(SQLException e) {
				System.out.println("Error: " + e);
				e.printStackTrace();
			}
			
		}catch(ClassNotFoundException e) {
			System.out.println(e);
		}

	}
}