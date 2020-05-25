/*
 *���ǳ���ķ����������ټ������ȴ�ĳ���ͷ��ˣ�������
 */
package Server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import common.Message;
import common.User;
import Dao.AddUser;
import Dao.FriendsList;
import Dao.LoginDao;

//���������������ȴ�ĳ���ͻ������ӷ�����
public class Server {
	ObjectInputStream ois;
	ObjectOutputStream oos;

	public Server() throws Exception {

		try {
			// ��ĳ���˿ڼ���
			System.out.println("���ڼ���,������������ ");
			ServerSocket ss = new ServerSocket(9999);
			// �������ȴ�����
			while (true) {
				// ������ֱ���û�����
				// �����þֲ�������ԭ������ΪҪΪÿһ������������ӵĿͻ��˽���һ��������Socket
				Socket s = ss.accept();

				// ���տͻ��˷������˺���Ϣ
				ois = new ObjectInputStream(s.getInputStream());

				User u = (User) ois.readObject();
				Message m = new Message();
				oos = new ObjectOutputStream(s.getOutputStream());

				// System.out.println("���������յ��û�id 0�Ų�����ifǰ��" + u.getFriendsList());
				if (u.getDoing().equals("95")) {
					LoginDao login = new LoginDao();
					if (login.checkUser(u)) {
						// ����һ���ɹ���½����Ϣ
						m.setMesType("91");
						FriendsList friends = new FriendsList();
						String lists = friends.getFriends(u);

						m.setLists(lists);
						oos.writeObject(m);

						// System.out.println("�ڷ�������ʾ�����б�" + lists);
						// System.out.println("u.getFriendsList()" + u.getFriendsList());
						// System.out.println("���������յ��û�id 2�Ų�����equals����" + u.getFriendsList());

						// ����һ���̣߳��ø��߳���ÿͷ��˱���ͨѶ
						SeverConnectionClientThread scct = new SeverConnectionClientThread(s);
						// ����ͨѶ�̼߳���
						ManageClientThread.addClientThread(u.getName(), scct);
						// ������ÿͷ���ͨ�ŵ��߳�
						scct.start();

						// ֪ͨ���������û�
						scct.notifyOther(u.getName());
					} else {
						m.setMesType("92");
						oos.writeObject(m);
						System.out.println("��½ʧ����");
						s.close();
					}
				} else if (u.getDoing().equals("96")) {
					AddUser add = new AddUser();
					if (add.addString(u)) {
						m.setMesType("93");
						oos.writeObject(m);
						System.out.println("ע��ɹ�3");
					} else {
						m.setMesType("94");
						oos.writeObject(m);
						System.out.println("ע�᲻�ɹ�4");
						s.close();
					}
				}

				// System.out.println("���������յ��û�id 3�Ų�����������" + u.getFriendsList());

			}
		}
		
		catch (BindException e) {
			System.out.println("�Ѿ�����");
			System.exit(0);
			// �رյڶ���������
		} 
		catch (SocketException e) {
			// TODO: handle exception
			System.out.println("û��");
			
		}
		
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
