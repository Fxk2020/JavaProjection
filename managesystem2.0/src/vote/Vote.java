package vote;

import javax.swing.*;

import common.Message;
import common.MessageType;
import common.SetVote;
import common.Users;
import log.Register;
import tools.MyTools;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Vote extends JFrame implements ActionListener{

	final int W = 500;
	final int H = 680;
	
	
	public Socket s;
	
	
	//���ڽ��ܵı�ǩ
	JLabel  l2,l3,l4,l5,l6;
	//����������ݵ�
	JTextField f2,f3,f4,f5;
	JTextArea area;
	
	JButton b1 ;
	
	//���ڻ�ȡһϵ������
	String item;
	String option1;
	String option2;
	String option3;
	String option4;
	
	String vote_name = null;
	
	public Vote(String name ) {
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
		
		//��÷���ͶƱ�˵�����
        vote_name =name;
		
		this.setSize(W, H);
		this.setVisible(true);
		this.setTitle("                                      ����ͶƱ");
		this.setResizable(false);// ʹ�û����ܵ��ڴ��ڵĴ�С
//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		// ʹ����λ����Ļ������
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension size = kit.getScreenSize(); // �����Ļ�ķֱ��ʴ�С
		double w = size.getWidth();
		double h = size.getHeight();
		int x = (int) ((w - W) / 2);
		int y = (int) ((h - H) / 2);
		this.setLocation(x, y);
		
		//��ǩ�ļ��룺
		l2 = new JLabel("��ҪͶƱ�����ݣ�");
		l2.setFont(MyTools.f0);
		l2.setBounds(160,20,200,50);
		l3 = new JLabel("ѡ��һ��");
		l3.setFont(MyTools.f3);
		l3.setBounds(45,200,200,50);
		l4 = new JLabel("ѡ�����");
		l4.setFont(MyTools.f3);
		l4.setBounds(45,300,200,50);
		l5 = new JLabel("ѡ������");
		l5.setFont(MyTools.f3);
		l5.setBounds(45,400,200,50);
		l6 = new JLabel("ѡ���ģ�");
		l6.setFont(MyTools.f3);
		l6.setBounds(45,500,200,50);
		
		//�ı���ļ��룺
		area = new JTextArea(75,75);
		area.setFont(MyTools.f2);
		area.setBounds(50, 70,400, 100);
		f2 = new JTextField(25);
		f2.setFont(MyTools.f3);
		f2.setBounds(120, 210, 250, 50);
		f3 = new JTextField(25);
		f3.setFont(MyTools.f3);
		f3.setBounds(120, 310, 250, 50);
		f4 = new JTextField(25);
		f4.setFont(MyTools.f3);
		f4.setBounds(120, 410, 250, 50);
		f5 = new JTextField(25);
		f5.setFont(MyTools.f3);
		f5.setBounds(120, 510, 250, 50);
		
		//��ť�ļ��룺
		b1 = new JButton("����ͶƱ");
		b1.setBackground(new Color(30, 144, 255));
		b1.setForeground(Color.white);
		b1.setFont(MyTools.f5);
		b1.setBounds(150,575 , 150, 50);
		
		b1.addActionListener(this);
		
//		//������������� �������޷�������� ��Ϊ�ӿڷ���
//		b1.addActionListener(new ActionListener() {
//			
//			
//			public void actionPerformed(ActionEvent e) {
//			
//				
//				
//			}
//		});
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		

		
		panel.add(l2);
		panel.add(l3);
		panel.add(l4);
		panel.add(l5);
		panel.add(l6);
		panel.add(area);
		panel.add(f2);
		panel.add(f3);
		panel.add(f4);
		panel.add(f5);
		panel.add(b1);
		
		//��ӱ���ͼƬ
		ImageIcon backGroundIma = new ImageIcon("imag/maobi.jpg"); // ��Java��Ŀ�·�ͼƬ
		JLabel backGroundPic = new JLabel(backGroundIma); // ����һ����ǩ��ͼƬ���ڱ�ǩ��
		backGroundPic.setBounds(0, 0, 500, 750); // ����X��Y��W��H��С���볬��JFrame�Ĵ�С����ͼƬ���Ḳ�Ǵ���
		this.getLayeredPane().add(backGroundPic, new Integer(Integer.MIN_VALUE));
		((JPanel) this.getContentPane()).setOpaque(false);
		panel.setOpaque(false); // �������Ϊ͸��SETOpaque��������
	
		this.add(panel);
	}

	public static void main(String[] args) {
		
        
        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				 new Vote("������");
			}
		});
	
}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			s = new Socket("127.0.0.1", 8888);
			
			//��ȡһϵ������
			item = area.getText();
			option1 = f2.getText();
			option2 = f3.getText();
			option3 = f4.getText();
			option4 = f5.getText();

			SetVote tickets = new SetVote();
			tickets.setName(vote_name);
			tickets.setItem(item);
			tickets.setOption1(option1);
			tickets.setOption2(option2);
			//��û��3��4ʱ���������ݿ�
			if((!option3.equals(""))&&(!option4.equals(""))) {
				tickets.setOption3(option3);
				tickets.setOption4(option4);
			}
			
			
			Users u = new Users();
			u.setTicket(tickets);
			u.setDosomething(MessageType.message_vote);
			
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(u);
			oos.flush();
			
			Message m = null;
			while (m == null) {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				m = (Message) ois.readObject();

			}
			
			if(m.getInformation().equals("����ͶƱ�ɹ�")) {
				JOptionPane.showMessageDialog(null, "���ѷ���ͶƱ��");
				
				this.dispose();
				
			}
			

			
		} catch (UnknownHostException e1) {
			
			e1.printStackTrace();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		
	}

}
