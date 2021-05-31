import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

class ServerThread implements Runnable{
	Thread t;
	MutipleServer server;

	public volatile boolean runMyThread = false;
	public byte receivedData[] = new byte[3000];
	
	ServerThread(String name, MutipleServer obj) {
		server = obj;
		t = new Thread(this, name);
	}
	
	public void run() {
		try {
			while(runMyThread) {
				DatagramPacket packet = new DatagramPacket(receivedData, receivedData.length);
				server.serverSocket.receive(packet);
				
				//	If port is 666 then it is client 1
				//	else if it is 555 then its for termination
				//	else it is client 2
				String clientMsg = new String(packet.getData(), 0, packet.getLength());
				if(packet.getPort() == 666) {
					server.textArea.setText(
								server.textArea.getText() + "\n" + "Client_1:" + clientMsg
							);
				}else if(clientMsg.equals("TERMINATE")) {
					runMyThread = false;
				}else {
					server.textArea.setText(
							server.textArea.getText() + "\n" + "Client_2:" + clientMsg
						);
				}
				
			}
		}catch(Exception e) {
			System.out.println("Error in thread execution: " + e);
			e.printStackTrace();
		}
	}
	
}

public class MutipleServer extends JFrame {

	private JPanel contentPane;
	public JTextField textField;
	public JTextArea textArea;
	public DatagramSocket serverSocket;
	public byte sentBuffer[] = new byte[3000];
	public byte TERMINATE[] = "TERMINATE".getBytes();
	
	public ServerThread thread;
	private JButton sendBtnC2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MutipleServer frame = new MutipleServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SocketException 
	 */
	public MutipleServer() throws SocketException {
		setResizable(false);
		setBounds(100, 100, 515, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textArea.setBounds(12, 5, 485, 344);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setBounds(12, 354, 407, 61);
		contentPane.add(textField);
		textField.setColumns(10);
		
		//	Create socket
		serverSocket = new DatagramSocket(555);
		
		//	Creating thread
		thread = new ServerThread("Server", this);
		thread.runMyThread = true;
		thread.t.start();
		
		
		JButton sendBtnC1 = new JButton("Client 1");
		sendBtnC1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sendData = textField.getText();
				try {
					if(sendData.length() > 0) {
						sentBuffer = sendData.getBytes();
						serverSocket.send(new DatagramPacket(sentBuffer, sendData.length(), InetAddress.getLocalHost(), 666));
						
						textArea.setText(
								textArea.getText() + "\n" + "Serve_C1: " + sendData.toString()
							);
						textField.setText("");
					}
				}catch(Exception e1) {
					System.out.println("Exception in sending msg to client 1: " + e1);
					e1.printStackTrace();
				}
				
			}
		});
		sendBtnC1.setBounds(418, 354, 79, 33);
		contentPane.add(sendBtnC1);
		
		sendBtnC2 = new JButton("Client 2");
		sendBtnC2.setBounds(418, 382, 79, 33);
		
		sendBtnC2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sendData = textField.getText();
				try {
					if(sendData.length() > 0) {
						sentBuffer = sendData.getBytes();
						serverSocket.send(new DatagramPacket(sentBuffer, sendData.length(), InetAddress.getLocalHost(), 777));
						
						textArea.setText(
									textArea.getText() + "\n" + "Serve_C2: " + sendData.toString()
								);
						textField.setText("");
					}
				}catch(Exception e2) {
					System.out.println("Exception in sending msg to client 2: " + e2);
					e2.printStackTrace();
				}
				
			}
		});
		contentPane.add(sendBtnC2);
		
		//	Set onWindowClsoing listener
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					if(thread.t.isAlive()) {
						serverSocket.send(new DatagramPacket(TERMINATE, TERMINATE.length, InetAddress.getLocalHost(), 555));
						thread.t.join();
						System.out.println("LOG: Thread execution stopped successfully");
					}
					serverSocket.close();
					System.exit(0);
					
				}catch(Exception ex) {
					System.out.println("Error in closing frame: " + ex);
					ex.printStackTrace();
				}
			}
		});
	}
}
