package dialog.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import book.BookDAO;
import book.BookDTO;
import mainFrame.MainFrame;
import panels.ImagePanel;

public class BookAddDialog extends JDialog {
	
	BookDAO bookDAO;
//	File targetFile;
	String bookName;
	String targetImgFilePath = "";
	
	JPanel botPanel;
	JPanel centerPanel;
	
	JPanel writePanel;
	ImagePanel imagePanel; // �̹����г�
	
	JPanel writePanel_1 = new JPanel(new BorderLayout());
	JPanel writePanel_2 = new JPanel(new BorderLayout());
	JPanel writePanel_3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	JLabel titleLabel = new JLabel("  ����  ");
	JTextField titleField = new JTextField();
	JLabel authorLabel = new JLabel("  ����  ");
	JTextField authorField = new JTextField();
	JButton imgBtn = new JButton("�̹���");
	
	JButton addBtn = new JButton("���");
	JButton exitBtn = new JButton("���");

	public void setBookDAO(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}
	
	public BookAddDialog(MainFrame frame, String title) {
		super(frame, title, true);
		
		centerPanel = new JPanel(new GridLayout(1, 2));
		botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		writePanel = new JPanel();
		writePanel.setLayout(new BoxLayout(writePanel, BoxLayout.Y_AXIS));
		imagePanel = new ImagePanel();  // �̹����� ������ �ٽ� �����ؾ���.
		centerPanel.add(writePanel);
		centerPanel.add(imagePanel);
		
		writePanel.add(writePanel_1);
		writePanel.add(writePanel_2);
		writePanel.add(writePanel_3);
		
		writePanel_1.add(titleLabel, BorderLayout.WEST);
		writePanel_1.add(titleField, BorderLayout.CENTER);
		writePanel_2.add(authorLabel, BorderLayout.WEST);
		writePanel_2.add(authorField, BorderLayout.CENTER);
		writePanel_3.add(imgBtn);
		
		
		botPanel.add(addBtn);
		botPanel.add(exitBtn);
		
		
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(botPanel, BorderLayout.SOUTH);
		
		imagePanel.setOpaque(true);
		imagePanel.setBackground(Color.LIGHT_GRAY);
		
		setSize(300, 230);
		setResizable(false);
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
				JFileChooser chooser = new JFileChooser(".");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Only JPG", "JPG");
				chooser.setFileFilter(filter);
				int ret = chooser.showOpenDialog(null);
				if(ret != JFileChooser.APPROVE_OPTION) return;
				
				targetImgFilePath = chooser.getSelectedFile().getPath();
//				targetFile = new File(targetFilePath);
//				byte[] targetContents = new byte[(int) targetFile.length()];
				imagePanel.setImage(targetImgFilePath);
			}
		});
		
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// å ���� ���
				String bookName = titleField.getText().trim();
				String bookAuthor = authorField.getText().trim();
				String bookImgName = "";
				if(!targetImgFilePath.trim().equals("")) {
					bookImgName = bookName + targetImgFilePath.substring(targetImgFilePath.length()-4);
				} 
				
//				System.out.printf("%s, %s, %d, %s", bookName, bookAuthor, bookState, rentaledByWho);
				BookDTO newBook = new BookDTO(bookName, bookAuthor, -3, null, null, bookImgName);
				if(bookDAO.checkExist(bookName)) {
					JOptionPane.showMessageDialog(null, "�̹� ��ϵ� å�Դϴ�.", "Alert", JOptionPane.WARNING_MESSAGE);
				} else {
					bookDAO.addBook(bookName, newBook);
					// å �̹��� ��� (�̹����� ���� ��)
					if(!bookImgName.equals("")) {
						bookDAO.addBookImg(bookImgName, targetImgFilePath);
					} 
					frame.bookListPanel.getList();
					JOptionPane.showMessageDialog(null, "å ��ϿϷ�", "Alert", JOptionPane.INFORMATION_MESSAGE);
				}
				
				setVisible(false);
			}
		});
		
	}
}
