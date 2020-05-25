package vote;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.*;

import common.Message;
import common.MessageType;
import common.SetVote;
import common.Users;
import common.students;
import log.Log;
import tools.MyTools;

public class TakepartinVote extends JFrame implements ActionListener {

	Socket s;

	final int W = 500, H = 650;
	JButton b1;
	int t;

	private JCheckBox jcb1 = null;
	private JCheckBox jcb2 = null;
	private JCheckBox jcb3 = null;
	private JCheckBox jcb4 = null;
	private JLabel item = null;
	private JLabel name = null;
	private JTextArea suggesstion = new JTextArea();

	// ���ڽ������еĽ���
	private JTextArea suggesstion2 = new JTextArea();
	String Sug;
	
	students stu0 = null;

	SetVote v = null;

	public TakepartinVote(students stu0) {

		this.stu0 = stu0;
		
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

		//frame���ܸı��С
		this.setResizable(false);
		
		this.setTitle("����ͶƱ");
		this.setSize(W, H);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ʹ��setbounds����֮ǰ����ʹԭʼ������Ϊ��
		this.setLayout(null);

		try {
			s = new Socket("127.0.0.1", 8888);

			Users u = new Users();
			u.setDosomething(MessageType.message_vote_take);

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(u);
			oos.flush();

			Message m = null;
			while (m == null) {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				m = (Message) ois.readObject();

			}
			if (m.getInformation().equals("ͶƱ�б�")) {
				v = m.getTicket();

				// ����ͶƱ����
				item = new JLabel(v.getItem());
				name = new JLabel(v.getName());
				Sug = v.getSuggesstion();

			}
			JLabel item0 = new JLabel("ͶƱ����:");
			item0.setFont(MyTools.f18);
			item0.setBounds(70, 0, 100, 50);

			JLabel name0 = new JLabel("������:");
			name0.setFont(MyTools.f7);
			name0.setBounds(120, 25, 100, 50);

			// ���ͶƱ���ݺ�ͶƱ������
			item.setFont(MyTools.f18);
			item.setBounds(200, 0, 300, 50);
			name.setFont(MyTools.f7);
			name.setBounds(189, 25, 100, 50);

			// ����ͶƱ��չʾ��ѡ��
			jcb1 = new JCheckBox(v.getOption1());
			jcb1.setBounds(160, 220, 150, 32);
			jcb2 = new JCheckBox(v.getOption2());
			jcb2.setBounds(160, 250, 150, 32);
			jcb1.addItemListener(new MyItemListener());
			jcb2.addItemListener(new MyItemListener());
			jcb1.setFont(MyTools.f3);
			jcb2.setFont(MyTools.f3);

			// �Կ�ѡ�������
//			System.out.println(v.getOption3().trim().equals(""));
			if ((v.getOption3() != null) && (v.getOption4() != null)) {
				jcb3 = new JCheckBox(v.getOption3());
				jcb3.setBounds(160, 280, 150, 32);
				jcb4 = new JCheckBox(v.getOption4());
				jcb4.setBounds(160, 310, 150, 32);
				jcb3.addItemListener(new MyItemListener());
				jcb4.addItemListener(new MyItemListener());
				jcb3.setFont(MyTools.f3);
				jcb4.setFont(MyTools.f3);
				String a4 = new String("ѡ��3�ѵ�Ʊ" + v.getNumber3());
				JLabel la3 = new JLabel(a4);
				la3.setBounds(50, 280, 100, 32);
				String a5 = new String("ѡ��4�ѵ�Ʊ" + v.getNumber4());
				JLabel la4 = new JLabel(a5);
				la4.setBounds(50, 310, 100, 32);
				this.add(jcb3);
				this.add(jcb4);
				this.add(la3);
				this.add(la4);
			}

			b1 = new JButton("ȷ��");
			b1.setFont(MyTools.f9);
			b1.setBackground(new Color(30, 144, 255));
			b1.setForeground(Color.white);
			b1.setBounds(175, 540, 100, 45);

			// ��ǰƱ������ʾ
			String a6 = new String("ѡ��1�ѵ�Ʊ" + v.getNumber1());
			JLabel la1 = new JLabel(a6);
			la1.setBounds(50, 220, 100, 32);
			String a3 = new String("ѡ��2�ѵ�Ʊ" + v.getNumber2());
			JLabel la2 = new JLabel(a3);
			la2.setBounds(50, 250, 100, 32);

			// �������
			JLabel su = new JLabel("��Ĳ������:");
			su.setFont(MyTools.f7);
			su.setBounds(75, 350, 150, 50);
			suggesstion.setFont(MyTools.f8);
			// �ӹ�����
			suggesstion.setLineWrap(true);// ʹ�ı������Զ�����
			suggesstion.setWrapStyleWord(true);// ʹ��������
			JScrollPane scrollpane1 = new JScrollPane(suggesstion, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollpane1.setBounds(50, 400, 380, 130);

			// �������
			if(Sug!=null) {
				suggesstion2.setText("	���еĽ���Ϊ��\n" + Sug);
			}else {
				suggesstion2.setText("	���޽��飡");
			}
			
			suggesstion2.setFont(MyTools.f8);
			suggesstion2.setLineWrap(true);// ʹ�ı������Զ�����
			suggesstion2.setWrapStyleWord(true);// ʹ��������
			JScrollPane scrollpane2 = new JScrollPane(suggesstion2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollpane2.setBounds(50, 65, 380, 150);

			// b1�Ӽ�����

			b1.addActionListener(this);

			this.add(jcb1);
			this.add(jcb2);

			this.add(item);
			this.add(item0);
			this.add(name);
			this.add(name0);
			this.add(b1);
			this.add(la1);
			this.add(la2);

			this.add(su);
			this.add(scrollpane1);
			this.add(scrollpane2);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// ��ӱ���ͼƬ
		ImageIcon backGroundIma = new ImageIcon("imag/ͶƱ.png"); // ��Java��Ŀ�·�ͼƬ
		JLabel backGroundPic = new JLabel(backGroundIma); // ����һ����ǩ��ͼƬ���ڱ�ǩ��
		backGroundPic.setBounds(0, 0, 500, 670); // ����X��Y��W��H��С���볬��JFrame�Ĵ�С����ͼƬ���Ḳ�Ǵ���
		this.getLayeredPane().add(backGroundPic, new Integer(Integer.MIN_VALUE));
		((JPanel) this.getContentPane()).setOpaque(false);
//		this.setOpaque(false); // �������Ϊ͸��SETOpaque��������

	}

	// ����ͶƱ
	public void actionPerformed(ActionEvent e) {

		try {

			SetVote vote2 = new SetVote();

			// �Ը�ѡ��ļ���,��������֤����ѡ����һ��
			if (jcb1.isSelected() || jcb2.isSelected() || jcb3.isSelected() || jcb4.isSelected()) {

				// ͨ��������if else���ȷ��Ʊ��ֻ���ۼӷ�ֹ�����
				if (jcb1.isSelected()) {
					System.out.println(v.getNumber1());
					vote2.setNumber1(v.getNumber1() + 1);
				} else {
					vote2.setNumber1(v.getNumber1());
				}
				if (jcb2.isSelected()) {
					System.out.println(v.getNumber2());
					vote2.setNumber2(v.getNumber2() + 1);
				} else {
					vote2.setNumber2(v.getNumber2());
				}
				if (jcb3.isSelected()) {
					System.out.println(v.getNumber3());
					vote2.setNumber3(v.getNumber3() + 1);
				} else {
					vote2.setNumber3(v.getNumber3());
				}
				if (jcb4.isSelected()) {
					System.out.println(v.getNumber4());
					vote2.setNumber4(v.getNumber4() + 1);
				} else {
					vote2.setNumber4(v.getNumber4());
				}

				Users u2 = new Users();

				u2.setDosomething(MessageType.message_vote_take_successful);

				// System.out.println(suggesstion.getText().trim().equals(""));

				// ��û�н���Ĵ���
				if (suggesstion.getText().trim().equals("")) {
					vote2.setSuggesstion("no");
				}
				else {
					vote2.setSuggesstion(suggesstion.getText());
				}
				vote2.setItem(v.getItem());

                vote2.setVote_name(stu0.getName());				
				
				u2.setTicket(vote2);

				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject(u2);
				oos.flush();

				ObjectInputStream ois = null;
				Message m = null;
				while (m == null) {
					ois = new ObjectInputStream(s.getInputStream());
					m = (Message) ois.readObject();

				}

				if (m.getInformation().equals("ͶƱ�ɹ�")) {
					JOptionPane.showMessageDialog(null, "ͶƱ�ɹ�");
				}
				else if(m.getInformation().equals("�ظ�ͶƱ")) {
					JOptionPane.showMessageDialog(null, "�㲻���ظ�ͶƱ", "���˳���", JOptionPane.ERROR_MESSAGE);
				}
				else if(m.getInformation().equals("ͶƱͶ����")) {
					JOptionPane.showMessageDialog(null, "ͶƱ������ͳ��Ʊ��", "ͶƱ�ѽ�����", JOptionPane.ERROR_MESSAGE);
					
					this.dispose();
				}
			} else {
				JOptionPane.showMessageDialog(null, "�㲻��һ�ѡ", "��ѡ��", JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	// ��ʾ�ÿ��ĶԺ�
	class MyItemListener implements ItemListener {
		private String right = "imag/gou.png";

		// ʵ��ItemListener�ķ�����ֻ��һ��������
		public void itemStateChanged(ItemEvent e) {
			// �õ��������¼�������ֻ�и�ѡ�����Կ���ǿ������ת����
			JCheckBox jcb = (JCheckBox) e.getItem(); // �õ��������¼�
			// �����ѡ���ˣ�����ʾ��ȷ��ͼƬ
			if (jcb.isSelected()) { // ��ʾ��
				jcb.setIcon(new ImageIcon(right));
			} else {
				// ȡ��ͼƬ��ʾ
				jcb.setIcon(null);
			}
		}
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() { // �����ڲ��࣬��д��run����
			public void run() {
				students  sss = new students();
				sss.setName("�ų��");
				new TakepartinVote(sss);
			}
		});

	}

}
