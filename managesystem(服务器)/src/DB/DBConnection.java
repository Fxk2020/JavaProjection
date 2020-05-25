package DB;

import java.sql.*;

public class DBConnection {
	//��������
	private static final String DBDRIVER="com.mysql.cj.jdbc.Driver";
	//���ݿ������sqltest
	private static final String DBURL="jdbc:mysql://localhost:3306/sqltest?&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
	//�û���root
	private static final String DBUSER="root";
	//����Fxk199959
	private static final String DBPASSWORD="Fxk199959";
	static {
		try {
			Class.forName(DBDRIVER);
			System.out.println("���������ɹ�");
		} catch (ClassNotFoundException e) {

			System.out.println("��������ʧ��");
		}
	}
	public static Connection getConnection() {
		Connection conn=null;
		try {
			conn=DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);
			System.out.println("���ӳɹ�");
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("���������쳣");
		}
		return conn;
	}
	
	public static void  close(Connection conn) {
		if (conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("�ر����ݿ����ӷ����쳣");
			}
			
		}
		
	}
	public static void close(Statement stmt) {
		if (stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("�ر����ݿ������Դ�����쳣");
			}
			
		}
	}
	public static void close(ResultSet rs) {
		if (rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("�رս���������쳣");
			}
			
		}
		
	}

}
