import java.io.*;
import java.net.*;

public class Client1 {

	public static void main(String[] args) throws Exception{
		Socket s = new Socket("localhost", 777);
		
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
		
		String str_server, str_user;
		System.out.println("Enter message...");
		
		while(!(str_user = kb.readLine()).equals("exits")) {
			dos.writeBytes(str_user+"\n");
			
			str_server = br.readLine();
			System.out.println("Server: " + str_server);
		}
		
		kb.close();
		br.close();
		dos.close();
		s.close();
	}

}
