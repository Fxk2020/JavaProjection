package mainpage;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Game.FiveChessGame;
import Game.Gamemanager;
import Game.SnakeGame;
import file.Download;
import file.FileClient;
import page.groupChat;
import page.login2;
import tools.ImagePanel;
import tools.ManageChat;
import tools.MyTools;
import vote.TakepartinVote;
import vote.Vote;
import ����.Informtion;

public class MainPage extends JFrame implements ActionListener, MouseListener {
	// ������Ҫ�����
	Image titelIcon, dibu, main_image;
	JMenuBar jmBar;
	// һ���˵�
	JMenu information, help, settings;
	// �����˵�
	JMenuItem students, administrator, exit, game, stuname, stunumber;

	// �Ƿ��ǹ���Ա
	boolean b = false;

	// ͼ��
	ImageIcon students_icon, administrator_icon, exit_icon;
	JToolBar toolbar;
	JButton jb1, jb2, jb3;
	JPanel jp_south;

	
	common.students stu0 = null;
	// �м�����
	JPanel p1, p2, p3, p4;
	// �м��ͼƬ���
	ImagePanel p1_imgPanel1;
	JLabel p1_lab1, p1_lab2, p1_lab3, p1_lab4, p1_lab5, p1_lab6;
	Image p1_image;

	// ͶƱ���,�ļ�,������־
	JLabel voteLable, filesLable, noticeLable, diaryLable;
	// �ײ���

	JLabel timeNow, producer, message;

	// Timer�ඨʱ����action�¼�
	javax.swing.Timer t;
	JLabel p2_lab1, p2_lab2;
	JSplitPane jsp1;

	// �л�����
	CardLayout cardP3;

	// �������
//	ChatPage ct;

//	public static void main(String[] args) {
//		MainPage m1 = new MainPage(null);
//	}

	// ��ʼ���˵�
	public void initMenu() {
		// ����ͼ��
		students_icon = new ImageIcon("imag/students.png");
		administrator_icon = new ImageIcon("imag/adm.png");
		exit_icon = new ImageIcon("imag/exit1.png");
		// ����һ���˵�
		information = new JMenu("��Ϣ����");
		settings = new JMenu("ϵͳ����");
		help = new JMenu("����");
		information.setFont(MyTools.f1);
		settings.setFont(MyTools.f1);
		help.setFont(MyTools.f1);

		// �������Ķ����˵�
		students = new JMenuItem("ѧ����Ϣ", students_icon);
		administrator = new JMenuItem("����Ա��Ϣ", administrator_icon);
		exit = new JMenuItem("�˳�", exit_icon);
		game = new JMenuItem("��Ϸ", new ImageIcon("imag/game.jpg"));

		students.setFont(MyTools.f2);
		administrator.setFont(MyTools.f2);
		exit.setFont(MyTools.f2);
		game.setFont(MyTools.f2);

		// ����
		if (b) {
			information.add(administrator);
		} else {
			information.add(students);
		}

		information.add(stuname);
		information.add(stunumber);
		settings.add(exit);
		help.add(game);

		// ��һ���˵����뵽JMenuBar
		jmBar = new JMenuBar();
		jmBar.add(information);
		jmBar.add(settings);
		jmBar.add(help);

		// ��JMenuBar��ӵ�JFrame��
		this.setJMenuBar(jmBar);
	}

	// ��ʼ��������
	public void initToolBar() {

		// �������������
		toolbar = new JToolBar();
		// ���ù������������ƶ�
		toolbar.setFloatable(false);
		jb1 = new JButton(new ImageIcon("imag/diary.png"));
		jb2 = new JButton(new ImageIcon("imag/folder1.png"));
		jb3 = new JButton(new ImageIcon("imag/folder2.png"));
		// �Ѱ�ť���빤����
		toolbar.add(jb1);
		toolbar.add(jb2);

	}

	public void initallPanels() {
		// ����p1���
		p1 = new JPanel(new BorderLayout());

		try {
			p1_image = ImageIO.read(new File("imag/background7.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// ����һ�������ʽ
		Cursor myCursor = new Cursor(Cursor.HAND_CURSOR);

		// ������ܽ���
		this.p1_imgPanel1 = new ImagePanel(p1_image);
		this.p1_imgPanel1.setLayout(new GridLayout(6, 1));
		p1_lab1 = new JLabel("   ��  ��", new ImageIcon("imag/chat.png"), 0);
		p1_lab1.setFont(MyTools.f1);

		p1_lab2 = new JLabel("   Ͷ  Ʊ", new ImageIcon("imag/vote.png"), 0);
		p1_lab2.setFont(MyTools.f1);

		p1_lab3 = new JLabel("   ��  ��", new ImageIcon("imag/notice.png"), 0);
		p1_lab3.setFont(MyTools.f1);

		p1_lab4 = new JLabel("   ��  ��", new ImageIcon("imag/files.png"), 0);
		p1_lab4.setFont(MyTools.f1);

		p1_lab5 = new JLabel("   ��  ־", new ImageIcon("imag/diarylabel.png"), 0);
		p1_lab5.setFont(MyTools.f1);
		
		p1_lab6 = new JLabel("   Ⱥ ��", new ImageIcon("imag/grounpChat.png"), 0);
		p1_lab6.setFont(MyTools.f1);


		// ����Ϊ������,������ƶ���ȥʱ����
		p1_lab1.setEnabled(false);
		p1_imgPanel1.add(p1_lab1);
		p1_lab2.setEnabled(false);
		p1_imgPanel1.add(p1_lab2);
		p1_lab3.setEnabled(false);
		p1_imgPanel1.add(p1_lab3);
		p1_lab4.setEnabled(false);
		p1_imgPanel1.add(p1_lab4);
		p1_lab5.setEnabled(false);
		p1_imgPanel1.add(p1_lab5);
		p1.add(this.p1_imgPanel1);
		p1_lab6.setEnabled(false);
		p1_imgPanel1.add(p1_lab6);
		
		// ��ÿ��label����ʽ
		p1_lab1.setCursor(myCursor);
		p1_lab2.setCursor(myCursor);
		p1_lab3.setCursor(myCursor);
		p1_lab4.setCursor(myCursor);
		p1_lab5.setCursor(myCursor);
		p1_lab6.setCursor(myCursor);
		// ����������
		p1_lab1.addMouseListener(this);
		p1_lab2.addMouseListener(this);
		p1_lab3.addMouseListener(this);
		p1_lab4.addMouseListener(this);
		p1_lab5.addMouseListener(this);
		p1_lab6.addMouseListener(this);

		// ����p2,p3,p4
		p4 = new JPanel(new BorderLayout());
		p2 = new JPanel(new CardLayout());
		p3 = new JPanel(new CardLayout());
		p2_lab1 = new JLabel(new ImageIcon("imag/rigth1.png"));
		p2_lab2 = new JLabel(new ImageIcon("imag/left1.png"));
		p2.add(p2_lab1, "0");
		p2.add(p2_lab2, "1");

		// ��P3����һ��������Ŀ�Ƭ
		this.cardP3 = new CardLayout();
		try {
			main_image = ImageIO.read(new File("imag/background9.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		p3 = new JPanel(this.cardP3);
		ImagePanel ip2 = new ImagePanel(main_image);
		p3.add(ip2, "0");// ������ı���ͼ

		// ��p3��Ӽ���JLable

		voteLable = new JLabel(new ImageIcon("imag/background2.jpg"));
		p3.add(voteLable, "2");
		noticeLable = new JLabel(new ImageIcon("imag/background3.jpg"));
		p3.add(noticeLable, "3");
		filesLable = new JLabel(new ImageIcon("imag/background4.jpg"));
		p3.add(filesLable, "4");
		diaryLable = new JLabel(new ImageIcon("imag/background5.jpg"));
		p3.add(diaryLable, "5");

		// ��p2,p3���뵽p4��
		p4.add(p2, BorderLayout.WEST);
		p4.add(p3, BorderLayout.CENTER);

		// ��ִ���,�ֱ���,p1,p4
		jsp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, p1, p4);
		// ָ��p1(��ߵ�)ռ�������
		jsp1.setDividerLocation(240);
		jsp1.setDividerSize(0);
	}

	public void initdibuPannel() {
		// �������� jp_south��� �ײ����,��ǰʱ��
		jp_south = new JPanel(new BorderLayout());
		producer = new JLabel("������:  2018���  ��Ȫ� ������  ");
		message = new JLabel("����ϵ����");
		producer.setFont(MyTools.f4);
		message.setFont(MyTools.f4);
		t = new Timer(1000, this);
		t.start();
		timeNow = new JLabel(java.util.Calendar.getInstance().getTime().toLocaleString());
		timeNow.setFont(MyTools.f4);
		try {
			dibu = ImageIO.read(new File("imag/dubu5.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImagePanel ip1 = new ImagePanel(dibu);

		ip1.setLayout(new BorderLayout());
		ip1.add(timeNow, BorderLayout.EAST);
		ip1.add(producer, BorderLayout.WEST);
		ip1.add(message, BorderLayout.CENTER);

		jp_south.add(ip1);

	}

	// ������
	public MainPage(common.students s) {
		
		stu0 = s;

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

		// ����û���Ϣ
		stuname = new JMenuItem("��ǰ�û�Ϊ��" + s.getName());
		stunumber = new JMenuItem("ѧ��Ϊ��" + s.getNumber());
		b = s.isAdministrator();

		System.out.println(s.getName());

		// �������
		try {
			titelIcon = ImageIO.read(new File("imag/tite.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ���ó�ʼ���˵�,������,�м����,�ײ�
		this.initMenu();
		this.initToolBar();
		this.initallPanels();
		this.initdibuPannel();

		// ��JFrame ȡ��container
		Container ct = this.getContentPane();
		ct.add(toolbar, BorderLayout.NORTH);
		ct.add(jsp1, BorderLayout.CENTER);
		ct.add(jp_south, BorderLayout.SOUTH);

		// ���˳��Ӽ�����
		quit q = new quit();
		exit.addActionListener(q);

		// ��С��Ϸ��Ӽ�����
		games g = new games();
		game.addActionListener(g);

		// ���ô�С
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(w, h - 30);
		// �رմ���,�˳�ϵͳ
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ô��ڵ�ͼ��
		this.setIconImage(titelIcon);
		this.setVisible(true);
		this.setTitle("ѧ������ϵͳ");
	}

	public void actionPerformed(ActionEvent arg0) {
		this.timeNow.setText("  ��ǰʱ��:   " + Calendar.getInstance().getTime().toLocaleString() + "     ");

	}

	// �ж��û�������ĸ����� JLable
	public void mouseClicked(MouseEvent arg0) {

		if(arg0.getSource() == this.p1_lab1) {//˽��
			
			this.cardP3.show(p3, "1");
			
			new login2(stu0.getName(), stu0.getPassword());
			
			this.setVisible(false);
			
		}
		
		if (arg0.getSource() == this.p1_lab2) {// ͶƱ�׶�

			this.cardP3.show(p3, "2");

			new TakepartinVote(stu0);

		} else if (arg0.getSource() == this.p1_lab3) {
			this.cardP3.show(p3, "3");
			new Informtion();
		} else if (arg0.getSource() == this.p1_lab4) {
			this.cardP3.show(p3, "4");
			// ѡ��Ի��򣬷��ص���һ������
			String[] possibleValues = { "�ϴ�", "����" };
			Object selectedValue = JOptionPane.showInputDialog(null, "��ѡ��", "ѡ����", JOptionPane.INFORMATION_MESSAGE,
					null, possibleValues, possibleValues[0]);
			try {
				if (selectedValue.equals("�ϴ�")) {
//				System.out.println("haikeyilei");

					try {
						new FileClient().sendFile();
						;
					} catch (Exception e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}

				}
				if (selectedValue.equals("����")) {
					new Download();
				}
			} catch (NullPointerException e1) {
				System.out.println("��û��ѡ����");

			}

		} else if (arg0.getSource() == this.p1_lab5) {

			this.cardP3.show(p3, "5");

			new logRecord();

		} else if (arg0.getSource() == this.p1_lab1) {
//			ct = new ChatPage();
		}
		 else if (arg0.getSource() == this.p1_lab6) {
//				groupChat chat = new groupChat(u);
//				ManageChat.addGroupChat("group", chat);
			}


	}

	// �û�ѡ��ĳ������JLabel,�����
	public void mouseEntered(MouseEvent arg0) {

		if (arg0.getSource() == this.p1_lab1) {
			System.out.println("ѡ������");
			this.p1_lab1.setEnabled(true);
		} else if (arg0.getSource() == this.p1_lab2) {

			this.p1_lab2.setEnabled(true);
		} else if (arg0.getSource() == this.p1_lab3) {

			this.p1_lab3.setEnabled(true);
		} else if (arg0.getSource() == this.p1_lab4) {

			this.p1_lab4.setEnabled(true);
		} else if (arg0.getSource() == this.p1_lab5) {

			this.p1_lab5.setEnabled(true);
		} else if (arg0.getSource() == this.p1_lab6) {

			this.p1_lab6.setEnabled(true);
		}
	}

	public void mouseExited(MouseEvent arg0) {
		// �û�ѡ��ĳ������JLabel,�����,����
		if (arg0.getSource() == this.p1_lab1) {
			this.p1_lab1.setEnabled(false);
		} else if (arg0.getSource() == this.p1_lab2) {
			this.p1_lab2.setEnabled(false);
		} else if (arg0.getSource() == this.p1_lab3) {
			this.p1_lab3.setEnabled(false);
		} else if (arg0.getSource() == this.p1_lab4) {
			this.p1_lab4.setEnabled(false);
		} else if (arg0.getSource() == this.p1_lab5) {
			this.p1_lab5.setEnabled(false);
		}else if (arg0.getSource() == this.p1_lab6) {
			this.p1_lab6.setEnabled(false);
		}
	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}

	// ���˳��ļ���
	class quit implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			int t;
			// �Ƿ����㣬�񷵻�1����ŷ���-1
			t = JOptionPane.showConfirmDialog(null, "�����Ҫ�˳���", "           ", JOptionPane.YES_NO_OPTION);

			if (t == 0) {
				System.exit(0);
			}

		}

	}

	// С��Ϸ�Ľ���
	class games implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		
		try {
			
				String[] possibleValues = { "̰����", "������" };
				Object selectedValue = JOptionPane.showInputDialog(null, "��ѡ��", "ѡ����Ϸ", JOptionPane.INFORMATION_MESSAGE,
						null, possibleValues, possibleValues[0]);

				if (selectedValue.equals("������")) {
					new FiveChessGame().start();
					;
				} else if (selectedValue.equals("̰����")) {
					new Gamemanager().start();
				}
		}
		

		//����Ի���
		catch(NullPointerException e2) {
			
			Object[] options = { "��", "ȡ��" }; 
			JOptionPane.showOptionDialog(null, "��û��ѡ����Ϸ������Լ���", "����", 
			JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
			null, options, options[0]); 
			
		}
		}

	}

}
