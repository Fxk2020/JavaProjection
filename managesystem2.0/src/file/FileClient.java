package file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import common.Users;
import log.Log;
import tools.JProcessBarDemo;

public class FileClient extends Socket{

	private static final String SERVER_IP = "127.0.0.1"; // �����IP
	private static final int SERVER_PORT = 8899; // ����˶˿�
	private Socket client;
	private FileInputStream fis;
	private DataOutputStream dos;
	private JProcessBarDemo JPBD = new JProcessBarDemo();//��ʾ�ļ��ϴ��Ľ�����

	JFrame frame = new JFrame();
	JFileChooser fileChooser;//����ϴ��ļ���ʱ�򶼻ᵯ��һ����������ѡ��Ҫ�ϴ����ļ�����Ȼ������������ȴ��֪�����������������Java���������ļ��������ھ���FileChooser

	boolean ok=true;

	//���������йر���
	int wanchengdu = 0;
	int length = 0;
	long progress = 0;
	int haifenxiangma=0;

	public FileClient() throws Exception {

		super(SERVER_IP, SERVER_PORT);
		this.client = this;
		System.out.println("Cliect[port:" + client.getLocalPort() + "] �ɹ����ӷ����");
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
	}   

	public void sendFile() throws Exception {



		//			String s = JOptionPane.showInputDialog( "��������Ҫ������ļ���λ�ã�");//this������contentPaneҲ�У�����ʾ��Ҫ��ʾ����Ϣ��Ŀ����� 
		//			//���showMessaggeDialog�����������ˣ��������·��� //JOptionPane.showMessageDialog
		new Thread() {
			public void run() {
				try {
					while(ok&&haifenxiangma==0){
						File file = new File(Gui());
						if (file.exists()) {
							fis = new FileInputStream(file);
							dos = new DataOutputStream(client.getOutputStream());

							// �ļ����ͳ���
							dos.writeUTF(file.getName());//ʹ�û����޹صķ�ʽʹ��modified UTF-8���뽫�ַ���д������������
							dos.flush();
							dos.writeLong(file.length());
							dos.flush();

							// ��ʼ�����ļ�
							System.out.println("======== ��ʼ�����ļ� ========");

							byte[] bytes = new byte[1024];
							while(wanchengdu<=100) {
								try {
									while ((length = fis.read(bytes, 0, bytes.length)) != -1) {//����-1ʱ�ļ��Ѿ��������
										try {
											dos.write(bytes, 0, length);
											dos.flush();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										progress += length;
										System.out.print("| " + (100 * progress / file.length()) + "% |");
										wanchengdu = (int) (100 * progress / file.length());
										JPBD.setValues(wanchengdu);
										JPBD.setVisible(true);
										if(wanchengdu>=100) {
											JPBD.dispose();
											break;
										}
									}
									if(wanchengdu>=100) {
										System.out.println();
										System.out.println("======== �ļ�����ɹ� ========");

										//									JOptionPane.showMessageDialog(frame, "����ɹ�", "��", JOptionPane.INFORMATION_MESSAGE);
										//�Ƿ����㣬�񷵻�1����ŷ���-1		

										haifenxiangma=JOptionPane.showConfirmDialog(null, "�Ƿ��������", "           ����ɹ�,��", JOptionPane.YES_NO_OPTION); 
										//ÿ�η����������ȫ�ֱ�������
										progress = 0;
										length = 0;
										wanchengdu = 0;
										
										break;
									}
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}

					}}
				catch(NullPointerException e2) {
					System.out.println("ð����");
				}
				catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (fis != null)
							fis.close();
						if (dos != null)
							dos.close();
						client.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}}.start();

	}

	//���ѡ����ļ���·����String��
	public  String Gui() {

		String s=null;
		try { // ʹ��Windows�Ľ�����
			UIManager
			.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//����ѡ��Ŀ¼Ҳ����ѡ���ļ�

		fileChooser.setMultiSelectionEnabled(true);//����ѡ�����ļ�

		fileChooser.showOpenDialog(null);
		File f = fileChooser.getSelectedFile();//������ѡ���ļ�
		if (f != null) {
			//		    System.out.println(f.getPath());
			s= f.getPath();
		}
		return s;
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {                      // �����ڲ��࣬��д��run����
			public void run() {
				try {
					FileClient c = new FileClient(); // �����ͻ�������
					c.sendFile(); // �����ļ�
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


	}
}
