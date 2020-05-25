package mainpage;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import tools.MyTools;
import vote.Vote;
import ����.Compile;

public class MainPage2 extends MainPage {
	
	JMenu bianji = new JMenu("�༭����");
	JMenuItem compile = new JMenuItem("����༭",new ImageIcon("imag/compile.jpg"));
	
	JMenu vote = new JMenu("����ͶƱ");
	JMenuItem faqi = new JMenuItem("����ͶƱ",new ImageIcon("imag/����.jpg"));
	
	common.students stu1;
	
	public MainPage2(common.students s) {
		
		super(s);
		
		stu1 = s;
		
		//����Ա�����⹦��
		bianji.setFont(MyTools.f1);
		vote.setFont(MyTools.f1);
		jmBar.add(bianji);
		jmBar.add(vote);
		
		compile.setFont(MyTools.f2);
		bianji.add(compile);
		faqi.setFont(MyTools.f2);
		vote.add(faqi);
		
		//�༭����
		comlistener c1 = new comlistener();
		compile.addActionListener(c1);
		
		Listenervote v1 = new Listenervote();
		faqi.addActionListener(v1);
		
	}
	
	
	
	
	public static void main(String[] args) {
		MainPage2 m2 = new MainPage2(null);
	}
	
	//�༭����ļ�����
	class comlistener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			new Compile(stu1.getName());
			
		}
	}
	
	//����ͶƱ�ļ�����
	class Listenervote implements ActionListener{

		
		public void actionPerformed(ActionEvent arg0) {
			
			new Vote(stu1.getName());
			
		}
		
	}

}
