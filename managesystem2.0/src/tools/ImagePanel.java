package tools;

//��̬����һ��ͼƬ�������ĵ�JPanel
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	Image picture;

	// ���캯��ȥָ����Panel��С
	public ImagePanel(Image picture) {
		this.picture = picture;
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(w, h);
	}

	// ��������
	public void paintComponent(Graphics g) {
		// ����
		super.paintComponents(g);
		g.drawImage(picture, 0, 0, this.getWidth(), this.getHeight(), this);

	}
}
