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

	JLabel welcomeLabel = new JLabel("�α��� ���ּ���.");
	// ȸ�� ����
	JButton memLoginBtn = new JButton("�α���");
	JButton memJoinBtn = new JButton("ȸ������");
	JButton memModifyBtn = new JButton("��������");
	JButton memLogoutBtn = new JButton("�α׾ƿ�");
	JPanel memLoginPanel = new JPanel(new GridLayout(2, 2));
	JPanel welcomePanel = new JPanel(new BorderLayout());
	
	JPanel memTopPanel = new JPanel(new BorderLayout());
	
	public MemRentalPanel memRentalPanel; 	// �뿩����
	public MemReservePanel memReservePanel; // ��������
	JPanel memBottomPanel = new JPanel(new GridLayout(2, 1));
	JPanel memPanel = new JPanel(new BorderLayout());
	
	public BookListPanel bookListPanel;
	JPanel bookListInfoPanel = new JPanel(new GridLayout(2, 1));
	
	JPanel bookPanel = new JPanel(new BorderLayout());
	
	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
	// -------------------------------------------------------------------
	// ������ �޴���
	public MainMenuBar menubar = new MainMenuBar(this);
	
	// -------------------------------------------------------------------
	// ���� �ʵ�
	String sessionID;
	public String getSessionID() {
		return sessionID;
	}

	String sessionAdmin;
	
	MemberDAO memberDAO;
	HashMap<String, MemberDTO> membersMap;
	
	BookDAO bookDAO;
//	HashMap<String, BookDTO> booksMap; // �Ⱦ��� ����
	
	// -------------------------------------------------------------------
	
	public MainFrame() {
		super("������ ���ø����̼�");
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
		
		// ȸ�� ���� ������
		
		this.memJoinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemJoinDialog memJoinDialog = new MemJoinDialog(frame, "ȸ������");
				memJoinDialog.setMembers(memberDAO);
				memJoinDialog.setVisible(true);
			}
		});
		
		this.memLoginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemLoginDialog memLoginDialog = new MemLoginDialog(frame, "�α���");
				memLoginDialog.setMembers(memberDAO);
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
			welcomeLabel.setText(sessionID + " �� ȯ���մϴ�."); 
			
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
			welcomeLabel.setText("�α��� ���ּ���.");
			
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
			menubar.adminMenu.setVisible(false); // �׽�Ʈ�ҵ��� ������
//			adminMenu.setVisible(true);
			menubar.adminLogin.setEnabled(true);
		}
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
