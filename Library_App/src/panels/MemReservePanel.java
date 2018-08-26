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
	Object[] column = {"����", "����", "������"};
	DefaultTableModel model = new DefaultTableModel(contents, column);
	JTable table = new JTable(model);
	JScrollPane tablePanel = new JScrollPane(table);
	
	JButton rentalBtn = new JButton("�뿩");
	JButton reserveCancleBtn = new JButton("�������");
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
				int check = JOptionPane.showConfirmDialog(null, "�� ������ �뿩�Ͻðڽ��ϱ�?", "å �뿩", JOptionPane.YES_NO_OPTION);
				if (check != JOptionPane.YES_OPTION) return;
				
				String sessionID = frame.getSessionID();
				BookDTO book = bookDAO.getBooks().get(selectedBookName);

				MemberDTO member = memberDAO.getMembers().get(sessionID); // ���Ǿ��̵�� �ش� ���̵� ���� ������
				HashMap<String, BookDTO> inputMember;

				if (sessionID.equals(book.getReservedByWho())) {
					book.setBookState(-1);
					book.setRentaledByWho(sessionID);
					book.setReservedByWho(null);
					bookDAO.addBook(selectedBookName, book); // ����� å �ٽ� ��Ͽ� ��������

					// �뿩�� å ��Ͽ� �߰�
					if (member.getBooks_rentaled() == null) {
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
				rentalBtn.setEnabled(false);
				reserveCancleBtn.setEnabled(false);
				frame.bookListPanel.getList(); // å ��� ����
				frame.memRentalPanel.getList(); // �� �뿩��� ����
				frame.memReservePanel.getList(); // �� ������ ����

			}
		});
		
		
		this.reserveCancleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

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
					
					System.out.println(sessionID + " ���� ( " + selectedBookName + " ) <�������>�ϼ̽��ϴ�. / " + new Date());
					rentalBtn.setEnabled(false);
					reserveCancleBtn.setEnabled(false);
					frame.bookListPanel.getList(); // å ��� ����
					frame.memRentalPanel.getList(); // �� �뿩��� ����
					frame.memReservePanel.getList(); // �� ������ ����
				}
			}
		});
	}

	public void getList() {
		model.setNumRows(0); // ��� �ʱ�ȭ �� ����
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
			rentalBtn.setEnabled(false); // ���� �� �⺻�� false
			reserveCancleBtn.setEnabled(false); // ���� �� �⺻�� false
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
