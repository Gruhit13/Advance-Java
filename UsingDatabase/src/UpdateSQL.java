import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class UpdateSQL  implements DBConstants{
	
	public static void main(String arg[]) {
		Scanner inp = new Scanner(System.in);
		
		try {
			Class.forName(SQLDriver);
			System.out.println("Everything working fine till now");
			Connection con = DriverManager.getConnection(SQLDB+"learning", username, password);
			System.out.println("Done !!!");
			/*
			 * int id, age, status; String name, choice;
			 * 
			 * System.out.print("Enter id to be updated:- "); id = inp.nextInt();
			 * 
			 * inp.nextLine(); System.out.print("Want to update Age or Name? (A/N)"); choice
			 * = inp.nextLine();
			 * 
			 * if(choice.toLowerCase().equals("a")) { PreparedStatement ps =
			 * con.prepareStatement("Update empinfo set age = ? where id = ?");
			 * System.out.print("Enter the new age:- "); age = inp.nextInt();
			 * 
			 * ps.setInt(1, age); ps.setInt(2, id); status = ps.executeUpdate(); ps.close();
			 * } else { PreparedStatement ps =
			 * con.prepareStatement("update empinfo set name=? where id=?");
			 * System.out.print("Enter the new name:- "); name = inp.nextLine();
			 * 
			 * ps.setString(1, name); ps.setInt(2, id); status = ps.executeUpdate();
			 * ps.close(); }
			 * 
			 * if(status != 0) System.out.print("Record Updated Successfully"); else
			 * System.out.println("Failed to Update Record");
			 */
			con.close();
			
		}catch(Exception e) {  e.printStackTrace(); }
		
	}
}
