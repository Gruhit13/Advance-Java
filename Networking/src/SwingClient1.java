import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SwingClient1 extends JFrame implements Runnable, ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private Thread t;
	private ServerSocket ss;
	private Socket s;
	private PrintStream ps;
	private BufferedReader br;
	final int PORT = 777;
	private volatile boolean WORK = false;
	
	private String server_msg, client_msg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingClient1 frame = new SwingClient1();
					frame.setTitle("Client");
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
				server_msg = br.readLine();
				
				if(server_msg.length() != 0) {
					textArea.setText( textArea.getText() + "\n" + "Server: " + server_msg);
					System.out.println("Server: " + server_msg);
				}
			}
		}catch(Exception e) {
			System.out.println("Error caught: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public SwingClient1() throws Exception {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		//	Create a socket on port 777
		s = new Socket("localhost", PORT);
		
		//	Get streams for reading and writing data
		
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		ps = new PrintStream(s.getOutputStream());
		
		//	Creating thread
		WORK = true;
		t = new Thread(this, "Client");
		t.start();
		
		this.addWindowListener(new WindowAdapter() {
			public void windowsClosing(WindowEvent we) {
				try {
					System.out.println("Thread Ended");
					ps.close();
					br.close();
					s.close();
					System.out.println("Thread ended");
				}catch(Exception e) {
					System.out.println("Error caught while closing Client: " + e);
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
	}
	
	public void actionPerformed(ActionEvent ae) {
		client_msg = textField.getText().toString();
		
		if(client_msg.length() != 0) {
			ps.println(client_msg);
			textArea.setText( textArea.getText() + "\n" + "Client: " + client_msg);
			textField.setText("");
			System.out.println("Client: " + client_msg);
		}
	}

}
