package file;

import javax.swing.*;

import common.GMKB;
import common.Message;
import common.MessageType;
import common.Users;
import log.Log;
import log.Register;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.net.*;
import java.text.DecimalFormat;
import java.util.LinkedList;

public class Download extends JFrame implements ActionListener {

	Socket s;

	final int W = 400, H = 600;
	JCheckBox chooseFile;// ��ѡ��JCheckBox
	LinkedList< JCheckBox > check;
	LinkedList<String> fileList2;
	GridLayout gridLayout;
	JPanel fileListPanel;
	JButton b1;
	int t;

	// �ļ��Ľ���
	 FileOutputStream fos;
	 FileInputStream  fis;
	

	JFileChooser fileChooser;

	public Download() {
		
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
		
		check = new LinkedList<>();

		this.setTitle("�ļ�������");
		this.setSize(W, H);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fileListPanel = new JPanel(new GridLayout(30, 1));
		this.add(fileListPanel);

		b1 = new JButton("����");
		b1.setBounds(0, 0, 5, 5);
		b1.setBackground(new Color(30, 144, 255));
		b1.setForeground(Color.white);
		fileListPanel.add(b1);
		b1.addActionListener(this);

		try {
			s = new Socket("127.0.0.1", 8888);

			Users u = new Users();
			u.setDosomething(MessageType.message_filelist);

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(u);
			oos.flush();

			Message m = null;
			while (m == null) {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				m = (Message) ois.readObject();

			}
			if (m.getInformation().equals("�ļ��б�")) {

				update(m);

			}
		} catch (UnknownHostException e1) {

			e1.printStackTrace();
		} catch (IOException e2) {

			e2.printStackTrace();
		} catch (ClassNotFoundException e3) {

			e3.printStackTrace();
		}
	}

	public void update(Message m) {// ����ļ��������

		LinkedList<String> fileList = m.getFileList();// ����ļ���

		System.out.println(fileList.size());

		fileList2 = new LinkedList<>();
		for (int i = 0; i < fileList.size(); i++) {

			chooseFile = new JCheckBox(fileList.get(i));// ������ѡ��

			check.add(chooseFile);//��ÿ����ѡ����ӵ�������

			fileListPanel.add(chooseFile);

		}
	}

	// ѡ�����ص��ļ�

	public void actionPerformed(ActionEvent e) {

		try {

			Users u2 = new Users();

			//�����Ҫ���ص��ļ��б�
			filelist_add();
			
			System.out.println(fileList2.size());
			System.out.println(fileList2);

			u2.setDosomething(MessageType.message_download);
			u2.setFileList(fileList2);

			ObjectOutputStream oos;
			oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(u2);
			oos.flush();

			ObjectInputStream ois = null;
			Message m = null;
			while (m == null) {
				ois = new ObjectInputStream(s.getInputStream());
				m = (Message) ois.readObject();

			}
			if (m.getMessType().equals("��ʼ����")) {

//				File directory = new File("F:\\������\\�����ļ��Ŀռ�");
				File directory = new File(Gui());// ѡ�����صĵ�ַ

				if (!directory.exists()) {
					directory.mkdir();
				}

				LinkedList<File> Filess = m.getFiles();
			   
			    System.out.println("OK");
			    byte[] bytes;
				for (int i = 0; i < Filess.size(); i++) {

					// ��ʼ�����ļ�
					System.out.println(GMKB.getFormatFileSize(Filess.get(i).length()));
					
					File a = Filess.get(i);
					File b = new File(directory.getAbsolutePath()+"//"+a.getName());
					
					FileCopy.copy(a, b);


					System.out.println("======== �ļ����سɹ� [File Name��" +a.getName() +"] [Size��" +GMKB.getFormatFileSize(a.length()) +"] ========");

				}
//�Ƿ����㣬�񷵻�1����ŷ���-1					
				t = JOptionPane.showConfirmDialog(null, "�Ƿ��������", "           ���سɹ�,��", JOptionPane.YES_NO_OPTION);

				if (t != 0) {
					this.setVisible(false);
				}

			}

		} catch (IOException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}

	}

	//���ѡ�еĸ�ѡ������ݡ�
	private void filelist_add() {
		
		for(int i=0;i<check.size();i++) {
			if(check.get(i).isSelected()) {
				fileList2.add(check.get(i).getText());
			}
		}
		
	}

	// Windows�����ļ�ѡ��
	public String Gui() {

		String s = null;
		try { // ʹ��Windows�Ľ�����
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//ֻ����ѡ��Ŀ¼�����ļ�

		fileChooser.setMultiSelectionEnabled(true);//����ѡ�����ļ�

		fileChooser.showOpenDialog(null);//��ʾһ���ļ�ѡ����
		File f = fileChooser.getSelectedFile();//������ѡ����ļ�
		if (f != null) {
//		    System.out.println(f.getPath());
			s = f.getPath();
		}
		return s;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { // �����ڲ��࣬��д��run����
			public void run() {
				new Download();
			}
		});

	}

}
