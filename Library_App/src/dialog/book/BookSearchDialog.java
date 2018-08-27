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
	JButton searchBtn = new JButton("�˻�");
	JPanel searchPanel = new JPanel(new BorderLayout());
	
	JPanel contentPanel = new JPanel(new BorderLayout());
	JPanel botPanel = new JPanel(new GridLayout(4, 1));
	ImagePanel imagePanel; // �̹����г�
	
	JLabel titleLabel = new JLabel("  ����  ");
	JTextField titleField = new JTextField();
	JLabel authorLabel = new JLabel("  ����  ");
	JTextField authorField = new JTextField();
	JLabel stateLabel = new JLabel("  ����  ");
	JTextField stateField = new JTextField();
	JButton rentalBtn = new JButton("�뿩");
	JButton reserveBtn = new JButton("����");
//	JButton exitBtn = new JButton("���");
	
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
					JOptionPane.showMessageDialog(null, "�ش� �̸��� å�� �������� �ʽ��ϴ�", "����", JOptionPane.INFORMATION_MESSAGE);
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
					stateField.setText("�뿩����");
					stateField.setForeground(Color.BLACK);
					rentalBtn.setEnabled(true);
					reserveBtn.setEnabled(true);
				} else if(selectedBookState == -2) {
					stateField.setText("������");
					stateField.setForeground(Color.BLUE);
					if(frame.getSessionID().equals(book.getReservedByWho())) {
						rentalBtn.setEnabled(true);
					}
				} else if(selectedBookState >= -1) {
					stateField.setText("�뿩��");
					stateField.setForeground(Color.RED);
				}
				
			}
		});
		
		rentalBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
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
					
				} else if(selectedBookState == -2 && book.getReservedByWho().equals(sessionID)) {
					// ����� å�� ���
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
				JOptionPane.showMessageDialog(null, "�뿩�� �Ϸ�Ǿ����ϴ�.", "�뿩 �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
				frame.bookListPanel.getList(); // å ��� ����
				frame.memRentalPanel.getList(); // �� �뿩��� ����
				frame.memReservePanel.getList(); // �� ������ ����
				setVisible(false);
			}
		});
		
		
		reserveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int check = JOptionPane.showConfirmDialog(null, "�� ������ �����Ͻðڽ��ϱ�?", "å ����", JOptionPane.YES_NO_OPTION);
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
				
				System.out.println(sessionID + " ���� ( " + selectedBookName + " ) <����>�ϼ̽��ϴ�. / " + new Date());
				JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.", "���� �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
				frame.bookListPanel.getList(); 
				frame.memRentalPanel.getList();
				frame.memReservePanel.getList();
				setVisible(false);
			}
		});
		
	}
}
