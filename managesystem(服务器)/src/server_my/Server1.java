package server_my;

import javax.swing.*;

import log.Log;
import model.FileServer;
import model.MySerever;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Server1 extends JFrame{

    final int W=500;
    final int H=750;
    JButton button1=new JButton("����������");
    
	public Server1() {
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			// �ı���
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setLayout(null);//setBounds��������ʹ����SETLAYOUTΪ��
		 
		this.setSize(W, H);
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		button1.setBounds(200,600,100,50);
		this.add(button1);
		 
		addListener();
	    this.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {                      // �����ڲ��࣬��д��run����
			public void run() {
				new Server1();
			}
		});

	}
	private void addListener() {
		B1Listener b1=new B1Listener();
		
		
		
		button1.addActionListener(b1);
	}
	class B1Listener  implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {                      // �����ڲ��࣬��д��run����
				public void run() {
					new MySerever();
					//Myserver()����acept��������������
//					try {
//						new FileServer().load();
//					} catch (Exception e) {
//						// TODO �Զ����ɵ� catch ��
//						e.printStackTrace();
//					}
				}
			});
			
		}
		
	}

}
