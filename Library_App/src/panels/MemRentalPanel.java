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
import panels.BookListPanel.TableSelectedRow;

public class MemRentalPanel extends JPanel {
	private MainFrame frame;
	private BookDAO bookDAO;
	private MemberDAO memberDAO;
	private String selectedBookName;
	
	Object[][] contents = null;
	Object[] column = {"����", "����", "�ݳ���"};
	DefaultTableModel model = new DefaultTableModel(contents, column);
	JTable table = new JTable(model);
	JScrollPane tablePanel = new JScrollPane(table);
	
	JButton returnBtn = new JButton("�ݳ�");
	JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			
	
	
	public MemRentalPanel(MainFrame frame, BookDAO bookDAO, MemberDAO memberDAO) {
		this.frame = frame;
		this.bookDAO = bookDAO;
		this.memberDAO = memberDAO;
		
		this.setLayout(new BorderLayout());
		
		btnPanel.add(returnBtn);
		this.add(tablePanel, BorderLayout.CENTER);
		this.add(btnPanel, BorderLayout.SOUTH);
		
		table.addMouseListener(new TableSelectedRow());
		
		returnBtn.setEnabled(false);
		
		getList();
		this.setVisible(true);
		
		
		this.returnBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

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
					
					System.out.println(sessionID + " ���� ( " + selectedBookName + " ) <�ݳ�>�ϼ̽��ϴ�. / " + new Date());
					returnBtn.setEnabled(false);
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
			books = memberDAO.getMembers().get(sessionID).getBooks_rentaled();
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
			returnBtn.setEnabled(false); // ���� �� �⺻�� false
			selectedBookName = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
			
			String sessionID = frame.getSessionID();
			BookDTO book = bookDAO.getBooks().get(selectedBookName);
			if(sessionID != null) {
				if(sessionID.equals(book.getRentaledByWho())) {
					returnBtn.setEnabled(true);
				}
			}
			
		}
	}
}
