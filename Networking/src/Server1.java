import java.io.*;
import java.net.*;

public class Server1 {
	public static void main(String arg[]) throws Exception {
		ServerSocket ss = new ServerSocket(777);
		
		//	Wait for client to accept connection req.
		Socket s = ss.accept();
		
		System.out.println("Connection Establised!!!");
		//	get Input Stream to get data sent from client
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		//	print Data to client
		PrintStream ps = new PrintStream(s.getOutputStream());
		
		//	BufferedeReader for getting data from user
		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			
			String str_user, str_client;
			
			//	Take data from client 
			while((str_client = br.readLine()) != null) {
				System.out.println("Client: "+str_client); //	Print the received data
				System.out.print("Server Enter message: ");
				str_user = kb.readLine();			//	Take user's message
				ps.println(str_user);				//	Print user's message towards client
			}
			
			kb.close();
			ps.close();
			br.close();
			s.close();
			ss.close();
		}
	}
}
