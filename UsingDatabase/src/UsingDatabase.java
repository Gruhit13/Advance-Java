import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.awt.Color;

public class UsingDatabase extends JFrame implements DBConstants, ActionListener {

	private JPanel contentPane;
	private JTextField id_tf;
	private JTextField name_tf;
	private JTextField age_tf;
	private JTextArea textArea;
	private PreparedStatement ps1, ps2;
	private Connection con;
	private ResultSet rs;
	private JButton clear;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		UsingDatabase frame = new UsingDatabase();
		frame.setVisible(true);
		frame.setTitle("DataBase");
	}

	/**
	 * Create the frame.
	 */
	public UsingDatabase() {
		try {
			Class.forName(MSAccessDriver);
			
			System.out.println("Connection setting up");
			con = DriverManager.getConnection(MsAccessDB+"Database1.accdb");
			System.out.println("Connection setted up");
			
			ps1 = con.prepareStatement("insert into students values(?, ?, ?)");
			ps2 = con.prepareStatement("select * from students");
		} catch(Exception e) {
			e.printStackTrace();
		}
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 681, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label1 = new JLabel("ID");
		label1.setFont(new Font("Arial", Font.BOLD, 17));
		label1.setBounds(47, 13, 34, 16);
		contentPane.add(label1);
		
		id_tf = new JTextField();
		id_tf.setFont(new Font("Arial", Font.PLAIN, 15));
		id_tf.setBounds(47, 42, 116, 22);
		contentPane.add(id_tf);
		id_tf.setColumns(10);
		
		JLabel label2 = new JLabel("Name");
		label2.setFont(new Font("Arial", Font.BOLD, 17));
		label2.setBounds(251, 13, 80, 16);
		contentPane.add(label2);
		
		name_tf = new JTextField();
		name_tf.setFont(new Font("Arial", Font.PLAIN, 15));
		name_tf.setColumns(10);
		name_tf.setBounds(251, 42, 116, 22);
		contentPane.add(name_tf);
		
		JLabel label3 = new JLabel("Age");
		label3.setFont(new Font("Arial", Font.BOLD, 17));
		label3.setBounds(433, 13, 34, 16);
		contentPane.add(label3);
		
		age_tf = new JTextField();
		age_tf.setFont(new Font("Arial", Font.PLAIN, 15));
		age_tf.setColumns(10);
		age_tf.setBounds(433, 42, 116, 22);
		contentPane.add(age_tf);
		
		textArea = new JTextArea();
		textArea.setBounds(47, 235, 525, 249);
		contentPane.add(textArea);
		
		JButton submit = new JButton("submit");
		submit.setBounds(47, 197, 97, 25);
		contentPane.add(submit);
		
		clear = new JButton("clear");
		clear.setForeground(Color.WHITE);
		clear.setBackground(Color.RED);
		clear.setBounds(473, 197, 97, 25);
		contentPane.add(clear);
		
		clear.addActionListener(this);
		
		submit.addActionListener(this);
		
		//	Adding windows closing event to close all connection and state objs
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					con.close();
				}catch(Exception e) {
					System.out.println("Erro while closing connections: " + e);
					e.printStackTrace();
				} finally {
					System.exit(0);
				}
			}
		});
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals("clear")) {
			textArea.setText("");
		} 
		
		if(ae.getActionCommand().toString().equals("submit")) {
			if(id_tf.getText().toString().isEmpty()) {
				textArea.setText("Enter ID");
			} else if(name_tf.getText().toString().isEmpty()) {
				textArea.setText("\nEnter Name");
			} else if(age_tf.getText().toString().isEmpty()) {
				textArea.setText("\nEnter Age");
			} else {
				textArea.setText("");
				insertData();
			}
		}
	}
	
	public void insertData() {
		try {
			ps1.setString(1, id_tf.getText().toString());
			ps1.setString(2, name_tf.getText().toString());
			ps1.setString(3, age_tf.getText().toString());
			
			int status = ps1.executeUpdate();
			
			if(status == 1) {
				displayData();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void displayData() {
		textArea.setText("");
		
		try {
			ResultSet rs = ps2.executeQuery();
			ResultSetMetaData rmd = rs.getMetaData();
			String results;
			
			while(rs.next()) {
				results = "";
				for(int i = 1; i <= rmd.getColumnCount(); i++) {
					results += rs.getString(i) + "\t";
				}
				
				results += "\n";
				//	Setting up text;
				textArea.setText(textArea.getText().toString() + results);
			}
			
		} catch(SQLException e) {
			System.out.println("Error in displaying Data: " + e);
			e.printStackTrace();
		}
	}
}