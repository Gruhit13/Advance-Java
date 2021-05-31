import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class SwingFileClient extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	BufferedReader in;
	PrintStream ps;
	Socket s;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingFileClient frame = new SwingFileClient();
					frame.setTitle("Client");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public SwingFileClient() throws Exception{
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter File");
		lblNewLabel.setBounds(25, 13, 56, 16);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(91, 10, 248, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton sendBtn = new JButton("Send");
		sendBtn.setBounds(338, 9, 82, 23);
		contentPane.add(sendBtn);
		
		sendBtn.addActionListener(this);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(25, 66, 395, 174);
		contentPane.add(textArea);
		
		JLabel lblNewLabel_1 = new JLabel("File Content");
		lblNewLabel_1.setBounds(183, 45, 82, 16);
		contentPane.add(lblNewLabel_1);
		
		s = new Socket("localhost", 778);
		
		//	get stream
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		ps = new PrintStream(s.getOutputStream());
		
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					ps.close();
					in.close();
					s.close();
				}catch(Exception e) {
					System.out.println("LOG: " + "Error while closing net objects: " + e);
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		String fileName = textField.getText().toString();
		
		if(fileName.length() != 0) {
			ps.println(fileName);
			
			try {
				//	Check if given file is present with sever 
				if(in.readLine().equals("yes")) {
					textField.setText("");
					System.out.println("File Found");
					// Creating Thread
					String fileContent;
					
					while((fileContent = in.readLine()) != null) {
						textArea.setText( textArea.getText() + "\n" + fileContent );
					}
				} else {
					textField.setText( "No file with name " + fileName + " Found");
				}
				
			}catch(IOException ie) {
				System.out.println("LOG: " + "Error while reading data from server." + ie);
				ie.printStackTrace();
			}
			
		}
	}
}
