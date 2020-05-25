package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;

import common.Message;

public class ConServerThread extends Thread {

	private Socket s;

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

	public ConServerThread(Socket s) {
		this.s = s;
	}

	public void run() {
		while (true) {
			try {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message m = (Message) ois.readObject();

			} catch (SocketException e) {
				System.out.println("");
			} catch (StreamCorruptedException e) {

				System.out.println("�쳣�˳�");
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��

				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}

		}
	}
}
