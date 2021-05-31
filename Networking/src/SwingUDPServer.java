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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

class serverThread implements Runnable{
	Thread t;
	SwingUDPServer server;
	public volatile boolean makeThreadRun; 
	public byte receivedBuffer[] = new byte[3000];
	
	serverThread(String name, SwingUDPServer obj){
		t = new Thread(this, name);
		makeThreadRun = false;
		t.start();
		server = obj;
	}
	
	public void run() {
		try {
			while(makeThreadRun) {
				DatagramPacket data = new DatagramPacket(receivedBuffer, receivedBuffer.length);
				server.serverSocket.receive(data);
				
				String clientMsg = new String(data.getData(), 0, data.getLength());
				
				//	Make thread terminate in 2 cases
				//	1: if current frame is closed
				//	2: if Other frame is closed
				
				if(clientMsg.equals("TERMINATE")) {
					makeThreadRun = false;
				}
				
				if(clientMsg.length() != 0) {
					System.out.println("Client: " + clientMsg);
					server.textArea.setText( 
								server.textArea.getText() + "\n" + "Client:" + clientMsg
							);
				}
				
			}
		}catch(Exception e) {
			System.out.println("LOG: Error in thread run: " + e);
			e.printStackTrace();
		}
	}
}

public class SwingUDPServer extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField serverTF;
	public JTextArea textArea;
	public DatagramSocket serverSocket;
	public serverThread thread;
	public byte sendBuffer[] = new byte[3000];
	public byte terminateMsg[] = "TERMINATE".getBytes();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SwingUDPServer frame = new SwingUDPServer();
			frame.setTitle("Server");
			frame.setVisible(true);
		}catch(Exception e) {
			System.out.println("LOG: Error in main: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public SwingUDPServer() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(12, 13, 408, 207);
		contentPane.add(textArea);
		
		serverTF = new JTextField();
		serverTF.setBounds(12, 222, 311, 24);
		contentPane.add(serverTF);
		serverTF.setColumns(10);
		
		JButton send = new JButton("Send");
		send.setBounds(323, 221, 97, 25);
		contentPane.add(send);
		send.addActionListener(this);
		
		System.out.println("LOG: Socket Initialized on port 666");
		//	Initializing client Socket at 666
		serverSocket = new DatagramSocket(666);
		
		//	initialing thread
		System.out.println("LOG: Thread Initialized");
		thread = new serverThread("Client", this);
		thread.makeThreadRun = true;
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					if(thread.t.isAlive()) {
						System.out.println("LOG: Closing Server Thread");
						thread.makeThreadRun = false;
						serverSocket.send(new DatagramPacket(terminateMsg, 0, InetAddress.getLocalHost(), 666));
						System.out.println("LOG: Terminating Connection...");
						thread.t.join();
						System.out.println("LOG: Thread ended...");
					}
					
					serverSocket.close();
					System.exit(0);
				}catch(Exception e) {
					System.out.println("LOG: Error while closing Window: " + e);
					e.printStackTrace();
				}
			}
		});
	}
	
	public void actionPerformed(ActionEvent ae) {
		String serverMsg = serverTF.getText();
		if(serverMsg.length() > 0) {
			sendBuffer = serverMsg.getBytes();
			try {
				serverSocket.send(new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getLocalHost(), 555));
				textArea.setText(  
							textArea.getText() + "\n" + "Server: " + serverMsg
						);
				serverTF.setText("");
			}catch(Exception e) {
				System.out.println("LOG: Error in Sending message: " + e);
				e.printStackTrace();
			}
		}
	}
}
