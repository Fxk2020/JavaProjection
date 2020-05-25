
package page;

/*
 * �ͻ���?
 */
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import common.Message;
import common.MessageType;
import tools.ManageClientConnectionServerThread;

public class PaintUI extends JFrame {

	public JPanel drawPanel;
	public JPanel colorPanel;
	public JPanel waitPanel;
	public JPanel drawLeftPanel;
	public JPanel centerPanel;
	public JTextField jtf;
	public JTextArea jta;
	public Graphics2D g;
	public Color color;
	// public ClientCtroller control;
	public Socket socket;
	public int x1, y1;
	public BasicStroke strock;
	public JComboBox<Integer> box;
	String sender;
	String accepter;
	Message m;

	public PaintUI(String sender, String accepter) {
		m = new Message();
		this.sender = sender;
		this.accepter = accepter;
		m.setSender(this.sender);
		m.setGetter(this.accepter);
		m.setMesType(MessageType.message_x_y);

		this.setTitle(sender + "���ֻ�");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(3);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addDrawPanel();
	}

	// ��ӻ����ĺ���
	public void addDrawPanel() {
		drawPanel = new JPanel();
		drawPanel.setLayout(new BorderLayout());
		// ���������������
		drawLeftPanel = new JPanel();
		drawLeftPanel.setLayout(new BorderLayout());

		// ��߰���м����
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		// ������µ���ɫ���
		colorPanel = new JPanel();

		// ����ɫ������ÿղ���
		colorPanel.setLayout(null);
		colorPanel.setBackground(Color.pink);
		colorPanel.setPreferredSize(new Dimension(0, 60));

		// ��ɫ������ɫ��ť
		Color[] colors = { Color.red, Color.black };

		// ��ɫ��ť���
		ActionListener btnlistener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JButton bt = (JButton) e.getSource();
				color = bt.getBackground();
			}
		};
		for (int i = 0; i < colors.length; i++) {
			JButton btn = new JButton();
			btn.setBackground(colors[i]);
			btn.addActionListener(btnlistener);
			btn.setBounds(40 + i * 30, 15, 30, 30);
			colorPanel.add(btn);
		}
		// ��ӻ��ʴ�ϸ
		box = new JComboBox<Integer>();
		box.setBounds(380, 15, 80, 30);
		for (int i = 0; i < 10; i++) {
			Integer intdata = new Integer(i + 1);
			box.addItem(intdata);
		}
		colorPanel.add(box);

		jta = new JTextArea();
		jta.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(jta);
		jtf = new JTextField(11);
		drawLeftPanel.add(centerPanel, BorderLayout.CENTER);
		drawLeftPanel.add(colorPanel, BorderLayout.NORTH);
		drawPanel.add(drawLeftPanel);
		this.add(drawPanel);
		centerPanel.addMouseListener(ma);
		centerPanel.addMouseMotionListener(ma);
		this.setVisible(true);
		g = (Graphics2D) centerPanel.getGraphics();
	}

	// ��������
	MouseAdapter ma = new MouseAdapter() {

		public void mousePressed(MouseEvent e) {
			x1 = e.getX();
			y1 = e.getY();
			System.out.println("����1" + x1 + "," + y1);
		};

		public void mouseEntered(MouseEvent e) {
			if (color == null) {
				color = Color.black;
			}
			g.setColor(color);
		};

		public void mouseDragged(MouseEvent e) {
			int width = (Integer) box.getSelectedItem();
			strock = new BasicStroke(width);
			g.setStroke(strock);
			int x2 = e.getX();
			int y2 = e.getY();
			// �����귢���������������ٷ����Ǹ��ͻ���
			// g.fillOval(x2, y2, width, width);
			g.drawLine(x1, y1, x2, y2);

			m.setX1(x1);
			m.setY1(y1);
			m.setX2(x2);
			m.setY2(y2);
			try {
				// control.sendMsg1(socket.getOutputStream(), x1, y1, x2,
				// y2,g.getColor().getRGB(),width);
				x1 = x2;
				y1 = y2;
			} catch (Exception e1) {
			}
			m.setColor(color);
			m.setWidth(width);

			try {
				ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectionServerThread
						.getClientConnectionServerThread(sender).getS().getOutputStream());
				oos.writeObject(m);
			} catch (IOException e1) {

				e1.printStackTrace();
			}

		};
	};
}

