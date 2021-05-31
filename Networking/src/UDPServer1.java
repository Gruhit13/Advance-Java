import java.net.*;

public class UDPServer1 {
	
	public static DatagramSocket mysocket;
	public static byte myBuffer[] = new byte[2000];
	public static byte receiveBuffer[] = new byte[2000];
	
	public static void ServerMethod() throws Exception{
		
		while(true) {
			
			//	Wait for a message from client;
			DatagramPacket dataPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			mysocket.receive(dataPacket);
			
			System.out.println("Client: " + new String(dataPacket.getData(), 0, dataPacket.getLength()));
			
			int position = 0;
			boolean run = true;
			//	Logic for sending a msg
			while(run) {
				int charData = System.in.read();
				
				switch(charData) {
				case -1: System.out.println("Execution of serevr has Terminated");
						 return;
				
				case '\r': break;
				
				case '\n': mysocket.send(new DatagramPacket(myBuffer, position, InetAddress.getLocalHost(), 777));
						   position = 0;
						   run = false;
						   break;
				default: myBuffer[position++] = (byte) charData;
				}
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		System.out.println("Enter CTRL+C to exit");
		mysocket = new DatagramSocket(888);
		ServerMethod();
	}

}
