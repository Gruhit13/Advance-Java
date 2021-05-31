import java.io.*;  
import java.sql.*;  
  
public class RetrieveFile implements DBConstants{  
	public static void main(String[] args) {  
		try{  
			Class.forName(SQLDriver);  
			Connection con=DriverManager.getConnection(SQLDB+"learning", username, password);  
			              
			PreparedStatement ps=con.prepareStatement("select * from ftable");  
			ResultSet rs=ps.executeQuery();  
			rs.next();//now on 1st row  
			 
			//	Retrive Clob object
			Clob c=rs.getClob(2);
			
			//	Get character stream out of it
			Reader r=c.getCharacterStream();              
			              
			//	Creating an output file
			FileWriter fw=new FileWriter("res//retrived_DataFile.txt");
			              
			//	Writing each character from reader object to output file
			int i;  
			while((i=r.read())!=-1)  
			fw.write(i);
			              
			fw.close();  
			con.close();  
			              
			System.out.println("success");  
		}catch (Exception e) {e.printStackTrace();  }  
	}  
}  