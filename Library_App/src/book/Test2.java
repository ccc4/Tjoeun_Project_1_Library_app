package book;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Test2 extends JFrame {
	Container contentPane;
	
	public Test2() {
		super("Á¦¸ñ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = getContentPane();
		JLabel panel = new JLabel(new ImageIcon("C:\\Users\\402-27\\Desktop\\gg.jpg"));
		contentPane.add(panel, BorderLayout.CENTER);
		
		setSize(200, 200);
		setVisible(true);
		
	}

	
	
	
	
	
	
//	class ImagePanel extends JPanel {
//		ImageIcon imageicon = new ImageIcon("./gg.jpg");
//		Image image = imageicon.getImage();
//		
//		
//		@Override
//		public void paintComponents(Graphics g) {
//			super.paintComponents(g);
//			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
//		}
//	}
	public static void main(String[] args) {
		new Test2();
	}
}
