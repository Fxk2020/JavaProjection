package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/*
* 1--����+���+�̶�
* 2--�ڽ����ϻ�����
* 3--���߶�����
* 4--���������̵ķ����ָ��
* 5--����������Ӳ����ڽ�����
* 6--ָ���߳Ե����Ӳ���������
* 7--ײǽGAME OVER ��
* 8--ҧ���Լ���β�� GAME OVER !
* 9--�޸�BUG
*/
public class SnakeGame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	private int fw = 800;
	private int fh = 600;
	private SP sp = null;
	private int sex = 200; // �߹ؽ�Ԫ������
	private int sey = 200;
	private int sesize = 20;
	private SE se = null;
	private Timer timer = new Timer();
	private LinkedList<SE> ses = new LinkedList<SE>();
	private String direction = "up"; // �����ߵķ���
	private int bx; // ��������
	private int by;
	private int bsize = sesize;
	private Bean bean = null;
	private Random rand = new Random();
	private boolean bean_is_eated = false;
	
	//�������˳����ڵ����˳�����
	private boolean xianshi = true;

	public SnakeGame() {
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		this.getContentPane().setBackground(Color.BLACK);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		sp = new SP();
		this.add(sp);
		
		new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				while(true) {
					if(!xianshi) {
						SnakeGame.this.dispose();
						System.out.print(xianshi);
						sp.setVisible(false);
						this.stop();
					}
					
				    System.out.println(xianshi);
				}
			} 
	}.start();
		this.setVisible(true);
		this.addKeyListener(this);
	}

	class SP extends JPanel {
		private static final long serialVersionUID = 1L;

		public SP() {
			this.setOpaque(false);
			this.getSE(); // ���ô����ߵķ���
			this.getBean_not_on_snakebody();// ���ô������ӵķ���
			timer.schedule(new TimerTask() {
				@Override
				public void run() { // ��Ҫִ�е�����
					if ("up".equalsIgnoreCase(direction)) {
						ses.removeLast(); // �Ƴ����һ��Ԫ��
//��ԭ����һ��Ԫ�صĻ����� ����һ��Ԫ��
						ses.addFirst(new SE(ses.getFirst().getSex(),
								ses.getFirst().getSey() - ses.getFirst().getSesize(), ses.getFirst().getSesize()));
//��ԭ������Ļ����� β������һ���ؽ�Ԫ�ض���
						if (bean_is_eated) {
							ses.addLast(new SE(ses.getLast().getSex(),
									ses.getLast().getSey() + ses.getLast().getSesize(), ses.getLast().getSesize()));
							bean_is_eated = false;
						}
//�ж��Ƿ�ײǽ ײǽ GAME OVER ��
						if (ses.getFirst().getSey() < 0) {
							JOptionPane.showMessageDialog(sp, "GAME OVER !", "ײǽ��ʾ", JOptionPane.DEFAULT_OPTION);
							xianshi = false;
							timer.cancel();
						}
					}
					if ("left".equalsIgnoreCase(direction)) {
						ses.removeLast(); // �Ƴ����һ��Ԫ��
//��ԭ����һ��Ԫ�صĻ����� ����һ��Ԫ��
						ses.addFirst(new SE(ses.getFirst().getSex() - ses.getFirst().getSesize(),
								ses.getFirst().getSey(), ses.getFirst().getSesize()));
//��ԭ������Ļ����� β������һ���ؽ�Ԫ�ض���
						if (bean_is_eated) {
							ses.addLast(new SE(ses.getLast().getSex() + ses.getLast().getSesize(),
									ses.getLast().getSey(), ses.getLast().getSesize()));
							bean_is_eated = false;
						}
//�ж��Ƿ�ײǽ ײǽ GAME OVER ��
						if (ses.getFirst().getSex() < 0) {
							JOptionPane.showMessageDialog(sp, "GAME OVER !", "ײǽ��ʾ", JOptionPane.DEFAULT_OPTION);
							xianshi = false;
							timer.cancel();
						}
					}
					if ("down".equalsIgnoreCase(direction)) {
						ses.removeLast(); // �Ƴ����һ��Ԫ��
//��ԭ����һ��Ԫ�صĻ����� ����һ��Ԫ��
						ses.addFirst(new SE(ses.getFirst().getSex(),
								ses.getFirst().getSey() + ses.getFirst().getSesize(), ses.getFirst().getSesize()));
//��ԭ������Ļ����� β������һ���ؽ�Ԫ�ض���
						if (bean_is_eated) {
							ses.addLast(new SE(ses.getLast().getSex(),
									ses.getLast().getSey() - ses.getLast().getSesize(), ses.getLast().getSesize()));
							bean_is_eated = false;
						}
//�ж��Ƿ�ײǽ ײǽ GAME OVER ��
						if (ses.getFirst().getSey() + sesize > fh) {
							JOptionPane.showMessageDialog(sp, "GAME OVER !", "ײǽ��ʾ", JOptionPane.DEFAULT_OPTION);
							xianshi = false;
							timer.cancel();
						}
					}
					if ("right".equalsIgnoreCase(direction)) {
						ses.removeLast(); // �Ƴ����һ��Ԫ��
//��ԭ����һ��Ԫ�صĻ����� ����һ��Ԫ��
						ses.addFirst(new SE(ses.getFirst().getSex() + ses.getFirst().getSesize(),
								ses.getFirst().getSey(), ses.getFirst().getSesize()));
//��ԭ������Ļ����� β������һ���ؽ�Ԫ�ض���
						if (bean_is_eated) {
							ses.addLast(new SE(ses.getLast().getSex() - ses.getLast().getSesize(),
									ses.getLast().getSey(), ses.getLast().getSesize()));
							bean_is_eated = false;
						}
//�ж��Ƿ�ײǽ ײǽ GAME OVER ��
						if (ses.getFirst().getSex() + sesize > fw) {
							JOptionPane.showMessageDialog(sp, "GAME OVER !", "ײǽ��ʾ", JOptionPane.DEFAULT_OPTION);
							xianshi = false;
							timer.cancel();
						}
					}
//��ҧ���Լ�������ж�
					for (int i = 1; i < ses.size(); i++) {
						if (ses.getFirst().getSex() == ses.get(i).getSex()
								&& ses.getFirst().getSey() == ses.get(i).getSey()) {
							JOptionPane.showMessageDialog(sp, "��������ҧ���Լ���������", "�Բ��ˣ��ֵܣ�", JOptionPane.DEFAULT_OPTION);
							xianshi = false;
							timer.cancel();
							
						}
					}
					sp.eatBean(); // ���óԶ��ӵķ���
					sp.repaint();
				}
			}, 0, 200);
		}

		@Override
		public void paint(Graphics g) {
			for (int i = 0; i < ses.size(); i++) {
//��������
				g.setColor(Color.WHITE);
				g.fill3DRect(bean.getBx(), bean.getBy(), bean.getBsize(), bean.getBsize(), true);
//������
				if (i == 0) {
					g.setColor(Color.RED);
				} else {
					g.setColor(Color.GREEN);
				}
				g.fill3DRect(ses.get(i).getSex(), ses.get(i).getSey(), ses.get(i).getSesize(), ses.get(i).getSesize(),
						true);
			}
		}

		public void eatBean() {
//���ӱ����� //�ߵ�ͷ������Ͷ��ӵ������غ�
			if (ses.getFirst().getSex() == bean.getBx() && ses.getFirst().getSey() == bean.getBy()) {
				bean = new Bean();
				sp.getBean_not_on_snakebody();
				bean_is_eated = true;
			}
		}

		public void getBean_not_on_snakebody() {
			this.getBean();
// * ���Ӳ��ܳ������ߵ������� �� bx ��by ͬʱ���ߵ�����ÿһ��Ԫ�ؽ��бȽ� ��֤û��ȫ��
			for (int i = 0; i < ses.size(); i++) {
				if (ses.get(i).getSex() == bean.getBx() && ses.get(i).getSey() == bean.getBy()) {
					this.getBean();
				}
			}
		}

		public void getBean() {
// * ���ܳ������ұߵı߽��� bx != fw
// * ���ӵ�x��������ܹ�����������Ϊ0 ��������Ԫ�صĳߴ� bx % sesize == 0
			while (true) {
				bx = rand.nextInt(fw);
				System.out.println(bx);
				if (bx != fw && bx % sesize == 0) {
					break;
				}
			}
// * ���ܳ������±ߵı߽��� by != fh 
// * ���ӵ�y��������ܹ�����������Ϊ0�� ������Ԫ�صĳߴ� by % sesize == 0 
			for (;;) {
				by = rand.nextInt(fh);
				if (by != fh && by % sesize == 0) {
					break;
				}
			}
			bean = new Bean(bx, by, bsize);
			System.out.println(bean);
		}

		public void getOneSEData() { // ��ȡһ���߹ؽ�Ԫ�ص�����
		}

		public void getSE() {// ��ȡһ���߹ؽڶ���
			for (int i = 0; i < 4; i++) {
				se = new SE(sex, sey + sesize * i, sesize);
				ses.add(se);
			}
		}

		public void getSnake() { // ��ȡһ������
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
//���� ���ܸı�Ϊ�෴�ķ��� ���磺left ���� �޸�Ϊ right
		if (e.getKeyCode() == KeyEvent.VK_LEFT && !"right".equalsIgnoreCase(direction)) {
			direction = "left";
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && !"up".equalsIgnoreCase(direction)) {
			direction = "down";
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !"left".equalsIgnoreCase(direction)) {
			direction = "right";
		}
		if (e.getKeyCode() == KeyEvent.VK_UP && !"down".equalsIgnoreCase(direction)) {
			direction = "up";
		}
		if (e.getKeyCode() == 32) {
			Runtime.getRuntime().exit(0);
		}
	}

	public static void main(String[] args) {
		new SnakeGame();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}

class SE implements Serializable {
	/**
	 * �ߵĹؽڶ���
	 */
	private static final long serialVersionUID = 1L;
	private int sex;
	private int sey;
	private int sesize;

	public SE() {
		super();
// TODO Auto-generated constructor stub
	}

	public SE(int sex, int sey, int sesize) {
		super();
		this.sex = sex;
		this.sey = sey;
		this.sesize = sesize;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getSey() {
		return sey;
	}

	public void setSey(int sey) {
		this.sey = sey;
	}

	public int getSesize() {
		return sesize;
	}

	public void setSesize(int sesize) {
		this.sesize = sesize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + sesize;
		result = prime * result + sex;
		result = prime * result + sey;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SE other = (SE) obj;
		if (sesize != other.sesize)
			return false;
		if (sex != other.sex)
			return false;
		if (sey != other.sey)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SE [sesize=" + sesize + ", sex=" + sex + ", sey=" + sey + "]";
	}
}

class Bean implements Serializable {
	/**
	 * ���Ӷ���
	 */
	private static final long serialVersionUID = 1L;
	private int bx;
	private int by;
	private int bsize;

	public Bean() {
		super();
// TODO Auto-generated constructor stub
	}

	public Bean(int bx, int by, int bsize) {
		super();
		this.bx = bx;
		this.by = by;
		this.bsize = bsize;
	}

	public int getBx() {
		return bx;
	}

	public void setBx(int bx) {
		this.bx = bx;
	}

	public int getBy() {
		return by;
	}

	public void setBy(int by) {
		this.by = by;
	}

	public int getBsize() {
		return bsize;
	}

	public void setBsize(int bsize) {
		this.bsize = bsize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bsize;
		result = prime * result + bx;
		result = prime * result + by;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bean other = (Bean) obj;
		if (bsize != other.bsize)
			return false;
		if (bx != other.bx)
			return false;
		if (by != other.by)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bean [bsize=" + bsize + ", bx=" + bx + ", by=" + by + "]";
	}
}