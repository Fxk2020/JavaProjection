package connection;

//���û��Ĳ���
import common.User;

public class ClientUser {
	public String[] checkUser(User u) {
		String[] loginFriend = new clientConectionServer().sendLoginInToServer(u);
		return loginFriend;

	}

}