import java.sql.*;
import java.util.Scanner;
import java.util.Scanner;

public class usingCallable implements DBConstants {

	public static void main(String[] args) {
		Scanner inp = new Scanner(System.in);
		
		try {
			Class.forName(SQLDriver);
			
			Connection con = DriverManager.getConnection(SQLDB+"learning", username, password);
			
			//	Creating a callable object
			CallableStatement cs = con.prepareCall("{ call addData(?, ?, ?) }");
			
			int id, age;
			String name, choice;
			while(true) {
				
				System.out.print("Enter ID:- " ); id = inp.nextInt();
				inp.nextLine();
				
				System.out.print("Enter name:- "); name = inp.nextLine();
				System.out.print("Enter age:- "); age = inp.nextInt();
				
				cs.setInt(1, id);
				cs.setString(2, name);
				cs.setInt(3,  age);
				
				cs.addBatch();
				
				inp.nextLine();
				System.out.println("Want to enter another data ? (y/n)");
				choice = inp.nextLine();
				
				if(choice.equals("n") || choice.equals("N") ) break;
			}
			int i = cs.executeBatch().length;
			
			System.out.println("Total data effected = " + i);
			
			System.out.println("Data stored");
			
			con.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
