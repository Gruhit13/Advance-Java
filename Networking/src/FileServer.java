import java.net.*;
import java.io.*;

public class FileServer {

	public static void main(String[] args) throws Exception{
		
		ServerSocket ss = new ServerSocket(778);
		System.out.println("Waiting for Client to accept connection...");
		
		Socket s = ss.accept();
		System.out.println("Connection accepted");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		
		FileReader fr;
		BufferedReader file;
		boolean flag;
		
		//	Get the name of file sent by client
		String fileName = in.readLine();
		
		//	Create file object to check if file exists or not
		File f = new File(fileName);
		
		if(f.exists()) {
			flag = true;
			System.out.println("File Found");
			dos.writeBytes("yes\n");
		} else {
			flag = false;
			System.out.println("File Not Found");
			dos.writeBytes("no\n");
		}
		
		if(flag) {
			fr = new FileReader(fileName);
			file = new BufferedReader(fr);
			
			String fileContent;
			
			while((fileContent = file.readLine()) != null) dos.writeBytes(fileContent+"\n");
			
			file.close();
			fr.close();
		}
		
		dos.close();
		in.close();
		s.close();
		ss.close();
	}

}
