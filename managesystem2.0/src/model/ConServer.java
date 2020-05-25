package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import common.Message;
import common.MessageType;
import common.Users;
import log.Register;
import mainpage.MainPage;
import mainpage.MainPage2;
import ����.Informtion;


public class ConServer {
	
	public Socket s;

    	public boolean sendInformationLogin(Object o) {
    		boolean b = false;
    		try {
    			s = new Socket("127.0.0.1", 8888);//������������
    			
    			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
    			oos.writeObject(o);
    			oos.flush();
    			
    			Message m = null;
    			while(m == null)
    			{
    				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
    			    m = (Message)ois.readObject();
    			    
    			}
    			
    			//��ͨ�û���½
    			if(m.getMessType().equals(MessageType.message_Login_succed_B)) 
    			{
    				ConServerThread cst = new ConServerThread(s);
    				cst.start();
    				
    				new MainPage(m.getStu());
    				new Informtion();
    				b=true;
    			}
                //����Ա��½
    			else if(m.getMessType().equals(MessageType.message_login_succed_A)) {
    				
    				ConServerThread cst = new ConServerThread(s);
    				cst.start();
    				
    				new MainPage2(m.getStu());
    				new Informtion();
    				b=true;
    			}
    			
    			//ע��ɹ�
    			else if(m.getMessType().equals(MessageType.message_resiger_succed)) {
    				JOptionPane.showMessageDialog(null, "����ע��ɹ������˳�ע�����");

    			}
    			//ע��ʧ��
    			else if(m.getMessType().equals(MessageType.message_resiger_fail)) {
    				JOptionPane.showMessageDialog(null, "��������д���");
    			}
    			else if(m.getMessType().equals(MessageType.message_information_succeed)) {
    			   String information=m.getInformation();
    			}
    			else if(m.getMessType().equals(MessageType.message_login_fail)) {
    				ConServerThread cst = new ConServerThread(s);
    				cst.start();
    				
    				JOptionPane.showMessageDialog(null, "����˺Ż������д�������������");
    			}
    			
    		} catch (UnknownHostException e) {

    			e.printStackTrace();
    		} catch (IOException e) {

    			e.printStackTrace();
    		} catch (ClassNotFoundException e) {

    			e.printStackTrace();
    		}
    		return b;
    	}
	}


