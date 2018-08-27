package dialog.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import book.BookDAO;
import book.BookDTO;
import mainFrame.MainFrame;
import member.MemberDAO;
import member.MemberDTO;
import panels.ImagePanel;

public class BookDeleteDialog extends JDialog {
	BookDTO book;
	String sessionID;
	String selectedBookName;
	int selectedBookState;
	
	
	JTextField searchField = new JTextField();
	JButton searchBtn = new JButton("검색");
	JPanel searchPanel = new JPanel(new BorderLayout());
	
	JPanel contentPanel = new JPanel(new BorderLayout());
	JPanel botPanel = new JPanel(new GridLayout(4, 1));
	ImagePanel imagePanel; // 이미지패널
	
	JLabel titleLabel = new JLabel("  제목  ");
	JTextField titleField = new JTextField();
	JLabel authorLabel = new JLabel("  저자  ");
	JTextField authorField = new JTextField();
	JLabel stateLabel = new JLabel("  상태  ");
	JTextField stateField = new JTextField();
	JButton delBtn = new JButton("삭제");
	
	JPanel writePanel_1 = new JPanel(new BorderLayout());
	JPanel writePanel_2 = new JPanel(new BorderLayout());
	JPanel writePanel_3 = new JPanel(new BorderLayout());
	JPanel writePanel_4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	
	
	public BookDeleteDialog(MainFrame frame, String title, BookDAO bookDAO) {
		super(frame, title, true);
		
		searchPanel.add(searchField, BorderLayout.CENTER);
		searchPanel.add(searchBtn, BorderLayout.EAST);
		
		writePanel_1.add(titleLabel, BorderLayout.WEST);
		writePanel_1.add(titleField, BorderLayout.CENTER);
		writePanel_2.add(authorLabel, BorderLayout.WEST);
		writePanel_2.add(authorField, BorderLayout.CENTER);
		writePanel_3.add(stateLabel, BorderLayout.WEST);
		writePanel_3.add(stateField, BorderLayout.CENTER);
		writePanel_4.add(delBtn);
		botPanel.add(writePanel_1);
		botPanel.add(writePanel_2);
		botPanel.add(writePanel_3);
		botPanel.add(writePanel_4);
		
		imagePanel = new ImagePanel();
		
		contentPanel.add(searchPanel, BorderLayout.NORTH);
		contentPanel.add(imagePanel, BorderLayout.CENTER);
		contentPanel.add(botPanel, BorderLayout.SOUTH);
		
		this.add(contentPanel);
		
		titleField.setHorizontalAlignment(SwingConstants.CENTER);
		authorField.setHorizontalAlignment(SwingConstants.CENTER);
		stateField.setHorizontalAlignment(SwingConstants.CENTER);
		
		imagePanel.setOpaque(true);
		imagePanel.setBackground(Color.LIGHT_GRAY);
		titleField.setEditable(false);
		authorField.setEditable(false);
		stateField.setEditable(false);
		delBtn.setEnabled(false);
		
		setSize(100, 480);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		
		
		searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedBookName = searchField.getText();
				if(!bookDAO.getBooks().containsKey(selectedBookName)) {
					JOptionPane.showMessageDialog(null, "해당 이름의 책은 존재하지 않습니다", "오류", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				sessionID = frame.getSessionID();
				book = bookDAO.getBooks().get(selectedBookName);
				
				String bookImgName = book.getBookImgName();
				if(bookImgName.trim().length() == 0) {
					imagePanel.setNoImage();
				} else {
					imagePanel.setSaveImage(bookImgName);
				}
				
				titleField.setText(book.getBookName());
				authorField.setText(book.getBookAuthor());
				selectedBookState = book.getBookState();
				if(selectedBookState == -3) {
					stateField.setText("대여가능");
					stateField.setForeground(Color.BLACK);
					delBtn.setEnabled(true);
				} else if(selectedBookState == -2) {
					stateField.setText("예약중");
					stateField.setForeground(Color.BLUE);
				} else if(selectedBookState >= -1) {
					stateField.setText("대여중");
					stateField.setForeground(Color.RED);
				}
				
			}
		});
		
		delBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int check = JOptionPane.showConfirmDialog(null, "이 도서를 삭제하시겠습니까?", "책 삭제", JOptionPane.YES_NO_OPTION);
				if(check != JOptionPane.YES_OPTION) return;
				
//				BookDTO book = bookDAO.getBooks().get(selectedBookName);
				HashMap<String, BookDTO> books = bookDAO.getBooks();
				books.remove(selectedBookName);
				bookDAO.saveBooksToFile(books);
				
				System.out.println(" ( " + selectedBookName + " ) 삭제완료.");
				JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.", "삭제 완료", JOptionPane.INFORMATION_MESSAGE);
				frame.bookListPanel.getList(); 
				frame.memRentalPanel.getList();
				frame.memReservePanel.getList();
				setVisible(false);
			}
		});
		
	}
}
