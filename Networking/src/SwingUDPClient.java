import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;


class clientThread implements Runnable{
	public Thread t;
	public SwingUDPClient clientFrame;
	public volatile boolean makeThreadRun;
	public byte receivedBuffer[] = new byte[3000];
	
	clientThread(String name, SwingUDPClient obj){
		t = new Thread(this, name);
		makeThreadRun = false;
		t.start();
		clientFrame = obj;
	}
	
	public void run() {
		try {
			while(makeThreadRun) {
				
				DatagramPacket dataPacket = new DatagramPacket(receivedBuffer, receivedBuffer.length);
				clientFrame.clientSocket.receive(dataPacket);
				
				String serverMsg = new String(dataPacket.getData(), 0, dataPacket.getLength());

				//	Make thread terminate in 2 cases
				//	1: if current frame is closed
				//	2: if Other frame is closed
				
				if(serverMsg.equals("TERMINATE")) {
					makeThreadRun = false;
				}
				
				if(serverMsg.length() != 0) {
					System.out.println("Server: " + serverMsg);
					clientFrame.textArea.setText(clientFrame.textArea.getText() + "\n" + "Server: " + serverMsg);
				}
			}
		}catch(Exception e) {
			System.out.println("Error in thread Executing: " + e);
			e.printStackTrace();
		}
	}
}

public class SwingUDPClient extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField clientTF;
	public DatagramSocket clientSocket;
	public clientThread thread;
	public byte sendBuffer[];
	public byte terminateMsg[] = "TERMINATE".getBytes();
	public JTextArea textArea;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		try {
			SwingUDPClient frame = new SwingUDPClient();
			frame.setTitle("Client");
			frame.setVisible(true);
		}catch(Exception e) {
			System.out.println("Error in main: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public SwingUDPClient() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		clientTF = new JTextField();
		clientTF.setBounds(12, 218, 320, 22);
		contentPane.add(clientTF);
		clientTF.setColumns(10);
		
		JButton send = new JButton("Send");
		send.setBounds(335, 217, 85, 25);
		send.addActionListener(this);
		contentPane.add(send);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(12, 13, 408, 192);
		contentPane.add(textArea);
		
		System.out.println("LOG: Socket Initialized on port 555");
		//	Initializing client Socket at 555
		clientSocket = new DatagramSocket(555);
		
		//	initialing thread
		System.out.println("LOG: Thread Initialized");
		thread = new clientThread("client", this);
		thread.makeThreadRun = true;
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					System.out.println("LOG: Waiting for thread to end....");
					thread.makeThreadRun = false;
					if(thread.t.isAlive()) {
						clientSocket.send(new DatagramPacket(terminateMsg, 0, InetAddress.getLocalHost(), 555));
						System.out.println("LOG: Terminate Connection...");
						thread.t.join();
					}
					System.out.println("Thread closed");
					clientSocket.close();
					System.exit(0);
				}catch(Exception e) {
					System.out.println("Error in windowsListener: " + e);
					e.printStackTrace();
				}
			}
		});
	}
	public void actionPerformed(ActionEvent ae) {
		String clientMsg = clientTF.getText();
		System.out.println("Client: " + clientMsg);
		sendBuffer = clientMsg.getBytes();
		try {
			System.out.println("Client: " + clientMsg.toString());
			System.out.println("LOG: Sending message at port 666");
			clientSocket.send(new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getLocalHost(), 666));
			textArea.setText(  
					textArea.getText() + "\n" + "Client: " + clientMsg
				);
			clientTF.setText("");
		}catch(Exception e) {
			System.out.println("Error in Sending Message: " + e);
			e.printStackTrace();
		}
	}
}
