package tools;
//����ͷ��������������ͨѶ���߳���

import java.util.HashMap;

public class ManageClientConnectionServerThread {

	public static HashMap hm = new HashMap<String, ClientConnectionServerThread>();

	// �Ѵ����õ� ClientConnectionServerThread����hm�У�
	// ClientConnectionServerThread����ÿ���û���½��socket
	public static void addClientConnectionServerThread(String userName, ClientConnectionServerThread ccst) {
		hm.put(userName, ccst);
	}

	// ͨ��userNameȡ�ø��߳�
	public static ClientConnectionServerThread getClientConnectionServerThread(String userName) {
		return (ClientConnectionServerThread) hm.get(userName);
	}
}
