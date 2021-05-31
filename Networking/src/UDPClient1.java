import java.net.*;

public class UDPClient1 {
	
	public static DatagramSocket mySocket;
	public static byte myBuffer[] = new byte[2000];
	public static byte receiveBuffer[] = new byte[2000];
	
	public static void ClientMethod() throws Exception{
		while(true) {
			int position = 0;
			boolean run = true;
			//	Logic for sending a msg
			while(run) {
				int charData = System.in.read();
				
				switch(charData) {
				case -1: System.out.println("Execution of serevr has Terminated");
						 return;
				
				case '\r': break;
				
				case '\n': mySocket.send(new DatagramPacket(myBuffer, position, InetAddress.getLocalHost(), 888));
						   position = 0;
						   run = false;
						   break;
				default: myBuffer[position++] = (byte) charData;
				}
			}
			
			//	Wait for a message from client;
			DatagramPacket dataPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			mySocket.receive(dataPacket);
			
			System.out.println("Server: " + new String(dataPacket.getData(), 0, dataPacket.getLength()));
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("Enter CTRL+C to exit");
		mySocket = new DatagramSocket(777);
		ClientMethod();
	}

}
