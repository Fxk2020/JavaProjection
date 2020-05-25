package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import common.Message;
import common.User;
import tools.ManageChat;

public class FriendsListPage extends JFrame implements MouseListener {
	// ��һ�ſ�Ƭ
	JPanel page_Jpanel, list_Jpanel, function_Jpanel;
	JButton private_chat, group_chat, function_button;
	JScrollPane list_scroll;
	JLabel[] list;
	User u;
	String[] name;

	public FriendsListPage(User user) {
		u = new User();
		u = user;
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
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
		// �����һ�ſ�Ƭ
		page_Jpanel = new JPanel(new BorderLayout());
		// �������
		String friends = u.getFriendsList();
		name = friends.split("`");
		int listLength = name.length;
		list_Jpanel = new JPanel(new GridLayout(listLength, 1, 5, 5));
		// ��ʼ������

		list = new JLabel[name.length];
		for (int i = 0; i < list.length; i++) {
			list[i] = new JLabel(name[i], new ImageIcon("imag/HeadImage.png"), JLabel.LEFT);
			list[i].setEnabled(false);
			list[i].addMouseListener(this);
			list_Jpanel.add(list[i]);
		}

		function_Jpanel = new JPanel(new GridLayout(2, 1));

		private_chat = new JButton("�ҵĺ���");

		list_scroll = new JScrollPane(list_Jpanel);
		page_Jpanel.add(private_chat, BorderLayout.NORTH);
		page_Jpanel.add(list_scroll, BorderLayout.CENTER);
		// page_Jpanel.add(function_Jpanel, BorderLayout.SOUTH);
		this.add(page_Jpanel, BorderLayout.CENTER);
		this.setVisible(true);
		this.setSize(300, 600);

		this.setTitle(u.getName());

	}

	public void updateFriend(Message m) {
		String onLineFriend[] = m.getCon().split(" ");
		System.out.println(list.length + "  " + m.getCon());
		u.setList(onLineFriend);
		for (int i = 0; i < onLineFriend.length; i++) {
			String temp = onLineFriend[i];
			System.out.println(temp);
			for (int j = 0; j < name.length; j++) {
				if (name[j].equals(temp)) {
					int index = j;
					list[index].setEnabled(true);
				}
			}
		}

	}

	public void mouseClicked(MouseEvent e) {
		// ��Ӧ�û�˫��
		if (e.getClickCount() == 2) {
			String friendName = ((JLabel) e.getSource()).getText();
			// ���������ʱ������Ҫ����
			privateChat chat = new privateChat(u, friendName);
			// ��Ҫ����һ������(��Ϊʵ��runnable�ӿ�)

			// �����������뵽������
			ManageChat.addChat(this.u.getName() + "  " + friendName, chat);

		}
	}

	public void mouseEntered(MouseEvent e) {
		JLabel jl = (JLabel) e.getSource();
		jl.setForeground(Color.red);

	}

	public void mouseExited(MouseEvent e) {
		JLabel jl = (JLabel) e.getSource();
		jl.setForeground(Color.black);
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
