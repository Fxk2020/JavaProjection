
// ���ܣ���������ĳ���ͻ��˵�ͨ���߳�

package Server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;

import common.Message;
import common.MessageType;

public class SeverConnectionClientThread extends Thread {
	Socket s;

	public SeverConnectionClientThread(Socket s) {
		this.s = s;
		// �ѷ������͸ÿͻ��˵����Ӹ���s �����߳���socket
	}

	// �ø��߳�ȥ֪ͨ�����û�
	public void notifyOther(String iam) {
		// �õ��������ߵ��˵��߳�
		HashMap hm = ManageClientThread.hm;
		Iterator it = hm.keySet().iterator();

		while (it.hasNext()) {
			Message m = new Message();
			m.setCon(iam);
			m.setMesType(MessageType.message_ret_onLineFriend);
			// ȡ�������û���id
			String onLineUserId = it.next().toString();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(
						ManageClientThread.getClientThread(onLineUserId).s.getOutputStream());
				m.setGetter(onLineUserId);
				oos.writeObject(m);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// @Override
	public void run() {
		while (true) {
			try {

				// ���߳̽��տͻ��˵���Ϣ
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message m = (Message) ois.readObject();

				// �Դӿͷ���ȡ�õ���Ϣ�����жϣ�������Ӧ���ж�
				if (m.getMesType().equals(MessageType.message_x_y)) {
					SeverConnectionClientThread sc = ManageClientThread.getClientThread(m.getGetter());
					ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
					oos.writeObject(m);// ����ȥ

				} else if (m.getMesType().equals(MessageType.message_comm_mes)) {
					// ת�� ������ת������Ӧ�Ŀͷ���
					// ȡ�ý����˵�ͨѶ�߳�
					System.out.println(m.getSender() + "Ҫ��������");// ��ӡ����

					SeverConnectionClientThread sc = ManageClientThread.getClientThread(m.getGetter());

					ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
					// scָҪ�������������������
					oos.writeObject(m);// ����ȥ

				} else if (m.getMesType().equals(MessageType.message_get_onLineFriend)) {
					// ���ڷ������ĺ��Ѹ��ÿͻ��˷���
					// System.out.println(m.getSender() + "Ҫ���ĺ���");
					String result = ManageClientThread.getAllOnLineUserName();
					Message m2 = new Message();
					m2.setMesType(MessageType.message_ret_onLineFriend);
					m2.setCon(result);
					m2.setGetter(m.getSender());
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m2);

				} else if (m.getMesType().equals(MessageType.message_group_mes)) {
					// System.out.println("��SeverConnectionClientThread Ҫʵ��Ⱥ��,������Ⱥ��");
					m.setSender(m.getSender());
					// System.out.println("��ϣ��" + ManageClientThread.hm.size());

					HashMap hm = ManageClientThread.hm;
					Iterator it = hm.keySet().iterator();
					while (it.hasNext()) {
						String userName = it.next().toString();
						SeverConnectionClientThread sc = ManageClientThread.getClientThread(userName);
						ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
						// System.out.println("�����ˣ�" + m.getGrounpChat());
						oos.writeObject(m);
					}

				} else if (m.getMesType().equals(MessageType.message_comm_picture)) {
					System.out.println("ͼƬ" + m.getFile().getName());
					SeverConnectionClientThread cct = ManageClientThread.getClientThread(m.getGetter());
					ObjectOutputStream oos = new ObjectOutputStream(cct.s.getOutputStream());
					oos.writeObject(m);
				}
			}
			catch (SocketException e) {
				System.out.println("OK");
				break;
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {

			}

		}
	}
}
