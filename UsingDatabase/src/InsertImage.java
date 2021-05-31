import java.sql.*;  
import java.io.*;  
public class InsertImage implements DBConstants{  
	public static void main(String[] args) {  
		try{  
			Class.forName(SQLDriver);  
			Connection con=DriverManager.getConnection(SQLDB+"learning", username, password);  
			              
			PreparedStatement ps=con.prepareStatement("insert into img values(?,?)");  
			ps.setString(1,"amit");  
			
			//	reading image file 
			FileInputStream fin =new FileInputStream("res//java.jpg");  
			ps.setBinaryStream(2, fin, fin.available());
			int i=ps.executeUpdate();  
			System.out.println(i+" records affected");  
			          
			con.close();  
		}catch (Exception e) {e.printStackTrace();}  
	}  
}  