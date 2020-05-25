package log;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import common.Message;
import common.MessageType;
import common.Users;
import mainpage.MainPage;
import mainpage.MainPage2;
import model.ConServer;
import ����.Informtion;




public class Log extends JPanel implements ActionListener {

	static final int W = 500;
	static final int H = 330;
	JFrame frame = new JFrame();
	
	Socket s = null;
	
	JButton b3 ;
	final static JTextField t1 = new JTextField(15);
	final JPasswordField t2 = new JPasswordField(18);                    // ��־Ϊ��������򣬼�������ַ�������ʾ��

	
	
	public Log() {
		super();	
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
		
		t1.addKeyListener(k);
		t2.addKeyListener(k);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {                      // �����ڲ��࣬��д��run����
			public void run() {
				new Log().execute();
			}
		});
		
		

	}
	
	//�Լ��̻س����ļ���,������Ҫ���ı�����м����������Ƕ�����JFrame������м���
	KeyListener k = new KeyListener() {
		
		
		public void keyTyped(KeyEvent e) {
			// TODO �Զ����ɵķ������
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO �Զ����ɵķ������
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				String name= t1.getText();
				String password=String.valueOf(t2.getPassword());
				
				if(!(name.equals(""))&&!(password.equals(""))) {
					Users u = new Users();
					u.setvName(name);
					u.setPassword(password);
					u.setDosomething("7");
					
					ConServer cs = new ConServer();
					if(cs.sendInformationLogin(u))
					{
						frame.setVisible(false);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "�е������ǿյ�", "���������", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
	};


	
	public void add(Component c, GridBagConstraints grid, int x, int y, int w, int h) {
		grid.gridx = x;
		grid.gridy = y;
		grid.gridwidth = w;
		grid.gridheight = h;
		add(c, grid);
	}

	public void execute() {
		
		
		
		frame.setSize(W, H);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("�༶����ϵͳ");


		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension size = kit.getScreenSize(); // �����Ļ�ķֱ��ʴ�С
		double w = size.getWidth();
		double h = size.getHeight();
		int x = (int) ((w - W) / 2);
		int y = (int) ((h - H) / 2);
		frame.setLocation(x, y);// ʹ����λ����Ļ������

		JLabel l0 = new JLabel("  ");// ֻ�������������λ��λ�õ����

		Font font = new Font("����", Font.PLAIN, 25);// ����1������ʵ��
		JLabel l1 = new JLabel("�ǳ�: ");
		l1.setFont(font);
		l1.setForeground(Color.BLACK);

		JLabel l2 = new JLabel("����: ");
		l2.setFont(font);
		l2.setForeground(Color.BLACK);

		JButton b1 = new JButton("ע��");
		b1.setBackground(new Color(30, 144, 255));
		b1.setForeground(Color.white);
		Listenerb1 listener2 = new Listenerb1();
		b1.addActionListener(listener2);

		b3 = new JButton("��¼");
		b3.setBackground(new Color(30, 144, 255));
		b3.setForeground(Color.white);
		b3.addActionListener(this);
	
		
		GridBagLayout lay = new GridBagLayout();                           // gridbaglayout���ֹ�����
		this.setLayout(lay);
		frame.add(this);
		GridBagConstraints con = new GridBagConstraints();
		con.fill = GridBagConstraints.BOTH;
		con.anchor = GridBagConstraints.SOUTH;// �����û�пռ��ʱ��ʹ���λ���ϲ�

		this.add(l0, con, -1, 3, 1, 1);
		this.add(l1, con, -1, 5, 1, 1);// �˺ţ�����
		this.add(l2, con, -1, 7, 1, 1);
		this.add(b1, con, 2, 5, 1, 2);// ע�ᣬ�һ�����
		this.add(b3, con, 2, 7, 1, 2);// ��½��ť
		this.add(t1, con, 1, 5, 1, 2);// �ı���
		this.add(t2, con, 1, 7, 1, 3);
		
		ImageIcon backGroundIma = new ImageIcon("imag/ɽ��3.png"); // ��Java��Ŀ�·�ͼƬ
		JLabel backGroundPic = new JLabel(backGroundIma); // ����һ����ǩ��ͼƬ���ڱ�ǩ��
		backGroundPic.setBounds(0, 0, 500, 300); // ����X��Y��W��H��С���볬��JFrame�Ĵ�С����ͼƬ���Ḳ�Ǵ���
		frame.getLayeredPane().add(backGroundPic, new Integer(Integer.MIN_VALUE));
		((JPanel) frame.getContentPane()).setOpaque(false);
		this.setOpaque(false); // �������Ϊ͸��SETOpaque��������

		frame.setResizable(false);// ʹ�û����ܵ��ڴ��ڵĴ�С
		frame.add(this);


	}

	public static String username() {//��õ�½���û���
		return t1.getText();
	}


	// ע�����
	class Listenerb1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new Register();
				}
			});
		}

	}

	
	public void actionPerformed(ActionEvent e) {//��½��ť�ļ���
		String name= t1.getText();
		String password=String.valueOf(t2.getPassword());
		
		if(!(name.equals(""))&&!(password.equals(""))) {
			Users u = new Users();
			u.setvName(name);
			u.setPassword(password);
			u.setDosomething("7");
			
			ConServer cs = new ConServer();
			if(cs.sendInformationLogin(u))
			{
				frame.setVisible(false);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "�е������ǿյ�", "���������", JOptionPane.ERROR_MESSAGE);
		}

	}

	
	

}
