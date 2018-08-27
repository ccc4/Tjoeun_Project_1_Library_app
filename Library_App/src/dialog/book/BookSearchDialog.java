package dialog.book;

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

public class BookSearchDialog extends JDialog {
//	BookDAO bookDAO;
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
	JButton rentalBtn = new JButton("대여");
	JButton reserveBtn = new JButton("예약");
//	JButton exitBtn = new JButton("취소");
	
	JPanel writePanel_1 = new JPanel(new BorderLayout());
	JPanel writePanel_2 = new JPanel(new BorderLayout());
	JPanel writePanel_3 = new JPanel(new BorderLayout());
	JPanel writePanel_4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	
	
	public BookSearchDialog(MainFrame frame, String title, BookDAO bookDAO, MemberDAO memberDAO) {
		super(frame, title, true);
		
//		this.bookDAO = bookDAO;
		
		searchPanel.add(searchField, BorderLayout.CENTER);
		searchPanel.add(searchBtn, BorderLayout.EAST);
		
		writePanel_1.add(titleLabel, BorderLayout.WEST);
		writePanel_1.add(titleField, BorderLayout.CENTER);
		writePanel_2.add(authorLabel, BorderLayout.WEST);
		writePanel_2.add(authorField, BorderLayout.CENTER);
		writePanel_3.add(stateLabel, BorderLayout.WEST);
		writePanel_3.add(stateField, BorderLayout.CENTER);
		writePanel_4.add(rentalBtn);
		writePanel_4.add(reserveBtn);
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
		rentalBtn.setEnabled(false);
		reserveBtn.setEnabled(false);
		
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
					rentalBtn.setEnabled(true);
					reserveBtn.setEnabled(true);
				} else if(selectedBookState == -2) {
					stateField.setText("예약중");
					stateField.setForeground(Color.BLUE);
					if(frame.getSessionID().equals(book.getReservedByWho())) {
						rentalBtn.setEnabled(true);
					}
				} else if(selectedBookState >= -1) {
					stateField.setText("대여중");
					stateField.setForeground(Color.RED);
				}
				
			}
		});
		
		rentalBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int check = JOptionPane.showConfirmDialog(null, "이 도서를 대여하시겠습니까?", "책 대여", JOptionPane.YES_NO_OPTION);
				if(check != JOptionPane.YES_OPTION) return;
				
				MemberDTO member = memberDAO.getMembers().get(sessionID); // 세션아이디로 해당 아이디 정보 가져옴
				HashMap<String, BookDTO> inputMember;
				
				// -3 일 경우 그냥 대여
				if(selectedBookState == -3) {
					book.setBookState(-1);
					book.setRentaledByWho(sessionID);
					bookDAO.addBook(selectedBookName, book); // 변경된 책 다시 목록에 수정삽입
					
					if(member.getBooks_rentaled() == null) {
						inputMember = new HashMap<>();
						inputMember.put(selectedBookName, book);
						member.setBooks_rentaled(inputMember);
					} else {
						inputMember = member.getBooks_rentaled();
						inputMember.put(selectedBookName, book);
						member.setBooks_rentaled(inputMember);
					}
					memberDAO.addMem(sessionID, member); // 예약목록에 추가
					
					System.out.println(sessionID + " 님이 ( " + selectedBookName + " ) <대여>하셨습니다. / " + new Date());
					
				} else if(selectedBookState == -2 && book.getReservedByWho().equals(sessionID)) {
					// 예약된 책일 경우
					book.setBookState(-1);
					book.setRentaledByWho(sessionID);
					book.setReservedByWho(null);
					bookDAO.addBook(selectedBookName, book); // 변경된 책 다시 목록에 수정삽입
					
					// 대여한 책 목록에 추가
					if(member.getBooks_rentaled() == null) {
						inputMember = new HashMap<>();
						inputMember.put(selectedBookName, book);
						member.setBooks_rentaled(inputMember);
					} else {
						inputMember = member.getBooks_rentaled();
						inputMember.put(selectedBookName, book);
						member.setBooks_rentaled(inputMember);
					}
					// 예약한 책 목록에서 제거
					inputMember = member.getBooks_reserved();
					inputMember.remove(selectedBookName);
					member.setBooks_reserved(inputMember);
					
					// 대여목록과 예약목록 갱신
					memberDAO.addMem(sessionID, member);
					
					System.out.println(sessionID + " 님이 ( " + selectedBookName + " ) <대여>하셨습니다. / " + new Date() + "(예약한 책 대여)");
				}
				JOptionPane.showMessageDialog(null, "대여가 완료되었습니다.", "대여 완료", JOptionPane.INFORMATION_MESSAGE);
				frame.bookListPanel.getList(); // 책 목록 갱신
				frame.memRentalPanel.getList(); // 내 대여목록 갱신
				frame.memReservePanel.getList(); // 내 예약목록 갱신
				setVisible(false);
			}
		});
		
		
		reserveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int check = JOptionPane.showConfirmDialog(null, "이 도서를 예약하시겠습니까?", "책 예약", JOptionPane.YES_NO_OPTION);
				if(check != JOptionPane.YES_OPTION) return;
				
				BookDTO book = bookDAO.getBooks().get(selectedBookName);
				book.setBookState(-2);
				book.setReservedByWho(sessionID);
				bookDAO.addBook(selectedBookName, book);
				
				MemberDTO member = memberDAO.getMembers().get(sessionID);
				HashMap<String, BookDTO> inputMember;
				if(member.getBooks_reserved() == null) {
					inputMember = new HashMap<>();
					inputMember.put(selectedBookName, book);
					member.setBooks_reserved(inputMember);
				} else {
					inputMember = member.getBooks_reserved();
					inputMember.put(selectedBookName, book);
					member.setBooks_reserved(inputMember);
				}
				memberDAO.addMem(sessionID, member);
				
				System.out.println(sessionID + " 님이 ( " + selectedBookName + " ) <예약>하셨습니다. / " + new Date());
				JOptionPane.showMessageDialog(null, "예약이 완료되었습니다.", "예약 완료", JOptionPane.INFORMATION_MESSAGE);
				frame.bookListPanel.getList(); 
				frame.memRentalPanel.getList();
				frame.memReservePanel.getList();
				setVisible(false);
			}
		});
		
	}
}
