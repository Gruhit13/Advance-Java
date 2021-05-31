import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class SwingServer1 extends JFrame implements Runnable, ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private Thread t;
	private ServerSocket ss;
	private Socket s;
	private PrintStream ps;
	private BufferedReader br;
	final int PORT = 777;
	String client_msg, server_msg;
	private volatile boolean WORK = false;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingServer1 frame = new SwingServer1();
					frame.setTitle("Server");
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void run() {
		try {
				while(WORK) {
					client_msg = br.readLine();
					
					if(client_msg.length() != 0) {
						textArea.setText( textArea.getText() + "\n" + "Client: " + client_msg);
						System.out.println("Client: " + client_msg);
					}
				}
		}catch(Exception e) {
			System.out.println("Error Caught: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public SwingServer1() throws Exception {
		//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(33, 0, 514, 394);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(33, 399, 442, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton sendBtn = new JButton("Send");
		sendBtn.setBounds(473, 398, 74, 25);
		contentPane.add(sendBtn);
		
		sendBtn.addActionListener(this);
		
		//	Creating SockerServer class
		ss = new ServerSocket(PORT);
		
		//	Wait for client to connect;
		s = ss.accept();
		
		//	Create streams for getting and putting data to stream
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		ps = new PrintStream(s.getOutputStream());
	
		//	Creating thread for handling incoming message.
		WORK = true;
		t = new Thread(this, "thread");
		t.start();
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					System.out.println("Ending Thread");
					WORK = false;
					Thread.sleep(1000);
					t.join();	//	Waiting for thread to end
					System.out.println("Thread Ended");
					ps.close();
					br.close();
					s.close();
					ss.close();
				}catch(Exception e) {
					System.out.println("Error while closing Server: " + e);
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		server_msg = textField.getText().toString();
		
		if(server_msg.length() != 0) {
			ps.println(server_msg);
			textArea.setText( textArea.getText() + "\n" + "Server: " + server_msg);
			textField.setText("");
			System.out.println("Server: " + server_msg);
		}
	}
}
