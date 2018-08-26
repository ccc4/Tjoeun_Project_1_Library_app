package panels;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private final String DEFAULT_BOOK_IMAGE = ".\\init\\default.jpg";
	private final String NOT_EXIST_BOOK_IMAGE = ".\\init\\not_Exist.jpg";
	
	ImageIcon imageicon;
	Image image;
	
	public ImagePanel() {
		imageicon = new ImageIcon(DEFAULT_BOOK_IMAGE);
		image = imageicon.getImage();
	}
	
	public ImagePanel(String location) {
		imageicon = new ImageIcon(location);
		image = imageicon.getImage();
	}
	
	public void setImage(String location) {
		imageicon = new ImageIcon(location);
		image = imageicon.getImage();
		repaint();
	}
	
	public void checkImage(String location) {
		File file = new File(location);
		if(file.exists()) {
			imageicon = new ImageIcon(location);
		} else {
			imageicon = new ImageIcon(NOT_EXIST_BOOK_IMAGE);
		}
		image = imageicon.getImage();
		repaint( );
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
