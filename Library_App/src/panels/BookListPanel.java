package panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import book.BookDAO;
import book.BookDTO;
import dialog.book.BookSearchDialog;
import mainFrame.MainFrame;
import member.MemberDAO;
import member.MemberDTO;

public class BookListPanel extends JPanel {
	private MainFrame frame;
	private BookDAO bookDAO;
	private MemberDAO memberDAO;
	private String selectedBookName;
	private int selectedBookState;
	
	JSplitPane centerPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	public JButton bookSearchBtn = new JButton("책 검색"); // 준비중
	JPanel bookSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	public JButton rentalBtn = new JButton("대여");
	public JButton returnBtn = new JButton("반납");
	public JButton reserveBtn = new JButton("예약");
	public JButton reserveCancleBtn = new JButton("예약취소");
	
	Object[][] contents = null;
	Object[] column = {"제목", "저자", "대여여부"};
	DefaultTableModel model = new DefaultTableModel(contents, column);
	JTable table = new JTable(model);
	JScrollPane tablePanel = new JScrollPane(table);
	
	ImagePanel bookImgLabel = new ImagePanel(); // 이미지 불러오는 곳
	
	public BookListPanel(MainFrame frame, BookDAO bookDAO, MemberDAO memberDAO) {
		this.frame = frame;
		this.bookDAO = bookDAO;
		this.memberDAO = memberDAO;
		
		this.setLayout(new BorderLayout());
		
		bookSearchPanel.add(bookSearchBtn);
		
		botPanel.add(rentalBtn);
		botPanel.add(returnBtn);
		botPanel.add(reserveBtn);
		botPanel.add(reserveCancleBtn);
		
		centerPanel.add(tablePanel);
		centerPanel.add(bookImgLabel);
		
		this.add(bookSearchPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(botPanel, BorderLayout.SOUTH);
		
		table.addMouseListener(new TableSelectedRow());
		
		returnBtn.setEnabled(false); // 평상시엔 비활성화.. 선택한 책이 대여중일 책일 경우 활성화 
		reserveCancleBtn.setEnabled(false); // 평상시엔 비활성화.. 선택한 책이 예약중일 책일 경우 활성화 
		
		centerPanel.setDividerLocation(300);
		centerPanel.setEnabled(false);
		
		getList();
		this.setVisible(true);
		
		
		// ========================================================================================================
		// 버튼 액션
		
		this.rentalBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(selectedBookState == -3 || selectedBookState == -2)) {
					JOptionPane.showMessageDialog(null, "대여가 불가능한 상태입니다.", "대여 불가능", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					String sessionID = frame.getSessionID();
					BookDTO book = bookDAO.getBooks().get(selectedBookName);
					
					// 본인이 예약한 책만 대여할 수 있습니다. -2 상태이고, 해당 책의 예약한 사람정보가 세션아이디여야함.
					if(!((selectedBookState == -2 && book.getReservedByWho().equals(sessionID)) || selectedBookState == -3)) {
						JOptionPane.showMessageDialog(null, "본인만 예약이 가능합니다.", "대여 불가능", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
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
					}
					
					// 예약된 책일 경우
					if(selectedBookState == -2 && book.getReservedByWho().equals(sessionID)) {
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
					frame.bookListPanel.getList(); // 책 목록 갱신
					frame.memRentalPanel.getList(); // 내 대여목록 갱신
					frame.memReservePanel.getList(); // 내 예약목록 갱신
				}
				
				
			}
		});

		
		this.reserveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedBookState != -3) {
					JOptionPane.showMessageDialog(null, "예약이 불가능한 상태입니다.", "예약 불가능", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int check = JOptionPane.showConfirmDialog(null, "이 도서를 예약하시겠습니까?", "책 예약", JOptionPane.YES_NO_OPTION);
				if(check != JOptionPane.YES_OPTION) return;
				
				String sessionID = frame.getSessionID();
				
				// 빌린 책 상태 변경
				BookDTO book = bookDAO.getBooks().get(selectedBookName); // 해당 제목의 책 정보 가져옴
				book.setBookState(-2);
				book.setReservedByWho(sessionID);
				bookDAO.addBook(selectedBookName, book); // 변경된 책 다시 목록에 수정삽입
				
				// 빌린 책 -> 목록에 저장 -> 멤버에 저장
				MemberDTO member = memberDAO.getMembers().get(sessionID); // 세션아이디로 해당 아이디 정보 가져옴
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
				memberDAO.addMem(sessionID, member); // 예약목록에 추가
				
				System.out.println(sessionID + " 님이 ( " + selectedBookName + " ) <예약>하셨습니다. / " + new Date());
				frame.bookListPanel.getList(); // 책 목록 갱신
				frame.memRentalPanel.getList(); // 내 대여목록 갱신
				frame.memReservePanel.getList(); // 내 예약목록 갱신
			}
		});
		
		
		this.returnBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (selectedBookState < -1) {
					JOptionPane.showMessageDialog(null, "잘못된접근", "ERROR", JOptionPane.WARNING_MESSAGE);
					return;
				}

				String sessionID = frame.getSessionID();
				BookDTO book = bookDAO.getBooks().get(selectedBookName);

				if (sessionID.equals(book.getRentaledByWho())) {
					int check = JOptionPane.showConfirmDialog(null, "이 도서를 반납하시겠습니까?", "책 반납", JOptionPane.YES_NO_OPTION);
					if (check != JOptionPane.YES_OPTION) return;

					book.setBookState(-3);
					book.setRentaledByWho(null);
					bookDAO.addBook(selectedBookName, book);
					
					MemberDTO member = memberDAO.getMembers().get(sessionID);
					HashMap<String, BookDTO> inputMember;
					inputMember = member.getBooks_rentaled();
					inputMember.remove(selectedBookName);
					member.setBooks_rentaled(inputMember);
					memberDAO.addMem(sessionID, member);
					
					returnBtn.setEnabled(false);
					System.out.println(sessionID + " 님이 ( " + selectedBookName + " ) <반납>하셨습니다. / " + new Date());
					frame.bookListPanel.getList(); // 책 목록 갱신
					frame.memRentalPanel.getList(); // 내 대여목록 갱신
					frame.memReservePanel.getList(); // 내 예약목록 갱신
				}
			}
		});
		
		
		this.reserveCancleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedBookState != -2) {
					JOptionPane.showMessageDialog(null, "잘못된접근", "ERROR", JOptionPane.WARNING_MESSAGE);
					return;
				}

				String sessionID = frame.getSessionID();
				BookDTO book = bookDAO.getBooks().get(selectedBookName);

				if (sessionID.equals(book.getReservedByWho())) {
					int check = JOptionPane.showConfirmDialog(null, "이 도서의 예약을 취소하시겠습니까?", "책 예약취소", JOptionPane.YES_NO_OPTION);
					if (check != JOptionPane.YES_OPTION) return;

					book.setBookState(-3);
					book.setReservedByWho(null);
					bookDAO.addBook(selectedBookName, book);
					
					MemberDTO member = memberDAO.getMembers().get(sessionID);
					HashMap<String, BookDTO> inputMember;
					inputMember = member.getBooks_reserved();
					inputMember.remove(selectedBookName);
					member.setBooks_reserved(inputMember);
					memberDAO.addMem(sessionID, member);
					
					reserveCancleBtn.setEnabled(false);
					System.out.println(sessionID + " 님이 ( " + selectedBookName + " ) <예약취소>하셨습니다. / " + new Date());
					frame.bookListPanel.getList(); // 책 목록 갱신
					frame.memRentalPanel.getList(); // 내 대여목록 갱신
					frame.memReservePanel.getList(); // 내 예약목록 갱신
				}
			}
		});
		
		
		this.bookSearchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BookSearchDialog bookSearchDialog = new BookSearchDialog(frame, "책 검색");
				bookSearchDialog.setVisible(true);
			}
		});
	}
	
	
	public void getList() {
		model.setNumRows(0); // 목록 초기화 후 갱신
		HashMap<String, BookDTO> books = bookDAO.getBooks();
		
//		ArrayList<String[]> sortArray; // 정렬...?
		Set<String> keySet = books.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			BookDTO book = books.get(key);
			
			String[] input = new String[3];
			input[0] = book.getBookName();
			input[1] = book.getBookAuthor();
			int state = book.getBookState();
			if(state == -3) {
				input[2] = "대여가능";
			} else if(state == -2) {
				input[2] = "예약중";
			} else if(state >= -1) {
				input[2] = "대여중";
			}
			model.addRow(input);
		}
	}
	
	class TableSelectedRow extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			returnBtn.setEnabled(false); // 누를 때 기본은 false
			reserveCancleBtn.setEnabled(false); // 누를 때 기본은 false
			selectedBookName = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
			selectedBookState = bookDAO.getBooks().get(selectedBookName).getBookState();
//			System.out.println(selectedBookName);
//			System.out.println(selectedBookState);
			String bookImgName = bookDAO.getBooks().get(selectedBookName).getBookImgName();
			if(bookImgName.trim().length() == 0) {
//				System.out.println("null");
				bookImgLabel.setImage(".\\init\\not_Exist.jpg");
//				bookImgLabel.setImage(bookImgName);
			} else {
//				System.out.println("exist");
//				System.out.println(bookImgName);
//				bookImgLabel.setImage(bookImgName);
				bookImgLabel.setImage(".\\init\\11.jpg");
			}
			
			String sessionID = frame.getSessionID();
			BookDTO book = bookDAO.getBooks().get(selectedBookName);
			if(sessionID != null) {
				// 접속한 아이디로 해당 책이 대여중일 경우 반납 버튼 활성화
				if(sessionID.equals(book.getRentaledByWho())) { // sessionID 를 앞에 쓴 이유는 book.getRentaledByWho() 가 null 일 경우를 대비해서
					returnBtn.setEnabled(true);
				}
				// 접속한 아이디로 해당 책이 예약중일 경우 예약취소 버튼 활성화
				if(sessionID.equals(book.getReservedByWho())) {
					reserveCancleBtn.setEnabled(true);
				}
			}
			
		}
	}
	
}
