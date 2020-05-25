package ����;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

import common.Message;
import common.MessageType;
import common.Users;
import model.ConServer;


public class Informtion extends JFrame {
	final int W = 360;
	final int H = 460;
	static JTextArea area1 = new JTextArea(18, 15);

	public Socket s;

	public Informtion() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			// �ı���
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

//    	System.out.println("���ѵ��ù�����");
		this.setTitle("                    ����");
		this.setSize(W, H);
//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);// ʹ����λ������
		this.setResizable(false);// ʹ�û����ܵ��ڴ��ڵĴ�С

		JLabel label1 = new JLabel("���棨������ۿ�����");
		Font font = new Font("����", Font.PLAIN, 25);// ����1������ʵ��
		label1.setFont(font);// ����JLabel������
		label1.setForeground(Color.white);// �������ֵ���ɫ
		label1.setBounds(50, 0, 300, 85);

		area1.setLineWrap(true);// ʹ�ı������Զ�����
		area1.setWrapStyleWord(true);// ʹ��������
		JScrollPane scrollpane1 = new JScrollPane(area1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane1.setBounds(25, 60, 300, 300);

		JButton bu = new JButton("�鿴���й���");
		bu.setBackground(new Color(30, 144, 255));
		bu.setForeground(Color.white);
		bu.setBounds(110, 366, 120, 50);
		BuListener bu1 = new BuListener();
		bu.addActionListener(bu1);// �Ӽ�����������

//		b11 = new JButton("�鿴���¹���");
//		b11.setBackground(new Color(30, 144, 255));
//		b11.setForeground(Color.white);
//		b11.setBounds(60, 366, 120, 50);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		ImageIcon backGroundIma = new ImageIcon("imag/����2.jpg"); // ��Java��Ŀ�·�ͼƬ
		JLabel backGroundPic = new JLabel(backGroundIma); // ����һ����ǩ��ͼƬ���ڱ�ǩ��
		backGroundPic.setBounds(0, 0, 360, 460); // ����X��Y��W��H��С���볬��JFrame�Ĵ�С����ͼƬ���Ḳ�Ǵ���
		this.getLayeredPane().add(backGroundPic, new Integer(Integer.MIN_VALUE));
		((JPanel) this.getContentPane()).setOpaque(false);
		panel.setOpaque(false); // �������Ϊ͸��SETOpaque��������
		
		try {
			s = new Socket("127.0.0.1", 8888);

			Users u = new Users();
			u.setDosomething(MessageType.message_information);

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(u);
			oos.flush();

			Message m = null;
			while (m == null) {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				m = (Message) ois.readObject();

			}
			if (m.getMessType().equals(MessageType.message_information_succeed)) {
				
				area1.setFont(new Font("����",0,18));
				area1.setText(m.getInformation());

			}

		} catch (UnknownHostException e1) {

			e1.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

		panel.add(label1);
		panel.add(scrollpane1);
		panel.add(bu);

		this.add(panel);

//	    System.out.println("����������");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new Informtion();
			}
		});

	}

	class BuListener implements ActionListener {// �鿴���๫��

		public void actionPerformed(ActionEvent e) {
			
			
				try {
					s = new Socket("127.0.0.1", 8888);

					Users u = new Users();
					u.setDosomething("�鿴���๫��");

					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(u);
					oos.flush();

					Message m = null;
					while (m == null) {
						ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
						m = (Message) ois.readObject();

					}
					if (m.getMessType().equals("�鵽��")) {
						
						area1.setFont(new Font("����",0,18));
						area1.setText(m.getInformation());

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
}
