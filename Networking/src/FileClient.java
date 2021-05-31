import java.net.*;
import java.io.*;

public class FileClient {

	public static void main(String[] args) throws Exception {
		//	Creating client socket
		Socket s = new Socket("localhost", 778);
		
		//	Creating streams
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintStream ps = new PrintStream(s.getOutputStream());
		
		//	Take the file to be read
		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Enter the file name: "); 
		String fname = kb.readLine();
		
		//	Sending the file name to Server
		ps.println(fname);
		
		//	Server will sent acknowledgement of file existence
		if(in.readLine().equals("yes")) {
			
			String fileContent;
			System.out.println("Printing file content");
			while( (fileContent = in.readLine()) != null ) {
				System.out.println(fileContent);
			}
		}else {
			System.out.println("File not Found");
		}
		
		ps.close();
		kb.close();
		in.close();
		s.close();
	}

}
