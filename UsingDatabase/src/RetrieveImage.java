import java.sql.*;  
import java.io.*;  
public class RetrieveImage implements DBConstants{  
	public static void main(String[] args) {  
		try{  
				Class.forName(SQLDriver);  
				Connection con=DriverManager.getConnection(SQLDB+"learning", username, password);  
				      
				PreparedStatement ps=con.prepareStatement("select * from img");  
				ResultSet rs=ps.executeQuery();  
				if(rs.next()){//now on 1st row  
					
					//	Read the blob data in blob object
					Blob b=rs.getBlob(2);//2 means 2nd column data
					
					//	Convert the blob object to byte array
					byte barr[] = b.getBytes(1, (int)b.length());//1 means first image
					              
					FileOutputStream fout=new FileOutputStream("res//retrived_java.jpg");  
					fout.write(barr);  
					              
		 			fout.close();  
				}//end of if  
			System.out.println("ok");  
			              
			con.close();  
		}catch (Exception e) {e.printStackTrace();  }  
	}  
} 