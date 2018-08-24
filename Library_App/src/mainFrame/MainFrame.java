package mainFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import book.BookDAO;
import book.BookDTO;
import book.BookList;
import dialog.admin.AdminLoginDialog;
import dialog.book.BookAddDialog;
import dialog.member.MemJoinDialog;
import dialog.member.MemLoginDialog;
import dialog.member.MemModifyDialog;
import member.MemberDTO;
import member.MemberList;
import panels.BookListPanel;
import member.MemberDAO;

public class MainFrame extends JFrame {

	JLabel welcomeLabel = new JLabel("�α��� ���ּ���.");
	// ȸ�� ����
	JButton memLoginBtn = new JButton("�α���");
	JButton memJoinBtn = new JButton("ȸ������");
	JButton memModifyBtn = new JButton("��������");
	JButton memLogoutBtn = new JButton("�α׾ƿ�");
	JPanel memLoginPanel = new JPanel(new GridLayout(2, 2));
	JPanel welcomePanel = new JPanel(new BorderLayout());
	
//	MemModifyPanel memModifyPanel;
	JPanel memTopPanel = new JPanel(new BorderLayout());
	
	JButton memRentalInfoTest = new JButton("�뿩����");
//	JPanel memRentalInfoPanel = new JPanel();
	JButton memServInfoTest = new JButton("��������");
//	JPanel memServInfoPanel = new JPanel();
	JPanel memBottomPanel = new JPanel(new GridLayout(2, 1));
	JPanel memPanel = new JPanel(new BorderLayout());
	
	
	// å ����
	JButton bookSearchBtn = new JButton("å �˻�");
	JPanel bookSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
//	JButton bookListTest = new JButton("å����Ʈ");
	BookListPanel bookListPanel;
//	JButton bookInfoTest = new JButton("��������");
//	JPanel bookInfoPanel = new JPanel();
	JPanel bookListInfoPanel = new JPanel(new GridLayout(2, 1));
	
	JPanel bookPanel = new JPanel(new BorderLayout());
	
	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
	// -------------------------------------------------------------------
	// ������ �޴���
	JMenuBar menubar = new  JMenuBar();
	JMenu optionMenu = new JMenu("Option");
	JMenuItem adminLogin = new JMenuItem("������ �α���");
	JMenu adminMenu = new JMenu("Admin Menu");
	JMenuItem adminLogout = new JMenuItem("������ �α׾ƿ�");
	JMenu bookMenu = new JMenu("å ����");
	JMenuItem listBook = new JMenuItem("å ���");
	JMenuItem addBook = new JMenuItem("å ���");
	JMenuItem modBook = new JMenuItem("å ����");
	JMenuItem delBook = new JMenuItem("å ����");
	JMenu memMenu = new JMenu("ȸ�� ����");
	JMenuItem listMem = new JMenuItem("ȸ�� ���");
	JMenuItem addMem = new JMenuItem("ȸ�� ���");
	JMenuItem modMem = new JMenuItem("ȸ�� ����");
	JMenuItem delMem = new JMenuItem("ȸ�� ����");
	
	
	// -------------------------------------------------------------------
	// ���� �ʵ�
	String sessionID;
	String sessionAdmin;
	
	MemberDAO memberDAO;
	HashMap<String, MemberDTO> membersMap;
	
	BookDAO bookDAO;
//	HashMap<String, BookDTO> booksMap; // �Ⱦ��δ�
	
	// -------------------------------------------------------------------
	
	public MainFrame() {
		super("������ ���ø����̼�");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		memberDAO = new MemberDAO();
		bookDAO = new BookDAO();
		
		// --------------------------------------------------------
		// �޴��� ����
		
		optionMenu.add(adminLogin);
		menubar.add(optionMenu);
		adminMenu.add(adminLogout);
		adminMenu.add(bookMenu);
		bookMenu.add(listBook);
		bookMenu.addSeparator();
		bookMenu.add(addBook);
		bookMenu.add(modBook);
		bookMenu.add(delBook);
		adminMenu.add(memMenu);
		memMenu.add(listMem);
		memMenu.addSeparator();
		memMenu.add(addMem);
		memMenu.add(modMem);
		memMenu.add(delMem);
		menubar.add(adminMenu);
		
		this.setJMenuBar(menubar);
		
		
		// --------------------------------------------------------
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		memLoginPanel.add(memLoginBtn);
		memLoginPanel.add(memJoinBtn);
		memLoginPanel.add(memModifyBtn);
		memLoginPanel.add(memLogoutBtn);
		
		welcomePanel.add(welcomeLabel, BorderLayout.NORTH);
		welcomePanel.add(memLoginPanel, BorderLayout.CENTER);
		
//		memPanel.add(memModifyPanel);
		memPanel.add(welcomePanel, BorderLayout.NORTH);
		memBottomPanel.add(memRentalInfoTest);
		memBottomPanel.add(memServInfoTest);
		memPanel.add(memBottomPanel, BorderLayout.CENTER);
		
		
		bookSearchPanel.add(bookSearchBtn);
		
		bookListPanel = new BookListPanel();
//		bookListInfoPanel.add(bookListPanel);
//		bookListInfoPanel.add(bookInfoTest);
		
		bookPanel.add(bookSearchPanel, BorderLayout.NORTH);
		bookPanel.add(bookListPanel, BorderLayout.CENTER);
		
		
		splitPane.add(bookPanel);
		splitPane.add(memPanel);
		
		add(splitPane);
		splitPane.setDividerLocation(500);
		splitPane.setEnabled(false);
		
		
		// --------------------------------------------------------
		
		check_Member_Login();
		check_Admin_Login();
		generateEvents(this);
		
		this.setSize(700, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	
	
	
	
	
	public void generateEvents(MainFrame frame) {
		
		// ȸ�� ���� ������
		
		this.memJoinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemJoinDialog memJoinDialog = new MemJoinDialog(frame, "ȸ������");
				memJoinDialog.setMembers(memberDAO);
				memJoinDialog.setVisible(true);
				//��޷� ����
				
			}
		});
		
		this.memLoginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemLoginDialog memLoginDialog = new MemLoginDialog(frame, "�α���");
				memLoginDialog.setMembers(memberDAO);
//				memLoginDialog.setMembersMap(membersMap);
//				memLoginDialog.setMemModifyPanel(memModifyPanel);
				memLoginDialog.setVisible(true);
			}
		});
		
		this.memModifyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemModifyDialog memModifyDialog = new MemModifyDialog(frame, "ȸ����������", memberDAO, memberDAO.getMembers().get(sessionID));
				memModifyDialog.setVisible(true);
			}
		});
		
		this.memLogoutBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int check = JOptionPane.showConfirmDialog(null, "�α׾ƿ� �Ͻðڽ��ϱ�?", "Logout Confirm", JOptionPane.YES_NO_OPTION);
				if(!(check == JOptionPane.YES_OPTION)) return;
				member_Logout();
				check_Member_Login();
				membersMap = null;
//				memModifyPanel.loginShowProfile("", "", "", 0, "", "");
			}
		});
		
		// --------------------------------------------------------------
		// ������ ������
		
		this.adminLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminLoginDialog adminLoginDialog = new AdminLoginDialog(frame, "������ �α���");
				adminLoginDialog.setVisible(true);
				
			}
		});
		
		this.adminLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				admin_Logout();
				check_Admin_Login();
			}
		});
		
		this.listMem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MemberList();
			}
		});
		
		// --------------------------------------------------------------
		// å ���� ������
		
		this.addBook.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BookAddDialog bookAddDialog = new BookAddDialog(frame, "å ���");
				bookAddDialog.setBookDAO(bookDAO);
				bookAddDialog.setVisible(true);
			}
		});
		
		this.listBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new BookList();
			}
		});
	}
	

	
	
	public void admin_Login() {
		this.sessionAdmin = "T";
	}

	public void admin_Logout() {
		this.sessionAdmin = null;
	}

	public void member_Login_Success(String id) {
		this.sessionID = id;
	}
	
	public void member_Logout() {
		this.sessionID = null;
	}
	
	public void check_Member_Login() {
		if(this.sessionID != null) {
			memLoginBtn.setEnabled(false);
			memJoinBtn.setEnabled(false);
			memModifyBtn.setEnabled(true);
			memLogoutBtn.setEnabled(true);
			bookSearchBtn.setEnabled(true);
			welcomeLabel.setText(sessionID + " �� ȯ���մϴ�."); 
		} else {
			memLoginBtn.setEnabled(true);
			memJoinBtn.setEnabled(true);
			memModifyBtn.setEnabled(false);
			memLogoutBtn.setEnabled(false);
			bookSearchBtn.setEnabled(false);
			welcomeLabel.setText("�α��� ���ּ���.");
		}
	}
	
	public void check_Admin_Login() {
		if(this.sessionAdmin != null) {
			adminMenu.setVisible(true);
			adminLogin.setEnabled(false);
		} else {
//			adminMenu.setVisible(false);
			adminMenu.setVisible(true);
			adminLogin.setEnabled(true);
		}
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
