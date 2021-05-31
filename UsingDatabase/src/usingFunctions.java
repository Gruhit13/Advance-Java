import java.sql.*;
import java.util.Scanner;

public class usingFunctions implements DBConstants{

	public static void main(String[] args) {
		try {
			Scanner inp = new Scanner(System.in);
			
			Class.forName(SQLDriver);
			Connection con = DriverManager.getConnection(SQLDB+"learning", username, password);
			CallableStatement call = con.prepareCall("{?= call getMax(?, ?)}");
			
			System.out.println("All well till here");
			int a, b; 
			System.out.print("Enter value for A:- "); a = inp.nextInt();
			System.out.print("Enter value for B:- "); b = inp.nextInt();
			
			call.setInt(2, a);
			call.setInt(3, b);
			
			call.registerOutParameter(1, Types.INTEGER);
			
			call.execute();
			
			System.out.println("Max of given is: " + call.getInt(1));
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
