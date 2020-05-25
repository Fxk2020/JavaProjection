package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.RoundingMode;
import java.net.PasswordAuthentication;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import DB.DBConnection;
import DB.IDUS;
import common.GMKB;
import common.Message;
import common.MessageType;
import common.SetVote;
import common.Users;
import common.students;
import ����.Informtion;

public class MySerever {

	ServerSocket ss = null;

	// �ļ�����Ĺ���

	public MySerever() {

		try {
			ss = new ServerSocket(8888);// ��ʼ����
			System.out.println("�����׽��ֳɹ���");

			while (true) {

				Socket s = ss.accept();// һֱ�ȴ��ͻ��˵�����
				new CheckSocket(s).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ù���ռ���������ļ������֣�����������LinkedList��
	private LinkedList<String> filelist() {
		LinkedList<String> filelist = new LinkedList<String>();
		String path = "F:/������/�����ļ��Ŀռ�";

		File dir = new File(path);
		File[] files = dir.listFiles();
		for (File file : files) {
			filelist.add(file.getName());
		}

		return filelist;

	}

	// ���ͶƱ����
	private SetVote getVote() {
		SetVote v = new SetVote();
		String name = null;
		String item = null;
		String option1 = null;
		String option2 = null;
		String option3 = null;
		String option4 = null;
		int number1 = 0;
		int number2 = 0;
		int number3 = 0;
		int number4 = 0;

		// ��ý���
		String sug = null;
		int i = 0;

		ResultSet rs;
		ResultSet rs2;

		try {
			rs = IDUS.executeQuery("select * from vote");
			rs2 = IDUS.executeQuery("select * from suggesstion");

			while (rs.next()) { // ���timestamp���Ժ����ɵĽ������ͶƱ

				name = rs.getString("name").trim();
				item = rs.getString("item");

				option1 = rs.getString("option1");
				option2 = rs.getString("option2");
				option3 = rs.getString("option3");
				option4 = rs.getString("option4");

				number1 = rs.getInt("number1");
				number2 = rs.getInt("number2");
				number3 = rs.getInt("number3");
				number4 = rs.getInt("number4");

			}
			while (rs2.next()) {
				i++;

				// ��ֹsug�г���null�ı��
				if (sug == null) {
					sug = "��" + i + "���ǣ�" + rs2.getString("advice")+"\n";
				} else {
					sug += "��" + i + "���ǣ�" + rs2.getString("advice")+"\n";
				}
			}

			v.setName(name);
			v.setItem(item);
			v.setOption1(option1);
			v.setOption2(option2);
			v.setOption3(option3);
			v.setOption4(option4);
			v.setNumber1(number1);
			v.setNumber2(number2);
			v.setNumber3(number3);
			v.setNumber4(number4);
			v.setSuggesstion(sug);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return v;
	}

	// ���¹�������,�����뵽��־��¼��
	private void update(String name, String information) {
		String adddata = null;
		String adddata2 = null;

		adddata = "insert into information1 (`Name`, information)  values('" + name + "'," + "'" + information + "'"
				+ ")";
		adddata2 = "insert into record (xingming,item) values ('" + name + "','����')";

		System.out.println(adddata);

		IDUS.executeUpdate(adddata);
		IDUS.executeUpdate(adddata2);

	}

	// �༭����ͷ���ͶƱ�ļ��,�����ǹ���Ա����
	private boolean exist(String name) {
		boolean b = false;
		String nameV = name;

		try {

			ResultSet rs;
			rs = IDUS.executeQuery("SELECT * FROM student1 WHERE Administrator=1;");

			while (rs.next()) {
				String nameT = rs.getString("Name").trim();

				if (nameT.equals(nameV)) {
					b = true;
					break;
				}

			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}

		return b;
	}

	// 1��ѯ���¹���
	private String getInformation1() {

		ResultSet rs;
		String information = null;

		try {
			rs = IDUS.executeQuery("select * from information1");

			while (rs.next()) { // ���timestamp���Ժ����ɵĽ�����¹�������

				String name = rs.getString("name").trim();
				String message = rs.getString("information");
				String time = rs.getString("time");

				information = ("�����ˣ�" + name + "\r\n" + "      " + "���� :" + message + "\r\n" + "����ʱ�䣺" + "\r\n" + time
						+ "\r\n");// �м�Ŀ��ַ���ʹ���������Ķ�
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return information;
	}

	// 2��ѯ���й���
	private String getInformation2() {
		ResultSet rs;
		String information = null;
		List<String> aList = new ArrayList<String>();// ����Array List�Թ�����д���

		try {
			rs = IDUS.executeQuery("select * from information1");
			while (rs.next()) {

				String name = rs.getString("name").trim();
				String message = rs.getString("information");
				String time = rs.getString("time");

				aList.add("�����ˣ�" + name + "\r\n" + "  " + "���� :" + message + "\r\n" + "����ʱ�䣺"  + time + "\r\n");// ʹ��java�е�ת���"\r\n":ʵ�ֻ���
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		information = aList.toString();// ��array listת���string
		
		information = information.replace(',', ' ');
		information = information.replace('[', ' ');
		information = information.substring(0, information.length()-1);
		

		return information;
	}

	// ��־��¼�ĳ���
	private String checkrec() {
		ResultSet rs;
		String record = null;
		List<String> aList = new ArrayList<String>();// ����Array List�Թ�����д���
		int i = 1;// �Լ�¼����

		try {
			rs = IDUS.executeQuery("select * from record");
			while (rs.next()) {

				String name = rs.getString("xingming").trim();

				String time = rs.getString("time");

				String item = rs.getString("item");

				if (item.equals("����")) {
					aList.add(i + "������Ա��" + name + "\n" + "��" + time + "��" + item + "�������޸�" + "\r\n");// ʹ��java�е�ת���"\r\n":ʵ�ֻ���
				} else if (item.equals("ͶƱ")) {
					aList.add(i + "������Ա��" + name + "\n" + "��" + time + "������" + item + "\r\n");// ʹ��java�е�ת���"\r\n":ʵ�ֻ���
				}
				i++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		record = aList.toString();//ȥ��������
		
		//ʹ����ĸ�ʽ������
		record = record.replace(',', ' ');
		record = record.replace('[', ' ');
		record = record.substring(0, record.length()-1);
		

		return record;
	}

	// ע��ļ��
	private boolean chekRES(Users u) {
		ResultSet rs;
		rs = IDUS.executeQuery("select * from student1");
		boolean b = false;

		try {

			rs = IDUS.executeQuery("select * from student1");

			while (rs.next()) {
				String name = u.getName();// ��ʵ����,�ӿͻ��˴�����
				String nameD = rs.getString("Name").trim();
				String number = u.getId();// ѧ��,�ӿͻ��˴�����
				String numberD = rs.getString("number");

				String user = u.getvName();// �ǳ�
				String pass = u.getPassword();// ����

				if (pass.trim().length() == 0) {// �ַ������ǿյĲ����ǰ�����ȫ���ַ�
//					JOptionPane.showMessageDialog(null, "�������ַ�", "������ǿյ�", JOptionPane.ERROR_MESSAGE);
					break;
				} else {

					if (name.equals(nameD) && number.equals(numberD)) {
						String adddata = "UPDATE student1 SET password=" + "'" + pass + "'" + ", users=" + "'" + user
								+ "'" + " WHERE Name=" + "'" + name + "'";
						System.out.append("OK");
						IDUS.executeUpdate(adddata);
//						JOptionPane.showMessageDialog(null, "ע��ɹ�", "��ر�ע�����", JOptionPane.OK_OPTION);
						b = true;
						break;

					}

				}

			}
			DBConnection.close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// ��������ʵ����
	private boolean checkname(String name) {
		boolean b = false;
		String nameV = name;

		try {

			ResultSet rs;
			rs = IDUS.executeQuery("SELECT * FROM student1 ;");

			while (rs.next()) {
				String nameT = rs.getString("Name").trim();

				if (nameT.equals(nameV)) {
					b = true;
					break;
				}

			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}

		return b;
	}

	// �������ͶƱ�������ݿ⣬��������־��¼��
	private void addvote(SetVote t) {

		String adddata = "INSERT INTO vote (`name`,item," + "option1,option2,option3,option4) VALUES('" + t.getName()
				+ "'" + ",'" + t.getItem() + "','" + t.getOption1() + "','" + t.getOption2() + "','" + t.getOption3()
				+ "','" + t.getOption4() + "')";

		String adddata2 = "insert into record (xingming,item) values ('" + t.getName() + "','ͶƱ')";

		IDUS.executeUpdate(adddata);
		IDUS.executeUpdate(adddata2);

	}

	// ��ͶƱ�Ľ���������ݿ�
	private boolean voted(SetVote t) {

		boolean b = true;

		String update = "UPDATE vote set number1=" + t.getNumber1() + ",number2=" + t.getNumber2() + ",number3="
				+ t.getNumber3() + ",number4=" + t.getNumber4() + "  WHERE item=" + "'" + t.getItem() + "'";

		String update2 = null;

		String update3 = "INSERT INTO votename VALUES ('" + t.getVote_name() + "');";

		// ��ֹ����յ����

		if (!t.getSuggesstion().equals("no")) {
			update2 = "INSERT into suggesstion (advice) VALUES ('" + t.getSuggesstion() + "');";
		}

		// һ����ͶƱ��һ����

		try {
			ResultSet rs;
			rs = IDUS.executeQuery("SELECT * FROM votename ;");
			// �������ݿ⣬û��������
			while (rs.next()) {
				String name = rs.getString("votename").trim();
				System.out.println("*********************************" + t.getVote_name());
				if (name.equals(t.getVote_name())) {
					b = false;

					break;
				}

			}
			// û�������ļ������ݿ�
			if (b) {
				// ִ����䣡����������������
				IDUS.executeUpdate(update);
				
				if (update2 != null) {
					IDUS.executeUpdate(update2);
				}
				
				IDUS.executeUpdate(update3);
			}

		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}

		return b;

	}

	//�ж�ͶƱ�Ƿ����
	private boolean checkvote_end() {
		boolean b = true;
		
		int studentNumber = IDUS.getStudentNumber();//��ð༶��������
		
		int i =0;
		ResultSet rs;
		rs = IDUS.executeQuery("SELECT * FROM votename ;");
		
		try {
			while (rs.next()) {
				i++;
				}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		if(i == studentNumber) {//�������Ѿ�Ͷ��Ʊ��
			b =false;
		}
		
		
		return b;
	}
	
	// ��½��ͬʱ��ȡ��½�ߵ�������Ϣ
	private students getstudent(String vname) {
		students stu = new students();

		try {
			
			ResultSet rs;
			rs = IDUS.executeQuery("select * from student1");

			String name = null;
			String number = null;
			String password = null;
			boolean b = false;

			while (rs.next()) {
				String nicheng = rs.getString("users");

				if (nicheng.equals(vname)) {
					name = rs.getString("Name");
					number = rs.getString("number");
					password = rs.getString("password");

					b = rs.getBoolean("Administrator");
				}

			}

			stu.setName(name);
			stu.setNumber(number);
			stu.setPassword(password);
			stu.setAdministrator(b);

		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		System.out.println("stu:" + stu.getName());

		return stu;
	}

	
	
//�߳���
	class CheckSocket extends Thread// �߳���
	{
		Socket s = null;

		public CheckSocket(Socket socket) {
			this.s = socket;
		}

		public void run() {
			while (1 < 2) {
				try {
					ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
					Users u = (Users) ois.readObject();// read��ȡ
					System.out.println(u.getDosomething());

					Message m = new Message();
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

					// ��½����֤
					if (u.getDosomething().equals("7")) {

						ResultSet rs;
						rs = IDUS.executeQuery("select * from student1");
						try {

							boolean cmp = false, cmp2 = false;
							while (rs.next()) {
								if (rs.getString("users") != null) {
									String name = rs.getString("users").trim();// ������ݿ��ǳ�
									String password = rs.getString("password");// ������ݿ�����
									if (password != null) {

										boolean permission = rs.getBoolean("Administrator");
										cmp = name.equals(u.getvName());
										if (cmp) {// �ַ�������Ȳ����õ��ں��ж�
											String password2 = new String(u.getPassword());// �������
											cmp2 = password2.equals(password);
											if (cmp2) {
												if (permission) {
													System.out.println("�ѿ�ʼ��½01");
													m.setStu(getstudent(u.getvName()));
													m.setMessType(MessageType.message_login_succed_A);
													oos.writeObject(m);
													oos.flush();
												} else {
													System.out.println("�ѿ�ʼ��½02");
													m.setStu(getstudent(u.getvName()));
													m.setMessType(MessageType.message_Login_succed_B);
													oos.writeObject(m);
													oos.flush();
												}
											}

										}

									}

								}
							}
							if ((!cmp) || (!cmp2)) {
								m.setMessType(MessageType.message_login_fail);

								System.out.println("��½ʧ�ܷ���ȥ��");

								System.out.println(m.getMessType());
//�޴��bugû�н������͹�ȥ							
								oos.writeObject(m);// ����һ��Ҫд��ȥ
							}

							DBConnection.close(rs);
						} catch (SQLException e2) {
							e2.printStackTrace();
							System.out.println("���ݿ�����ʧ��");
						}
					}

					// ע�����
					if (u.getDosomething().equals("6")) {
						if (chekRES(u)) {
							m.setMessType(MessageType.message_resiger_succed);
							oos.writeObject(m);
						} else {
							m.setMessType(MessageType.message_resiger_fail);
							oos.writeObject(m);
						}
					}

					// ��־��¼
					if (u.getDosomething().equals(MessageType.message_record)) {
						m.setInformation(checkrec());

						m.setMessType(MessageType.message_record_successfully);

						oos.writeObject(m);// �Ѷ���д��ȥ
					}

					// �鿴���¹���
					if (u.getDosomething().equals(MessageType.message_information)) {
						System.out.println("������ȥ���ݿ���ù���");
						m.setInformation(getInformation1());// ���ͻ�õ�����
						m.setMessType(MessageType.message_information_succeed);
						oos.writeObject(m);
					}

					// �鿴���๫��
					if (u.getDosomething().equals("�鿴���๫��")) {// ��ΪuesrsȥΪmessage
						m.setInformation(getInformation2());
						m.setMessType("�鵽��");
						oos.writeObject(m);
					}

					// ����༭
					if (u.getDosomething().equals(MessageType.message_information_Compile)) {
				
							update(u.getName(), u.getInformation());
							m.setMessType(MessageType.message_information_Compile_succeed);
							oos.writeObject(m);
						
					}

					// ����ͶƱ����
					if (u.getDosomething().equals(MessageType.message_vote)) {
						
							addvote(u.getTicket());

							m.setInformation("����ͶƱ�ɹ�");

							oos.writeObject(m);
						

					}

					// ͶƱ�б�
					if (u.getDosomething().equals(MessageType.message_vote_take)) {
						m.setInformation("ͶƱ�б�");

						m.setTicket(getVote());

						oos.writeObject(m);
					}

					// ��ʼͶƱ
					if (u.getDosomething().equals(MessageType.message_vote_take_successful)) {

						if (checkvote_end()) {
							if (voted(u.getTicket())) {

								m.setInformation("ͶƱ�ɹ�");

								oos.writeObject(m);
							}

							else {
								m.setInformation("�ظ�ͶƱ");

								oos.writeObject(m);
							}

						} else {
							m.setInformation("ͶƱͶ����");
							
							//��ûд��ȥ����������������������
							
							oos.writeObject(m);
						}
					}

					// �ļ������б�
					if (u.getDosomething().equals(MessageType.message_filelist)) {
						m.setInformation("�ļ��б�");

						m.setFileList(filelist());

						oos.writeObject(m);
					}

//System.out.println(u.getDosomething()+"lkjkj");
					// �ļ�������
					if (u.getDosomething().equals(MessageType.message_download)) {// ��ָ���������ļ�һ��һ���Ĵ���ȥ

						System.out.println("��֪����Ҫ����ʲô��");

						LinkedList<String> filelist2 = u.getFileList();

						LinkedList<String> filelist1 = filelist();// ��ù���ռ�������ļ�������

						String path = "F:/������/�����ļ��Ŀռ�";
						File dir = new File(path);
						File[] files = dir.listFiles();// �����ļ�������

						for (int i = 0; i < files.length; i++) {
							System.out.println(GMKB.getFormatFileSize(files[i].length()));
						}

						LinkedList<File> filedown = new LinkedList<File>();
						for (File file : files) {
							for (int i = 0; i < filelist2.size(); i++) {
								if (filelist2.get(i).equals(file.getName())) {

									System.out.println("OKOKOK��Ҫ��������ļ�");
									System.out.println(GMKB.getFormatFileSize(file.length()));
									filedown.add(file);

								}
							}
						}
						m.setMessType("��ʼ����");
						m.setFiles(filedown);
						oos.writeObject(m);
						oos.flush();

					}

				} catch (SocketException e2) {
					System.out.println("�û��˳���1");
					break;
				} catch (EOFException e4) {
					System.out.println("�û��˳���2");
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		

	}
}
