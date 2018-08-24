package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import book.BookDAO;
import book.BookDTO;
import mainFrame.MainFrame;

public class BookAddDialog extends JDialog {
	BookDAO bookDAO;
	
	File targetFile;
	String bookName;
	
	JPanel imagePanel;
//	ImagePanel imagePanel;

	public void setBookDAO(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}
	
	public BookAddDialog(MainFrame frame, String title) {
		super(frame, title);
		
		JPanel centerPanel = new JPanel(new GridLayout(1, 2));
		JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JPanel writePanel = new JPanel(new GridLayout(3, 1));
		imagePanel = new JPanel();  // 이미지가 들어오면 다시 변경해야함.
		centerPanel.add(writePanel);
		centerPanel.add(imagePanel);
		
		JPanel writePanel_1 = new JPanel(new BorderLayout());
		JPanel writePanel_2 = new JPanel(new BorderLayout());
		JPanel writePanel_3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		writePanel.add(writePanel_1);
		writePanel.add(writePanel_2);
		writePanel.add(writePanel_3);
		JLabel titleLabel = new JLabel("  제목  ");
		JTextField titleField = new JTextField();
		JLabel authorLabel = new JLabel("  저자  ");
		JTextField authorField = new JTextField();
		JButton imgBtn = new JButton("이미지");
		writePanel_1.add(titleLabel, BorderLayout.WEST);
		writePanel_1.add(titleField, BorderLayout.CENTER);
		writePanel_2.add(authorLabel, BorderLayout.WEST);
		writePanel_2.add(authorField, BorderLayout.CENTER);
		writePanel_3.add(imgBtn);
		
		JButton addBtn = new JButton("등록");
		JButton exitBtn = new JButton("취소");
		botPanel.add(addBtn);
		botPanel.add(exitBtn);
		
		
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(botPanel, BorderLayout.SOUTH);
		
		imagePanel.setOpaque(true);
		imagePanel.setBackground(Color.LIGHT_GRAY);
		
		setSize(300, 200);
		setLocationRelativeTo(null);
		
		
		
		
		
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		imgBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				JFileChooser chooser = new JFileChooser(".\\Book Images");
				JFileChooser chooser = new JFileChooser("C:\\Users\\402-27\\Desktop");
				int ret = chooser.showOpenDialog(null);
				if(ret != JFileChooser.APPROVE_OPTION) return;
				
				String targetFilePath = chooser.getSelectedFile().getPath();
				targetFile = new File(targetFilePath);
//				byte[] targetContents = new byte[(int) targetFile.length()];
				
			}
		});
		
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String bookName = titleField.getText();
				String bookAuthor = authorField.getText();
				int bookState = -2;
				String rentaledByWho = null;
				
//				System.out.printf("%s, %s, %d, %s", bookName, bookAuthor, bookState, rentaledByWho);
				BookDTO newBook = new BookDTO(bookName, bookAuthor, bookState, rentaledByWho);
				if(bookDAO.checkExist(bookName)) {
					JOptionPane.showMessageDialog(null, "이미 등록된 책입니다.", "Alert", JOptionPane.WARNING_MESSAGE);
				} else {
					bookDAO.addBook(bookName, newBook);
					JOptionPane.showMessageDialog(null, "책 등록완료", "Alert", JOptionPane.INFORMATION_MESSAGE);
				}
				setVisible(false);
			}
		});
		
	}



	
//	class ImagePanel extends JPanel {
//		ImageIcon imageicon;
//		Image image;
//		
//		public ImagePanel() {
//			
//		}
//		
//		public ImagePanel(String location) {
//			imageicon = new ImageIcon(location);
//			image = imageicon.getImage();
//		}
//		
//		@Override
//		public void paintComponents(Graphics g) {
//			super.paintComponents(g);
//			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
//		}
//	}
}
