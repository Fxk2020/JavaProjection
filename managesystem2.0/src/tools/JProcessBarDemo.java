package tools;
import java.awt.Color;
import java.awt.FlowLayout;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
 
 
 
public class JProcessBarDemo extends JFrame{
 
	private static final long serialVersionUID = 1L;
	private JProgressBar processBar;
	
	public JProcessBarDemo(){
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
		setTitle("�ļ��������");		//���ô������
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���ô����˳��Ĳ���
		
		setBounds(600, 600, 350, 100);// ���ô����λ�úʹ�С
		
		setResizable(false);
		
		JPanel contentPane = new JPanel();   // �����������
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));// �����������߿�
		
		setContentPane(contentPane);// Ӧ��(ʹ��)�������
		
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));// ����Ϊ��ʽ����
		
		processBar = new JProgressBar();// ����������
		
		processBar.setStringPainted(true);// ���ý������ϵ��ַ�����ʾ��false������ʾ
		
		processBar.setBackground(Color.GREEN);
	
		
		
		contentPane.add(processBar);// �������ӽ��ȿؼ�
	}
	
	public void setValues(int i) {
//		// �����߳���ʾ����
//		new Thread(){
//
//			public void run(){
//				while(i!=100) {
//					try {
//						Thread.sleep(100);  //   �õ�ǰ�߳�����0.1ms
//					} catch (InterruptedException e) {
//						// TODO: handle exception
//						e.printStackTrace();
//					}
//					processBar.setValue(i);	// ���ý�������ֵ
//				}
//				processBar.setString("�ļ��������");// ������ʾ��Ϣ
//			}
//		}.start(); //  �����������߳�
		
		processBar.setValue(i);
		
	}
	
	public static void main(String[] args){
		JProcessBarDemo JPBD = new JProcessBarDemo();
		new Thread() {
			public void run() {
				for(int i=1;i<=100;i++) {
					JPBD.setValues(i);
					JPBD.setVisible(true);
					if(i==100) {
						JPBD.dispose();
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}.start();
		
		
//		JPBD.setVisible(true);
	}
}
