package book;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JFrame {
//	JPanel target = new JPanel();
	ImagePanel target = new ImagePanel();
	public Test() {
		super("Á¦¸ñ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		this.add(target);
		
		target = new ImagePanel("C:\\Users\\402-27\\Desktop\\gg.jpg");
		
		setSize(200, 200);
		setVisible(true);
		
	}

	
	
	public static void main(String[] args) {
		new Test();
	}
	
	
	
	
	class ImagePanel extends Panel {
		ImageIcon imageicon;
		Image image;
		
		public ImagePanel() {
			
		}
		
		public ImagePanel(String location) {
			imageicon = new ImageIcon(location);
			image = imageicon.getImage();
		}
		
		@Override
		public void paintComponents(Graphics g) {
			super.paintComponents(g);
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}
}
