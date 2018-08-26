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
	
	public JButton bookSearchBtn = new JButton("å �˻�"); // �غ���
	JPanel bookSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	public JButton rentalBtn = new JButton("�뿩");
	public JButton returnBtn = new JButton("�ݳ�");
	public JButton reserveBtn = new JButton("����");
	public JButton reserveCancleBtn = new JButton("�������");
	
	Object[][] contents = null;
	Object[] column = {"����", "����", "�뿩����"};
	DefaultTableModel model = new DefaultTableModel(contents, column);
	JTable table = new JTable(model);
	JScrollPane tablePanel = new JScrollPane(table);
	
	ImagePanel bookImgLabel = new ImagePanel(); // �̹��� �ҷ����� ��
	
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
		
		returnBtn.setEnabled(false); // ���ÿ� ��Ȱ��ȭ.. ������ å�� �뿩���� å�� ��� Ȱ��ȭ 
		reserveCancleBtn.setEnabled(false); // ���ÿ� ��Ȱ��ȭ.. ������ å�� �������� å�� ��� Ȱ��ȭ 
		
		centerPanel.setDividerLocation(300);
		centerPanel.setEnabled(false);
		
		getList();
		this.setVisible(true);
		
		
		// ========================================================================================================
		// ��ư �׼�
		
		this.rentalBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(selectedBookState == -3 || selectedBookState == -2)) {
					JOptionPane.showMessageDialog(null, "�뿩�� �Ұ����� �����Դϴ�.", "�뿩 �Ұ���", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					String sessionID = frame.getSessionID();
					BookDTO book = bookDAO.getBooks().get(selectedBookName);
					
					// ������ ������ å�� �뿩�� �� �ֽ��ϴ�. -2 �����̰�, �ش� å�� ������ ��������� ���Ǿ��̵𿩾���.
					if(!((selectedBookState == -2 && book.getReservedByWho().equals(sessionID)) || selectedBookState == -3)) {
						JOptionPane.showMessageDialog(null, "���θ� ������ �����մϴ�.", "�뿩 �Ұ���", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					int check = JOptionPane.showConfirmDialog(null, "�� ������ �뿩�Ͻðڽ��ϱ�?", "å �뿩", JOptionPane.YES_NO_OPTION);
					if(check != JOptionPane.YES_OPTION) return;
					
					MemberDTO member = memberDAO.getMembers().get(sessionID); // ���Ǿ��̵�� �ش� ���̵� ���� ������
					HashMap<String, BookDTO> inputMember;
					
					// -3 �� ��� �׳� �뿩
					if(selectedBookState == -3) {
						book.setBookState(-1);
						book.setRentaledByWho(sessionID);
						bookDAO.addBook(selectedBookName, book); // ����� å �ٽ� ��Ͽ� ��������
						
						if(member.getBooks_rentaled() == null) {
							inputMember = new HashMap<>();
							inputMember.put(selectedBookName, book);
							member.setBooks_rentaled(inputMember);
						} else {
							inputMember = member.getBooks_rentaled();
							inputMember.put(selectedBookName, book);
							member.setBooks_rentaled(inputMember);
						}
						memberDAO.addMem(sessionID, member); // �����Ͽ� �߰�
						
						System.out.println(sessionID + " ���� ( " + selectedBookName + " ) <�뿩>�ϼ̽��ϴ�. / " + new Date());
					}
					
					// ����� å�� ���
					if(selectedBookState == -2 && book.getReservedByWho().equals(sessionID)) {
						book.setBookState(-1);
						book.setRentaledByWho(sessionID);
						book.setReservedByWho(null);
						bookDAO.addBook(selectedBookName, book); // ����� å �ٽ� ��Ͽ� ��������
						
						// �뿩�� å ��Ͽ� �߰�
						if(member.getBooks_rentaled() == null) {
							inputMember = new HashMap<>();
							inputMember.put(selectedBookName, book);
							member.setBooks_rentaled(inputMember);
						} else {
							inputMember = member.getBooks_rentaled();
							inputMember.put(selectedBookName, book);
							member.setBooks_rentaled(inputMember);
						}
						// ������ å ��Ͽ��� ����
						inputMember = member.getBooks_reserved();
						inputMember.remove(selectedBookName);
						member.setBooks_reserved(inputMember);
						
						// �뿩��ϰ� ������ ����
						memberDAO.addMem(sessionID, member);
						
						System.out.println(sessionID + " ���� ( " + selectedBookName + " ) <�뿩>�ϼ̽��ϴ�. / " + new Date() + "(������ å �뿩)");
					}
					frame.bookListPanel.getList(); // å ��� ����
					frame.memRentalPanel.getList(); // �� �뿩��� ����
					frame.memReservePanel.getList(); // �� ������ ����
				}
				
				
			}
		});

		
		this.reserveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedBookState != -3) {
					JOptionPane.showMessageDialog(null, "������ �Ұ����� �����Դϴ�.", "���� �Ұ���", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int check = JOptionPane.showConfirmDialog(null, "�� ������ �����Ͻðڽ��ϱ�?", "å ����", JOptionPane.YES_NO_OPTION);
				if(check != JOptionPane.YES_OPTION) return;
				
				String sessionID = frame.getSessionID();
				
				// ���� å ���� ����
				BookDTO book = bookDAO.getBooks().get(selectedBookName); // �ش� ������ å ���� ������
				book.setBookState(-2);
				book.setReservedByWho(sessionID);
				bookDAO.addBook(selectedBookName, book); // ����� å �ٽ� ��Ͽ� ��������
				
				// ���� å -> ��Ͽ� ���� -> ����� ����
				MemberDTO member = memberDAO.getMembers().get(sessionID); // ���Ǿ��̵�� �ش� ���̵� ���� ������
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
				memberDAO.addMem(sessionID, member); // �����Ͽ� �߰�
				
				System.out.println(sessionID + " ���� ( " + selectedBookName + " ) <����>�ϼ̽��ϴ�. / " + new Date());
				frame.bookListPanel.getList(); // å ��� ����
				frame.memRentalPanel.getList(); // �� �뿩��� ����
				frame.memReservePanel.getList(); // �� ������ ����
			}
		});
		
		
		this.returnBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (selectedBookState < -1) {
					JOptionPane.showMessageDialog(null, "�߸�������", "ERROR", JOptionPane.WARNING_MESSAGE);
					return;
				}

				String sessionID = frame.getSessionID();
				BookDTO book = bookDAO.getBooks().get(selectedBookName);

				if (sessionID.equals(book.getRentaledByWho())) {
					int check = JOptionPane.showConfirmDialog(null, "�� ������ �ݳ��Ͻðڽ��ϱ�?", "å �ݳ�", JOptionPane.YES_NO_OPTION);
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
					System.out.println(sessionID + " ���� ( " + selectedBookName + " ) <�ݳ�>�ϼ̽��ϴ�. / " + new Date());
					frame.bookListPanel.getList(); // å ��� ����
					frame.memRentalPanel.getList(); // �� �뿩��� ����
					frame.memReservePanel.getList(); // �� ������ ����
				}
			}
		});
		
		
		this.reserveCancleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedBookState != -2) {
					JOptionPane.showMessageDialog(null, "�߸�������", "ERROR", JOptionPane.WARNING_MESSAGE);
					return;
				}

				String sessionID = frame.getSessionID();
				BookDTO book = bookDAO.getBooks().get(selectedBookName);

				if (sessionID.equals(book.getReservedByWho())) {
					int check = JOptionPane.showConfirmDialog(null, "�� ������ ������ ����Ͻðڽ��ϱ�?", "å �������", JOptionPane.YES_NO_OPTION);
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
					System.out.println(sessionID + " ���� ( " + selectedBookName + " ) <�������>�ϼ̽��ϴ�. / " + new Date());
					frame.bookListPanel.getList(); // å ��� ����
					frame.memRentalPanel.getList(); // �� �뿩��� ����
					frame.memReservePanel.getList(); // �� ������ ����
				}
			}
		});
		
		
		this.bookSearchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BookSearchDialog bookSearchDialog = new BookSearchDialog(frame, "å �˻�");
				bookSearchDialog.setVisible(true);
			}
		});
	}
	
	
	public void getList() {
		model.setNumRows(0); // ��� �ʱ�ȭ �� ����
		HashMap<String, BookDTO> books = bookDAO.getBooks();
		
//		ArrayList<String[]> sortArray; // ����...?
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
				input[2] = "�뿩����";
			} else if(state == -2) {
				input[2] = "������";
			} else if(state >= -1) {
				input[2] = "�뿩��";
			}
			model.addRow(input);
		}
	}
	
	class TableSelectedRow extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			returnBtn.setEnabled(false); // ���� �� �⺻�� false
			reserveCancleBtn.setEnabled(false); // ���� �� �⺻�� false
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
				// ������ ���̵�� �ش� å�� �뿩���� ��� �ݳ� ��ư Ȱ��ȭ
				if(sessionID.equals(book.getRentaledByWho())) { // sessionID �� �տ� �� ������ book.getRentaledByWho() �� null �� ��츦 ����ؼ�
					returnBtn.setEnabled(true);
				}
				// ������ ���̵�� �ش� å�� �������� ��� ������� ��ư Ȱ��ȭ
				if(sessionID.equals(book.getReservedByWho())) {
					reserveCancleBtn.setEnabled(true);
				}
			}
			
		}
	}
	
}
