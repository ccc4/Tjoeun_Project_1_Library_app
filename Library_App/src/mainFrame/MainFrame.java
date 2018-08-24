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

	JLabel welcomeLabel = new JLabel("로그인 해주세요.");
	// 회원 관련
	JButton memLoginBtn = new JButton("로그인");
	JButton memJoinBtn = new JButton("회원가입");
	JButton memModifyBtn = new JButton("정보수정");
	JButton memLogoutBtn = new JButton("로그아웃");
	JPanel memLoginPanel = new JPanel(new GridLayout(2, 2));
	JPanel welcomePanel = new JPanel(new BorderLayout());
	
//	MemModifyPanel memModifyPanel;
	JPanel memTopPanel = new JPanel(new BorderLayout());
	
	JButton memRentalInfoTest = new JButton("대여정보");
//	JPanel memRentalInfoPanel = new JPanel();
	JButton memServInfoTest = new JButton("예약정보");
//	JPanel memServInfoPanel = new JPanel();
	JPanel memBottomPanel = new JPanel(new GridLayout(2, 1));
	JPanel memPanel = new JPanel(new BorderLayout());
	
	
	// 책 관련
	JButton bookSearchBtn = new JButton("책 검색");
	JPanel bookSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
//	JButton bookListTest = new JButton("책리스트");
	BookListPanel bookListPanel;
//	JButton bookInfoTest = new JButton("도서정보");
//	JPanel bookInfoPanel = new JPanel();
	JPanel bookListInfoPanel = new JPanel(new GridLayout(2, 1));
	
	JPanel bookPanel = new JPanel(new BorderLayout());
	
	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
	// -------------------------------------------------------------------
	// 관리자 메뉴바
	JMenuBar menubar = new  JMenuBar();
	JMenu optionMenu = new JMenu("Option");
	JMenuItem adminLogin = new JMenuItem("관리자 로그인");
	JMenu adminMenu = new JMenu("Admin Menu");
	JMenuItem adminLogout = new JMenuItem("관리자 로그아웃");
	JMenu bookMenu = new JMenu("책 관리");
	JMenuItem listBook = new JMenuItem("책 목록");
	JMenuItem addBook = new JMenuItem("책 등록");
	JMenuItem modBook = new JMenuItem("책 수정");
	JMenuItem delBook = new JMenuItem("책 삭제");
	JMenu memMenu = new JMenu("회원 관리");
	JMenuItem listMem = new JMenuItem("회원 목록");
	JMenuItem addMem = new JMenuItem("회원 등록");
	JMenuItem modMem = new JMenuItem("회원 수정");
	JMenuItem delMem = new JMenuItem("회원 삭제");
	
	
	// -------------------------------------------------------------------
	// 메인 필드
	String sessionID;
	String sessionAdmin;
	
	MemberDAO memberDAO;
	HashMap<String, MemberDTO> membersMap;
	
	BookDAO bookDAO;
//	HashMap<String, BookDTO> booksMap; // 안쓰인다
	
	// -------------------------------------------------------------------
	
	public MainFrame() {
		super("도서관 애플리케이션");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		memberDAO = new MemberDAO();
		bookDAO = new BookDAO();
		
		// --------------------------------------------------------
		// 메뉴바 구현
		
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
		
		// 회원 관리 리스너
		
		this.memJoinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemJoinDialog memJoinDialog = new MemJoinDialog(frame, "회원가입");
				memJoinDialog.setMembers(memberDAO);
				memJoinDialog.setVisible(true);
				//모달로 설정
				
			}
		});
		
		this.memLoginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemLoginDialog memLoginDialog = new MemLoginDialog(frame, "로그인");
				memLoginDialog.setMembers(memberDAO);
//				memLoginDialog.setMembersMap(membersMap);
//				memLoginDialog.setMemModifyPanel(memModifyPanel);
				memLoginDialog.setVisible(true);
			}
		});
		
		this.memModifyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemModifyDialog memModifyDialog = new MemModifyDialog(frame, "회원정보수정", memberDAO, memberDAO.getMembers().get(sessionID));
				memModifyDialog.setVisible(true);
			}
		});
		
		this.memLogoutBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int check = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "Logout Confirm", JOptionPane.YES_NO_OPTION);
				if(!(check == JOptionPane.YES_OPTION)) return;
				member_Logout();
				check_Member_Login();
				membersMap = null;
//				memModifyPanel.loginShowProfile("", "", "", 0, "", "");
			}
		});
		
		// --------------------------------------------------------------
		// 관리자 리스너
		
		this.adminLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminLoginDialog adminLoginDialog = new AdminLoginDialog(frame, "관리자 로그인");
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
		// 책 관리 리스너
		
		this.addBook.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BookAddDialog bookAddDialog = new BookAddDialog(frame, "책 등록");
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
			welcomeLabel.setText(sessionID + " 님 환영합니다."); 
		} else {
			memLoginBtn.setEnabled(true);
			memJoinBtn.setEnabled(true);
			memModifyBtn.setEnabled(false);
			memLogoutBtn.setEnabled(false);
			bookSearchBtn.setEnabled(false);
			welcomeLabel.setText("로그인 해주세요.");
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
