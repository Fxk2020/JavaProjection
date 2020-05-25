package page;

/*
 * ���������ý���
 * �ͷ���һֱ���ڶ�ȡ��״̬�����ǰ���Ҳ����һ���߳�
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import common.Message;
import common.MessageType;
import common.User;
import tools.ChatTextPanel;
import tools.ManageClientConnectionServerThread;

public class groupChat extends JFrame implements ActionListener {

	public static void main(String args[]) {

	}

	User user;
	private ChatTextPanel showMessageView;
	private ChatTextPanel sendMessgaeView;
	String ownName, friendName;
	JSplitPane splitPane;
	JButton close, send, handWrite, picture, font, emoji;
	JPanel function_JPanel, bottom_JPanel;
	ObjectOutputStream oos;
	JPanel panel_2;

	public void initFrame() {

		setBounds(100, 100, 558, 576);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		splitPane = new JSplitPane();
		splitPane.setDividerLocation(350);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		splitPane.setRightComponent(panel_2);

		// ���ܿ� ����
		function_JPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		function_JPanel.setLayout(flowLayout);
		panel_2.add(function_JPanel, BorderLayout.NORTH);

		handWrite = new JButton(new ImageIcon("imag/handwrite.png"));
		picture = new JButton(new ImageIcon("imag/picture.png"));
		emoji = new JButton(new ImageIcon("imag/emoji.png"));
		font = new JButton(new ImageIcon("imag/font.png"));

		function_JPanel.add(picture);
		function_JPanel.add(handWrite);
		function_JPanel.add(font);
		function_JPanel.add(emoji);

		// �ײ����رպͷ��͡�south�ϲ�
		bottom_JPanel = new JPanel();
		FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		bottom_JPanel.setLayout(flowLayout_1);
		panel_2.add(bottom_JPanel, BorderLayout.SOUTH);

		close = new JButton();
		close.setText("�ر�");
		send = new JButton();
		send.setText("����");

		bottom_JPanel.add(close);
		bottom_JPanel.add(send);
		send.addActionListener(this);
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);

		showMessageView = new ChatTextPanel();
		scrollPane_1.setViewportView(showMessageView);// ��ʾ��Ϣ��¼��,�ڴ��ڵ��ϱ�λ��

		sendMessgaeView = new ChatTextPanel();
		scrollPane.setViewportView(sendMessgaeView); // ����ʾ������,�ڴ��ڵ��±�λ��
		// sendMessgae.setHorizontalAlignment(JTextPane.LEFT);
		this.setVisible(true);

	}

	public groupChat(User u) {
		user = u;
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

		ownName = u.getName();

		setTitle("�� ǰ �� �� ��" + ownName);
		this.initFrame();
		String[] test = user.getList();

	}

	public void actionPerformed(ActionEvent arg0) {

		Message m = new Message();
		m.setSender(this.ownName);

		m.setGrounpChat(this.sendMessgaeView.getText());
		m.setMesType(MessageType.message_group_mes);
		m.setSendTime(Calendar.getInstance().getTime().toLocaleString());

		if (arg0.getSource() == send) {
			sendMessgaeView.setText("");
			// ���͸�����������Ҫ�õ�socket
			try {
				ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectionServerThread
						.getClientConnectionServerThread(this.ownName).getS().getOutputStream());

				oos.writeObject(m);
				System.out.println("��groupchat��socket");
			} catch (IOException e) {
				System.out.println("group�����ˣ�");
				e.printStackTrace();
			}
		}
	}

	public void showNews(Message m) {
		// ��ʾ���������
		// System.out.println("Ⱥ������Ϣ��ʾ");
		String info = m.getSender() + "   " + m.getSendTime() + '\n' + m.getGrounpChat() + "\r\n";
		showMessageView.setText(showMessageView.getText() + info);
		System.out.println(info);
	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent arg0) {

	}

	public void windowClosing(WindowEvent arg0) {

	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
}
