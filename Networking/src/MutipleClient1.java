import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;

class Client1Thread implements Runnable{
	Thread t;
	volatile boolean runMyThread;
	MutipleClient1 client1;
	byte receiveBuffer[] = new byte[3000];
	
	Client1Thread(String name, MutipleClient1 obj){
		client1 = obj;
		t = new Thread(this, name);
	}
	
	public void run() {
		try {
			DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			
		}catch(Exception e) {
			System.out.println("Error in Thread Execution: " + e);
			e.printStackTrace();
		}
	}
}

public class MutipleClient1 extends JFrame {

	private JPanel contentPane;
	public JTextArea textArea;
	public JTextField textField;
	private JButton sendBtn;
	public DatagramSocket client1Socket;
	
	public Client1Thread thread;
	
	public byte sentBuffer[] = new byte[3000];
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MutipleClient1 frame = new MutipleClient1();
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
	public MutipleClient1() throws SocketException {
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
		
		
		sendBtn = new JButton("Send");
		sendBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		sendBtn.setForeground(Color.DARK_GRAY);
		sendBtn.setBackground(Color.GREEN);
		sendBtn.setBounds(417, 354, 80, 61);
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	TODO::	
			}
		});
		contentPane.add(sendBtn);
		
		//	Initialize socket
		client1Socket = new DatagramSocket(666);
		
		//	Create thread
		thread = new Client1Thread("Client1", this);
		thread.runMyThread = true;
		thread.t.start();
		
	}

}
