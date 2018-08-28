package mainFrame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import book.BookDAO;
import dialog.member.MemJoinDialog;
import dialog.member.MemLoginDialog;
import dialog.member.MemModifyDialog;
import member.MemberDAO;
import member.MemberDTO;
import panels.BookListPanel;
import panels.MemRentalPanel;
import panels.MemReservePanel;

public class MainFrame extends JFrame {

	JLabel welcomeLabel = new JLabel("로그인 해주세요.");
	// 회원 관련
	JButton memLoginBtn = new JButton("로그인");
	JButton memJoinBtn = new JButton("회원가입");
	JButton memModifyBtn = new JButton("정보수정");
	JButton memLogoutBtn = new JButton("로그아웃");
	JPanel memLoginPanel = new JPanel(new GridLayout(2, 2));
	JPanel welcomePanel = new JPanel(new BorderLayout());
	
	JPanel memTopPanel = new JPanel(new BorderLayout());
	
	public MemRentalPanel memRentalPanel; 	// 대여정보
	public MemReservePanel memReservePanel; // 예약정보
	JPanel memBottomPanel = new JPanel(new GridLayout(2, 1));
	JPanel memPanel = new JPanel(new BorderLayout());
	
	public BookListPanel bookListPanel;
	JPanel bookListInfoPanel = new JPanel(new GridLayout(2, 1));
	
	JPanel bookPanel = new JPanel(new BorderLayout());
	
	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
	// -------------------------------------------------------------------
	// 관리자 메뉴바
	public MainMenuBar menubar = new MainMenuBar(this);
	
	// -------------------------------------------------------------------
	// 메인 필드
	String sessionID;
	public String getSessionID() {
		return sessionID;
	}

	String sessionAdmin;
	
	MemberDAO memberDAO;
	HashMap<String, MemberDTO> membersMap;
	
	BookDAO bookDAO;
//	HashMap<String, BookDTO> booksMap; // 안쓰면 삭제
	
	// -------------------------------------------------------------------
	
	public MainFrame() {
		super("도서관 애플리케이션");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		memberDAO = new MemberDAO();
		bookDAO = new BookDAO();
		
		this.setJMenuBar(menubar);
		
		
		// --------------------------------------------------------
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		memLoginPanel.add(memLoginBtn);
		memLoginPanel.add(memJoinBtn);
		memLoginPanel.add(memModifyBtn);
		memLoginPanel.add(memLogoutBtn);
		
		welcomePanel.add(welcomeLabel, BorderLayout.NORTH);
		welcomePanel.add(memLoginPanel, BorderLayout.CENTER);
		
		memPanel.add(welcomePanel, BorderLayout.NORTH);
		memRentalPanel = new MemRentalPanel(this, bookDAO, memberDAO);
		memReservePanel = new MemReservePanel(this, bookDAO, memberDAO);
		
		memBottomPanel.add(memRentalPanel);
		memBottomPanel.add(memReservePanel);
		memPanel.add(memBottomPanel, BorderLayout.CENTER);
		
		bookListPanel = new BookListPanel(this, bookDAO, memberDAO);
		
		
		splitPane.add(bookListPanel);
		splitPane.add(memPanel);
		
		
		add(splitPane);
		
//		splitPane.setDividerLocation(500);
		splitPane.setResizeWeight(.9);
		splitPane.setEnabled(false);
		
		// --------------------------------------------------------
		
		check_Member_Login();
		check_Admin_Login();
		generateEvents(this);
		
		this.setSize(700, 400);
//		this.setResizable(false);
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
			}
		});
		
		this.memLoginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemLoginDialog memLoginDialog = new MemLoginDialog(frame, "로그인");
				memLoginDialog.setMembers(memberDAO);
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
			}
		});
		
	}
	

	
	
	

	public void member_Login_Success(String id) {
		this.sessionID = id;
		check_Member_Login();
	}
	
	public void member_Logout() {
		this.sessionID = null;
		check_Member_Login();
	}
	
	public void check_Member_Login() {
		if(this.sessionID != null) {
			memLoginBtn.setEnabled(false);
			memJoinBtn.setEnabled(false);
			memModifyBtn.setEnabled(true);
			memLogoutBtn.setEnabled(true);
			welcomeLabel.setText(sessionID + " 님 환영합니다."); 
			
			menubar.memberMenu.setVisible(true);
			
			bookListPanel.bookSearchBtn.setEnabled(true);
			bookListPanel.rentalBtn.setEnabled(true);
			bookListPanel.reserveBtn.setEnabled(true);
		} else {
			memLoginBtn.setEnabled(true);
			memJoinBtn.setEnabled(true);
			memModifyBtn.setEnabled(false);
			memLogoutBtn.setEnabled(false);
			membersMap = null;
			welcomeLabel.setText("로그인 해주세요.");
			
			menubar.memberMenu.setVisible(false);
			
			bookListPanel.bookSearchBtn.setEnabled(false);
			bookListPanel.rentalBtn.setEnabled(false);
			bookListPanel.reserveBtn.setEnabled(false);
			
			memRentalPanel.memLogout();
			memReservePanel.memLogout();
		}
	}
	
	public void admin_Login() {
		this.sessionAdmin = "T";
		check_Admin_Login();
	}

	public void admin_Logout() {
		this.sessionAdmin = null;
		check_Admin_Login();
	}
	
	public void check_Admin_Login() {
		if(this.sessionAdmin != null) {
			menubar.adminMenu.setVisible(true);
			menubar.adminLogin.setEnabled(false);
		} else {
			menubar.adminMenu.setVisible(false); // 테스트할동안 꺼놓기
//			adminMenu.setVisible(true);
			menubar.adminLogin.setEnabled(true);
		}
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
