import java.io.*;  
import java.sql.*;  
  
public class StoreFile implements DBConstants{  
	public static void main(String[] args) {  
		try{  
			Class.forName(SQLDriver);  
			Connection con=DriverManager.getConnection( SQLDB+"learning", username, password);  
			              
			PreparedStatement ps=con.prepareStatement( "insert into ftable values(?,?)");  
			              
			File f=new File("res//DataFile.txt");
			FileReader fr=new FileReader(f);  
			 
			ps.setInt(1,101);  
			ps.setCharacterStream(2, fr, (int)f.length());  
			int i=ps.executeUpdate();  
			System.out.println(i+" records affected");  
			              
			con.close();  
		              
		}catch (Exception e) {e.printStackTrace();}  
	}  
} 