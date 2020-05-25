package Server.model;

import java.util.HashMap;
import java.util.Iterator;

public class ManageClientThread {
	public static HashMap hm = new HashMap<String, SeverConnectionClientThread>();

	// HashMap<K, V>
	// ��map�����һ���ͻ���ͨѶ�߳�
	public static void addClientThread(String userName, SeverConnectionClientThread ct) {
		hm.put(userName, ct);
	}

	public static SeverConnectionClientThread getClientThread(String userName) {
		return (SeverConnectionClientThread) hm.get(userName);
	}

	// ���ص�ǰ���ߵ��˵ĺ������
	public static String getAllOnLineUserName() {
		// ʹ�õ��������
		Iterator it = hm.keySet().iterator();
		String res = "";
		while (it.hasNext()) {
			res += it.next().toString() + " ";
		}
		return res;
	}
}
