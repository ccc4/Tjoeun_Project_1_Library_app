package panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import book.BookDAO;
import book.BookDTO;
import mainFrame.MainFrame;
import member.MemberDAO;
import member.MemberDTO;

public class MemReservePanel extends JPanel {
	private MainFrame frame;
	private BookDAO bookDAO;
	private MemberDAO memberDAO;
	private String selectedBookName;
	
	Object[][] contents = null;
	Object[] column = {"제목", "저자", "뭘쓸까"};
	DefaultTableModel model = new DefaultTableModel(contents, column);
	JTable table = new JTable(model);
	JScrollPane tablePanel = new JScrollPane(table);
	
	JButton rentalBtn = new JButton("대여");
	JButton reserveCancleBtn = new JButton("예약취소");
	JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			
	
	
	public MemReservePanel(MainFrame frame, BookDAO bookDAO, MemberDAO memberDAO) {
		this.frame = frame;
		this.bookDAO = bookDAO;
		this.memberDAO = memberDAO;
		
		this.setLayout(new BorderLayout());
		
		btnPanel.add(rentalBtn);
		btnPanel.add(reserveCancleBtn);
		this.add(tablePanel, BorderLayout.CENTER);
		this.add(btnPanel, BorderLayout.SOUTH);
		
		table.addMouseListener(new TableSelectedRow());
		
		rentalBtn.setEnabled(false);
		reserveCancleBtn.setEnabled(false);
		
		getList();
		this.setVisible(true);
		
		
		this.rentalBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int check = JOptionPane.showConfirmDialog(null, "이 도서를 대여하시겠습니까?", "책 대여", JOptionPane.YES_NO_OPTION);
				if (check != JOptionPane.YES_OPTION) return;
				
				String sessionID = frame.getSessionID();
				BookDTO book = bookDAO.getBooks().get(selectedBookName);

				MemberDTO member = memberDAO.getMembers().get(sessionID); // 세션아이디로 해당 아이디 정보 가져옴
				HashMap<String, BookDTO> inputMember;

				if (sessionID.equals(book.getReservedByWho())) {
					book.setBookState(-1);
					book.setRentaledByWho(sessionID);
					book.setReservedByWho(null);
					bookDAO.addBook(selectedBookName, book); // 변경된 책 다시 목록에 수정삽입

					// 대여한 책 목록에 추가
					if (member.getBooks_rentaled() == null) {
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
				rentalBtn.setEnabled(false);
				reserveCancleBtn.setEnabled(false);
				frame.bookListPanel.getList(); // 책 목록 갱신
				frame.memRentalPanel.getList(); // 내 대여목록 갱신
				frame.memReservePanel.getList(); // 내 예약목록 갱신

			}
		});
		
		
		this.reserveCancleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

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
					
					System.out.println(sessionID + " 님이 ( " + selectedBookName + " ) <예약취소>하셨습니다. / " + new Date());
					rentalBtn.setEnabled(false);
					reserveCancleBtn.setEnabled(false);
					frame.bookListPanel.getList(); // 책 목록 갱신
					frame.memRentalPanel.getList(); // 내 대여목록 갱신
					frame.memReservePanel.getList(); // 내 예약목록 갱신
				}
			}
		});
	}

	public void getList() {
		model.setNumRows(0); // 목록 초기화 후 갱신
		String sessionID = frame.getSessionID();
		HashMap<String, BookDTO> books;
		if(sessionID == null) {
			books = new HashMap<>();
		} else {
			books = memberDAO.getMembers().get(sessionID).getBooks_reserved();
		}
		
		Set<String> keySet = books.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			BookDTO book = books.get(key);
			
			String[] input = new String[3];
			input[0] = book.getBookName();
			input[1] = book.getBookAuthor();
			input[2] = String.valueOf(book.getBookState());
			model.addRow(input);
		}
	}
	
	public void memLogout() {
		model.setNumRows(0);
	}
	
	class TableSelectedRow extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			rentalBtn.setEnabled(false); // 누를 때 기본은 false
			reserveCancleBtn.setEnabled(false); // 누를 때 기본은 false
			selectedBookName = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
			
			String sessionID = frame.getSessionID();
			BookDTO book = bookDAO.getBooks().get(selectedBookName);
			if(sessionID != null) {
				if(sessionID.equals(book.getReservedByWho())) {
					rentalBtn.setEnabled(true);
					reserveCancleBtn.setEnabled(true);
				}
			}
			
		}
	}
}
