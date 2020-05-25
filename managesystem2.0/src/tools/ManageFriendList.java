package tools;

import java.util.HashMap;

import page.FriendsListPage;

public class ManageFriendList {
	private static HashMap hm = new HashMap<String, FriendsListPage>();

	// ����
	public static void addFriendList(String name, FriendsListPage friendsListPage) {
		hm.put(name, friendsListPage);
	}

	// ȡ��
	public static FriendsListPage getFriendList(String name) {
		return (FriendsListPage) hm.get(name);
	}

}
