package ����;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import common.Message;
import common.MessageType;
import common.Users;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import java.net.*;

public class Compile extends JFrame implements ActionListener {
	final int W = 350;
	final int H = 520;

	static String adddata = null;

	//�����˵�����
	String stu_name = null;
	JTextArea area1 = new JTextArea(12, 10);// ����

	JButton b1 = new JButton("ȷ�ϱ༭");
	Socket s = null;

	public Compile(String stu_name) {
		
		this.stu_name = stu_name;
		
		this.setLayout(null);// ��ʹ�ò��ֹ�����
		this.setTitle("  ����");
		this.setSize(W, H);
		this.setVisible(true);
		this.setLocationRelativeTo(null);// ʹ����λ������
		this.setResizable(false);// ʹ�û����ܵ��ڴ��ڵĴ�С
//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel label1 = new JLabel("����༭");
		Font font = new Font("����", Font.PLAIN, 25);// ����1������ʵ��
		label1.setFont(font); // ����JLabel������
		label1.setForeground(Color.black); // �������ֵ���ɫ
		label1.setBounds(125, 10, 100, 50);
		
		area1.setFont(font);
		b1.addActionListener(this);
		area1.setLineWrap(true); // ʹ�ı������Զ�����
		area1.setWrapStyleWord(true); // ʹ��������
		JScrollPane scrollpane1 = new JScrollPane(area1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane1.setBounds(50, 50, 250, 320);

		b1.setBounds(125, 425, 100, 50);
		this.add(b1);

		
		this.add(label1);
		this.add(scrollpane1);

		ImageIcon backGroundIma = new ImageIcon("imag/����3.jpg"); // ��Java��Ŀ�·�ͼƬ
		JLabel backGroundPic = new JLabel(backGroundIma); // ����һ����ǩ��ͼƬ���ڱ�ǩ��
		backGroundPic.setBounds(0, 0, 350, 520); // ����X��Y��W��H��С���볬��JFrame�Ĵ�С����ͼƬ���Ḳ�Ǵ���
		this.getLayeredPane().add(backGroundPic, new Integer(Integer.MIN_VALUE));
		((JPanel) this.getContentPane()).setOpaque(false);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				new Compile("������");

			}
		});

	}

	public void actionPerformed(ActionEvent e) {
		
		try {
			s = new Socket("127.0.0.1", 8888);

			//�������������ݶ���Ϊ��
			if(!(area1.getText().equals(""))) {
			
			Users u = new Users();
			u.setDosomething(MessageType.message_information_Compile);
			u.setName(stu_name);
			u.setInformation(area1.getText());

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(u);
			oos.flush();
			

			Message m = null;
			while (m == null) {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				m = (Message) ois.readObject();

			}
			if (m.getMessType().equals(MessageType.message_information_Compile_succeed)) {
				JOptionPane.showMessageDialog(null, "���ѱ༭�ɹ���");
				
				this.dispose();

			}
			else if(m.getMessType().equals("���޴���")) {
				JOptionPane.showMessageDialog(null, "��������ʵ����");
			}
			}
			else {
				JOptionPane.showMessageDialog(null, "�е������ǿյ�", "���������", JOptionPane.ERROR_MESSAGE);
			}

		} catch (UnknownHostException e1) {

			e1.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		
	}

}
