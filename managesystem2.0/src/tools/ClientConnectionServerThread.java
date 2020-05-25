package tools;

import java.awt.BasicStroke;
//���ܣ��ͻ������ӷ������߳�
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import common.Message;
import common.MessageType;
import page.FriendsListPage;
import page.PaintUI;
import page.groupChat;
import page.privateChat;

public class ClientConnectionServerThread extends Thread {
	private Socket s;

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

	public ClientConnectionServerThread(Socket s) {
		this.s = s;
	}

	public void run() {

		while (true) {
			try {

				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message m = (Message) ois.readObject();

				if (m.getMesType().equals(MessageType.message_x_y)) {
					// ��ȡ����

					// �ѷ�������õ���Ϣ����ʾ������ʾ�Ľ�����
					privateChat chat = ManageChat.getChat(m.getGetter() + "  " + m.getSender());

					PaintUI paintUI = chat.paintUI;
					BasicStroke strock = new BasicStroke(m.getWidth());
					paintUI.g.setStroke(strock);
					paintUI.g.setColor(m.getColor());
					paintUI.g.drawLine(m.getX1(), m.getY1(), m.getX2(), m.getY2());

				} else if (m.getMesType().equals(MessageType.message_comm_mes)) {
					// �ѷ�������õ���Ϣ����ʾ������ʾ�Ľ�����
					privateChat chat = ManageChat.getChat(m.getGetter() + "  " + m.getSender());

					// ������Ϣ��ʾ
					System.out.println("˽��   ���ǿͻ��ˣ��㷢����Ϣ");
					// ��ʾ
					chat.showNews(m);
				} else if (m.getMesType().equals(MessageType.message_comm_picture)) {
					// �ѷ�������õ���Ϣ����ʾ������ʾ�Ľ�����
					privateChat chat = ManageChat.getChat(m.getGetter() + "  " + m.getSender());

					// ������Ϣ��ʾ
					System.out.println("˽��ͼƬ   ���ǿͻ��ˣ��㷢����Ϣ");
					// ��ʾ
					chat.showNews(m);
				} else if (m.getMesType().equals(MessageType.message_ret_onLineFriend)) {

					String con = m.getCon();
					String[] friends = con.split(" ");

					// �ӿͷ��˷��͵�ʱ����sender���ӷ���������ȥ����getter
					String getter = m.getGetter();
					System.out.println("getter" + getter);
					// �޸���Ӧ�ĺ����б�
					FriendsListPage friendsListPage = ManageFriendList.getFriendList(getter);

					System.out.println("�ͷ��ˣ���ȡ����");

					// �������ߺ���

					if (friendsListPage != null) {
						friendsListPage.updateFriend(m);
						System.out.println("�������ߺ���");
					}
				} else if (m.getMesType().equals(MessageType.message_group_mes)) {
					groupChat group = ManageChat.getGroupChat("group");
					// System.out.println(m.getSender() + "21313131");
					if (group != null) {
						// System.out.println(" ClientConnectionServerThread" + m.getSender() + " " +
						// m.getGetter());
						group.showNews(m);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
