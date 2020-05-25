package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import common.Message;
import common.User;
import tools.ClientConnectionServerThread;
import tools.ManageClientConnectionServerThread;

public class clientConectionServer {
	String s1 = "127.0.0.1";
	int port = 9999;
	public Socket s;

	// ��������
	public String[] sendLoginInToServer(Object o) {
		String login = "false";
		String register = "false";
		String result = null;

		String request[] = { login, result, register };
		try {
			s = new Socket(s1, port);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);// ������󣬴����������

			// ��ָ����inputstream�ж��ض�����Ϣ
			Message message = null;
			while (message == null) {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				message = (Message) ois.readObject();
			}
			// ��������֤�û���½
			if (message.getMesType().equals("91")) {
				// 1:�����½�ɹ�
				// ����һ�����������������ͨѶ���ӵ��߳�
				//	ÿ��½һ���˾ʹ���һ���߳�
				ClientConnectionServerThread ccst = new ClientConnectionServerThread(s);
				// ������ͨѶ�߳�
				ccst.start();
				
				//��ÿ���˴������̷߳��ڹ����hm��
				ManageClientConnectionServerThread.addClientConnectionServerThread(((User) o).getName(), ccst);
				login = "true";
				request[0] = "true";//request��һ����������

				// ��½�ɹ���Ҳ���غ����б�
				result = message.getLists();
				request[1] = result;
			} else if (message.getMesType().equals("92")) {
				// 2:�����½ʧ��
				login = "false";
				request[0] = "false";
				System.out.println("ʧ��");
			} else if (message.getMesType().equals("93")) {
				// ע��ɹ�
				register = "true";
				request[2] = "true";
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {

		}

		return request;

	}
}
