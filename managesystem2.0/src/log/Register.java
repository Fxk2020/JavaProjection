package log;

import javax.swing.*;

//import com.microsoft.sqlserver.jdbc.SQLServerException;

import common.MessageType;
import common.Users;
import model.ConServer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Register extends JPanel {
	final int W = 450;
	final int H = 240;
	JFrame this1 = new JFrame();

	JTextField f1 = new JTextField(25);// ��ʵ����
	JPasswordField f2 = new JPasswordField(25);// ����
	JPasswordField f3 = new JPasswordField(25);// ��������
	JTextField fie1 = new JTextField(25);// ѧ��
	JTextField fie2 = new JTextField(25);// �ǳ�


	public Register() {
//this1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//
		
		this1.setSize(W, H);
		this1.setVisible(true);
		this1.setTitle("�û�ע��");

		this1.addWindowListener(new WindowAdapter() {// ������

			public void windowClosing(WindowEvent e) {
				this1.setVisible(false);
			}
		});

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension size = kit.getScreenSize(); // �����Ļ�ķֱ��ʴ�С
		double w = size.getWidth();
		double h = size.getHeight();
		int x = (int) ((w - W) / 2);
		int y = (int) ((h - H) / 2);
		this1.setLocation(x, y);// ʹ����λ����Ļ������

		Font font2 = new Font("����", Font.PLAIN, 18);
		JLabel l1 = new JLabel("����");
		JLabel bel1 = new JLabel("ѧ��");
		JLabel bel2 = new JLabel("�ǳ�");// ��ӵ����ݿ��user
		JLabel l2 = new JLabel("����");
		JLabel l3 = new JLabel("ȷ������");
		l1.setFont(font2);
		l2.setFont(font2);
		l3.setFont(font2);
		bel1.setFont(font2);
		bel2.setFont(font2);
		l1.setForeground(new Color(0,0,0));
		l2.setForeground(new Color(0,0,0));
		l3.setForeground(new Color(0,0,0));
		bel1.setForeground(new Color(0,0,0));
		bel2.setForeground(new Color(0,0,0));

		GridBagLayout lay = new GridBagLayout();// gridbaglayout���ֹ�����
		this.setLayout(lay);
		JLabel label1 = new JLabel("ѧ��ע��");
		Font font = new Font("����", Font.PLAIN, 25);// ����1������ʵ��
		label1.setFont(font); // ����JLabel������
		label1.setForeground(new Color(255, 100, 89));// �������ֵ���ɫ

		JButton b1 = new JButton("ȷ��");
		b1.setBackground(new Color(30, 144, 255));
		b1.setForeground(Color.white);
		B1listener listener1 = new B1listener();
		b1.addActionListener(listener1);

		GridBagConstraints con = new GridBagConstraints();
		con.fill = GridBagConstraints.BOTH;
		con.anchor = GridBagConstraints.NORTH;

		this.add(label1, con, 1, 0, 1, 1);
		this.add(l1, con, 0, 1, 1, 1);
		this.add(bel1, con, 0, 2, 1, 1);
		this.add(bel2, con, 0, 3, 1, 1);
		this.add(l2, con, 0, 4, 1, 1);
		this.add(l3, con, 0, 5, 1, 1);
		this.add(f1, con, 1, 1, 2, 1);
		this.add(fie1, con, 1, 2, 2, 1);
		this.add(fie2, con, 1, 3, 2, 1);
		this.add(f2, con, 1, 4, 2, 1);
		this.add(f3, con, 1, 5, 2, 1);
		this.add(b1, con, 2, 7, 0, 0);

		ImageIcon backGroundIma = new ImageIcon("imag/ͼƬ1.jpg"); // ��Java��Ŀ�·�ͼƬ
		JLabel backGroundPic = new JLabel(backGroundIma); // ����һ����ǩ��ͼƬ���ڱ�ǩ��
		backGroundPic.setBounds(0, 0, 450, 240); // ����X��Y��W��H��С���볬��JFrame�Ĵ�С����ͼƬ���Ḳ�Ǵ���
		this1.getLayeredPane().add(backGroundPic, new Integer(Integer.MIN_VALUE));
		((JPanel) this1.getContentPane()).setOpaque(false);
		this.setOpaque(false); // �������Ϊ͸��SETOpaque��������

		this1.setResizable(false);// ʹ�û����ܵ��ڴ��ڵĴ�С
		this1.setContentPane(this);
	}

	public void add(Component c, GridBagConstraints grid, int x, int y, int w, int h) {
		grid.gridx = x;
		grid.gridy = y;
		grid.gridwidth = w;
		grid.gridheight = h;
		add(c, grid);
	}

	private class B1listener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			String name = f1.getText();//��ʵ����
			String number = fie1.getText();//ѧ��

			String user = fie2.getText();// �ǳ�
			String pass1 =String.valueOf( f2.getPassword());//����
			String pass2 =String.valueOf( f3.getPassword());
	if ((!f1.getText().equals("")) && (!fie1.getText().equals("")) && (!fie2.getText().equals(""))) {// ������λ������һ���ǿյľͲ�ִ���������)		
	   if(pass1.equals(pass2)) {
				
			Users u = new Users();
			u.setId(number);
			u.setName(name);
			u.setvName(user);
			u.setPassword(pass1);
			u.setDosomething("6");
			
			ConServer cs = new ConServer();
			cs.sendInformationLogin(u);
			
			
			
				
			}
		else {
				JOptionPane.showMessageDialog(null, "������������벻һ��", "����������",JOptionPane.ERROR_MESSAGE);
			}}
	else {
		JOptionPane.showMessageDialog(null, "�е������ǿյ�", "���������", JOptionPane.ERROR_MESSAGE);
		
	}

		
	}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {                      // �����ڲ��࣬��д��run����
			public void run() {
				new Register();
			}
		});

	}
	}


