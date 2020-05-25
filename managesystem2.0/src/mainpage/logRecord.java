package mainpage;

import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.tools.Tool;

import common.Message;
import common.MessageType;
import common.Users;
import tools.MyTools;


public class logRecord extends JFrame {

	Socket s = null;
	
	JTextArea area1 = new JTextArea(18, 15);
	
	final int W = 560;
	final int H = 660;
	
	public logRecord() {
		
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
		
		
		this.setTitle("��־��¼");
		this.setSize(W, H);
//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
        this.setVisible(true);
        this.setLocationRelativeTo(null);// ʹ����λ������
		
        JLabel label1 = new JLabel("��־��¼:");
        label1.setFont(MyTools.f1);
        label1.setBounds(150, 0, 200, 50);
		

        area1.setLineWrap(true);// ʹ�ı������Զ�����
		area1.setWrapStyleWord(true);// ʹ��������
		JScrollPane scrollpane1 = new JScrollPane(area1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane1.setBounds(25, 60, 480, 500);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		 // ��Java��Ŀ�·�ͼƬ
        ImageIcon backGroundIma = new ImageIcon("imag/beautiful.jpg");
		JLabel backGroundPic = new JLabel(backGroundIma); // ����һ����ǩ��ͼƬ���ڱ�ǩ��
		backGroundPic.setBounds(0, 0, 560, 660); // ����X��Y��W��H��С���볬��JFrame�Ĵ�С����ͼƬ���Ḳ�Ǵ���
		this.getLayeredPane().add(backGroundPic, new Integer(Integer.MIN_VALUE));
		((JPanel) this.getContentPane()).setOpaque(false);
		panel.setOpaque(false); // �������Ϊ͸��SETOpaque��������
		
		
		panel.add(label1);
		panel.add(scrollpane1);
		
        this.add(panel);
        
       
		
		try {
			s = new Socket("127.0.0.1", 8888);
			
			Users u = new Users(); 
			u.setDosomething(MessageType.message_record);
			
			//�Ѷ���д��ȥ
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(u);
			oos.flush();
			
			//
			Message m = null;
			while (m == null) {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				m = (Message) ois.readObject();

			}
			if(m.getMessType().equals(MessageType.message_record_successfully)) {
				
				
				
				area1.setText(m.getInformation());
				area1.setFont(MyTools.f6);
			}

			
		} catch (IOException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	
		
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new logRecord();
			}
		});

	}

}
