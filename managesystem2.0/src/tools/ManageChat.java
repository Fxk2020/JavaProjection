package tools;
//�����û�����������

import java.util.HashMap;

import page.groupChat;
import page.privateChat;

public class ManageChat {

	private static HashMap hm = new HashMap<String, privateChat>();

	// ���� ��ʵ��һ������
	public static void addChat(String ownNameFriendName, privateChat chat) {
		hm.put(ownNameFriendName, chat);
	}

	// ����������
	public static privateChat getChat(String ownNameFriendName) {
		return (privateChat) hm.get(ownNameFriendName);
	}

	public static void addGroupChat(String ownName, groupChat group) {
		hm.put(ownName, group);
	}

	// ȡ��
	public static groupChat getGroupChat(String ownName) {
		return (groupChat) hm.get(ownName);
	}
}
