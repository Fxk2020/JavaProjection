package model;

import java.net.ServerSocket;
import java.net.Socket;


public class Fileserverdown {
	ServerSocket ss = null;
	
	public Fileserverdown(){
	try {
		ss = new ServerSocket(9988);// ��ʼ����
		System.out.println("�����׽��ֳɹ���");

		while (true)
		{

			Socket s = ss.accept();// һֱ�ȴ��ͻ��˵�����
			new CheckSocket(s).start();
		}
	
       }catch(Exception e) {
    	   e.printStackTrace();
       }
	
}
	class CheckSocket extends Thread
	{
		Socket s = null;
    	public CheckSocket(Socket socket)
    	{
    		this.s = socket;
    	}
    	public void run() {	
    		
    	}
	}
}