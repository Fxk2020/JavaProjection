package page;

/*
 * ���������ý���

 * �ͷ���һֱ���ڶ�ȡ��״̬�����ǰ���Ҳ����һ���߳�
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import common.Message;
import common.MessageType;
import common.User;
import tools.ManageClientConnectionServerThread;

public class privateChat extends JFrame implements ActionListener, MouseListener {

	public static void main(String args[]) {

	}

	private JTextPane showMessageView;
	private JTextPane sendMessgaeView;
//	private JTextArea showMessage;
//	private JTextField sendMessgae;
	// Ϊ ����ʾ���ֽ���Ϣ��ͷ��Ϳ򶼸�ΪJTextPane��
	String ownName, friendName;
	JSplitPane splitPane;
	JButton close, send, handWrite, picture, font, emoji;
	JPanel function_JPanel, bottom_JPanel;
	ObjectOutputStream oos;
	JPanel panel_2;
	public PaintUI paintUI;
	// ����ͼƬ
	StyledDocument doc = null;
	JFileChooser fileChooser;
	SimpleAttributeSet attr;

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
		handWrite.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				paintUI = new PaintUI(ownName, friendName);
			}
		});
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
		picture.addActionListener(this);
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);

		showMessageView = new JTextPane();
		scrollPane_1.setViewportView(showMessageView);// ��ʾ��Ϣ��¼��,�ڴ��ڵ��ϱ�λ��

		sendMessgaeView = new JTextPane();
		showMessageView.setEditable(false);
		attr = new SimpleAttributeSet();
		StyleConstants.setFontSize(attr, 19);
		doc = showMessageView.getStyledDocument();
		scrollPane.setViewportView(sendMessgaeView); // ����ʾ������,�ڴ��ڵ��±�λ��
		// sendMessgae.setHorizontalAlignment(JTextPane.LEFT);

		this.setVisible(true);

	}

	public privateChat(User u, String name) {
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
		friendName = name;
		setTitle(name);
		this.initFrame();

	}

	public void actionPerformed(ActionEvent arg0) {

		Message m = new Message();
		m.setSender(this.ownName);
		m.setGetter(this.friendName);

		m.setSendTime(Calendar.getInstance().getTime().toLocaleString());
		if (arg0.getSource() == send) {
			m.setCon(this.sendMessgaeView.getText());

			m.setMesType(MessageType.message_comm_mes);
			// String ownMessage = this.ownName + " " + m.getSendTime() + '\n' +
			// this.sendMessgaeView.getText() + '\n';
			// showMessage.append(ownMessage);
			// JTextPaneû��append����
			// Ϊ��׷�����֣�������ԭ�������ݣ�
			// textPane.setText(textPane.getText()+"Ҫ׷�ӵ��ı�");

			// showMessageView.setText(showMessageView.getText() + ownMessage);
			// showMessageView.setFont(MyTools.f4);
			// sendMessgaeView.setText("");

			// ���͸�����������Ҫ�õ�socket
			try {
				ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectionServerThread
						.getClientConnectionServerThread(this.ownName).getS().getOutputStream());

				oos.writeObject(m);
			} catch (IOException e) {
				System.out.println("����socket");
				e.printStackTrace();
			}
			try {
				doc.insertString(doc.getLength(),
						this.ownName + "     " + m.getSendTime() + '\n' + this.sendMessgaeView.getText() + '\n', attr);
			} catch (BadLocationException e1) {

				e1.printStackTrace();
			}
		} else if (arg0.getSource() == picture) {
			fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			fileChooser.setMultiSelectionEnabled(true);

			fileChooser.showOpenDialog(null);
			File f = fileChooser.getSelectedFile();

			if (f != null) {
				System.out.println(f.getPath());
			}

			m.setFile(f);
			m.setMesType(MessageType.message_comm_picture);

			try {
				ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectionServerThread
						.getClientConnectionServerThread(this.ownName).getS().getOutputStream());

				oos.writeObject(m);
			} catch (IOException e) {

				e.printStackTrace();
			}
			try {
				doc.insertString(doc.getLength(), '\n' + this.ownName + "     " + m.getSendTime() + '\n' + '\n', attr);
			} catch (BadLocationException e1) {

				e1.printStackTrace();
			}
			showMessageView.setCaretPosition(doc.getLength());
			showMessageView.insertIcon(new ImageIcon(f.getPath()));

		}

		sendMessgaeView.setText(" ");

	}

	public void showNews(Message m) {
		// ��ʾ���������
		if (m.getMesType().equals(MessageType.message_comm_mes)) {
			// String info = m.getSender() + " " + m.getSendTime() + '\n' + " " + m.getCon()
			// + "\r\n";
			// showMessageView.setText(showMessageView.getText() + info);
			// System.out.println(info);
			String info = m.getSender() + "     " + m.getSendTime() + '\n' + " " + m.getCon() + "\r\n";

			try {
				doc.insertString(doc.getLength(), info, attr);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (m.getMesType().equals(MessageType.message_comm_picture)) {
			this.showMessageView.setCaretPosition(doc.getLength());
			String info = '\n' + m.getSender() + "     " + m.getSendTime() + "\r\n";
			try {
				doc.insertString(doc.getLength(), info + "\r\n", attr);

			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.showMessageView.insertIcon(new ImageIcon(m.getFile().getPath()));

		}
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

	public void mouseClicked(MouseEvent arg0) {
		System.out.print("wacac");
		if (arg0.getClickCount() == 1) {

		}
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

//	public void run() {
//
//		// ��ȡ
//		try {
//			while (true) {
//				ObjectInputStream ois = new ObjectInputStream(clientConectionServer.s.getInputStream());
//				Message m = (Message) ois.readObject();
//
//				// ��ʾ���������
//				String info = m.getSender() + "   " + m.getSendTime() + '\n' + m.getCon() + "\r\n";
//
//				this.showMessage.append(info);
//
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

}
