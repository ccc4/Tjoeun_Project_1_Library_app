package mainFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dialog.AdminLoginDialog;
import dialog.BookAddDialog;
import dialog.MemJoinDialog;
import dialog.MemLoginDialog;
import mainFrame.MainFrame.MemModifyPanel;
import member.MemberDTO;
import member.MemberList;
import member.Members;

public class MainFrame extends JFrame {

	JButton memLoginBtn = new JButton("�α���");
	JButton memJoinBtn = new JButton("ȸ������");
	JButton memLogoutBtn = new JButton("�α׾ƿ�");
	JPanel memLoginPanel = new JPanel(new GridLayout(1, 3));
	
	MemModifyPanel memModifyPanel;
	JPanel memProfilePanel = new JPanel();
	
	JButton memRentalInfoTest = new JButton("�뿩����");
//	JPanel memRentalInfoPanel = new JPanel();
	JButton memServInfoTest = new JButton("��������");
//	JPanel memServInfoPanel = new JPanel();
	JPanel memPanel = new JPanel(new GridLayout(3, 1));
	
	JButton bookSearchBtn = new JButton("å �˻�");
	JPanel bookSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	JButton bookListTest = new JButton("å����Ʈ");
//	JPanel bookListPanel = new JPanel();
	JButton bookInfoTest = new JButton("��������");
//	JPanel bookInfoPanel = new JPanel();
	JPanel bookListInfoPanel = new JPanel(new GridLayout(2, 1));
	
	JPanel bookPanel = new JPanel(new BorderLayout());
	
	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
	// -------------------------------------------------------------------
	
	JMenuBar menubar = new  JMenuBar();
	JMenu optionMenu = new JMenu("Option");
	JMenuItem adminLogin = new JMenuItem("������ �α���");
	JMenu adminMenu = new JMenu("Admin Menu");
	JMenuItem adminLogout = new JMenuItem("������ �α׾ƿ�");
	JMenu bookMenu = new JMenu("å ����");
	JMenuItem addBook = new JMenuItem("å ���");
	JMenuItem modBook = new JMenuItem("å ����");
	JMenuItem delBook = new JMenuItem("å ����");
	JMenu memMenu = new JMenu("ȸ�� ����");
	JMenuItem listMem = new JMenuItem("ȸ�� ���");
	JMenuItem addMem = new JMenuItem("ȸ�� ���");
	JMenuItem modMem = new JMenuItem("ȸ�� ����");
	JMenuItem delMem = new JMenuItem("ȸ�� ����");
	
	
	// -------------------------------------------------------------------
	
	Members members;
	HashMap<String, MemberDTO> membersMap;
	
	boolean checkMemberLogin = false;
	boolean checkAdminLogin = false;
	
	public MainFrame() {
		super("������ ���ø����̼�");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		memLoginPanel.add(memLoginBtn);
		memLoginPanel.add(memJoinBtn);
		memLoginPanel.add(memLogoutBtn);
		
//		memPanel.add(memLoginPanel);
		memModifyPanel = new MemModifyPanel();
		memProfilePanel.setLayout(new BorderLayout());
		memProfilePanel.add(memLoginPanel, BorderLayout.NORTH);
		memProfilePanel.add(memModifyPanel, BorderLayout.CENTER);
		
//		memPanel.add(memModifyPanel);
		memPanel.add(memProfilePanel);
		memPanel.add(memRentalInfoTest);
		memPanel.add(memServInfoTest);
		
		
		bookSearchPanel.add(bookSearchBtn);
		
		bookListInfoPanel.add(bookListTest);
		bookListInfoPanel.add(bookInfoTest);
		
		bookPanel.add(bookSearchPanel, BorderLayout.NORTH);
		bookPanel.add(bookListInfoPanel, BorderLayout.CENTER);
		
		
		splitPane.add(bookPanel);
		splitPane.add(memPanel);
		
		add(splitPane);
		splitPane.setDividerLocation(600);
		splitPane.setEnabled(false);
		
		
		// --------------------------------------------------------
		
		optionMenu.add(adminLogin);
		menubar.add(optionMenu);
		adminMenu.add(adminLogout);
		adminMenu.add(bookMenu);
		bookMenu.add(addBook);
		bookMenu.add(modBook);
		bookMenu.add(delBook);
		adminMenu.add(memMenu);
		memMenu.add(listMem);
		memMenu.add(addMem);
		memMenu.add(modMem);
		memMenu.add(delMem);
		menubar.add(adminMenu);
		
		
		this.setJMenuBar(menubar);
		
		
		
		// --------------------------------------------------------
		
		checkMemberLogin(checkMemberLogin);
		checkAdminLogin(checkAdminLogin);
		generateEvents(this);
		
		this.setSize(1000, 900);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		members = new Members();
	}
	
	
	
	
	
	
	
	public void generateEvents(MainFrame frame) {
		this.memJoinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemJoinDialog memJoinDialog = new MemJoinDialog(frame, "ȸ������");
				memJoinDialog.setMembers(members);
				memJoinDialog.setVisible(true);
				//��޷� ����
				
			}
		});
		
		this.memLoginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemLoginDialog memLoginDialog = new MemLoginDialog(frame, "�α���");
				memLoginDialog.setMembers(members);
				memLoginDialog.setMembersMap(membersMap);
				memLoginDialog.setMemModifyPanel(memModifyPanel);
				memLoginDialog.setVisible(true);
			}
		});
		
		this.memLogoutBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int check = JOptionPane.showConfirmDialog(null, "�α׾ƿ� �Ͻðڽ��ϱ�?", "Logout Confirm", JOptionPane.YES_NO_OPTION);
				if(!(check == JOptionPane.YES_OPTION)) return;
				checkMemberLogin(false);
				membersMap = null;
				memModifyPanel.loginShowProfile("", "", "", 0, "", "");
			}
		});
		
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
				checkAdminLogin(false);
			}
		});
		
		this.listMem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MemberList();
			}
		});
		
		this.addBook.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BookAddDialog bookAddDialog = new BookAddDialog(frame, "å ���");
				bookAddDialog.setVisible(true);
			}
		});
	}
	
	
	
	public class MemModifyPanel extends JPanel {
		JTextField idField;
		JPasswordField pwField;
		JTextField nameField;
		JTextField ageField;
		JTextField phoneField;
		JTextArea addressField;
		
		JButton modifyBtn;
		
		public MemModifyPanel() {
			this.setLayout(new BorderLayout());
			
			JPanel centerPanel = new JPanel(new GridLayout(2, 1));
			JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			
			modifyBtn = new JButton("Modify");
			botPanel.add(modifyBtn);
			
			JPanel A_Panel = new JPanel(new BorderLayout());
			JPanel B_Panel = new JPanel(new BorderLayout());
			centerPanel.add(A_Panel);
			centerPanel.add(B_Panel);
			
			JPanel A_LabelPanel = new JPanel(new GridLayout(5, 1));
			JLabel idLabel = new JLabel("ID");
			JLabel pwLabel = new JLabel("PW");
			JLabel nameLabel = new JLabel("Name");
			JLabel ageLabel = new JLabel("age");
			JLabel phoneNumLabel = new JLabel("Phone");
			A_LabelPanel.add(idLabel);
			A_LabelPanel.add(pwLabel);
			A_LabelPanel.add(nameLabel);
			A_LabelPanel.add(ageLabel);
			A_LabelPanel.add(phoneNumLabel);
			
			JPanel A_FieldPanel = new JPanel(new GridLayout(5, 1));
			idField = new JTextField();
			pwField = new JPasswordField();
			nameField = new JTextField();
			ageField = new JTextField();
			phoneField = new JTextField();
			A_FieldPanel.add(idField);
			A_FieldPanel.add(pwField);
			A_FieldPanel.add(nameField);
			A_FieldPanel.add(ageField);
			A_FieldPanel.add(phoneField);
			
			A_Panel.add(A_LabelPanel, BorderLayout.WEST);
			A_Panel.add(A_FieldPanel, BorderLayout.CENTER);
			
			JLabel addressLabel = new JLabel("Address");
			addressField = new JTextArea();
			JScrollPane addressPanel = new JScrollPane(addressField);
			
			B_Panel.add(addressLabel, BorderLayout.WEST);
			B_Panel.add(addressPanel, BorderLayout.CENTER);
			
			idLabel.setHorizontalAlignment(SwingConstants.CENTER);
			pwLabel.setHorizontalAlignment(SwingConstants.CENTER);
			nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			ageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			phoneNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
			addressLabel.setHorizontalAlignment(SwingConstants.CENTER);
			idField.setEditable(false);

			
			this.add(centerPanel, BorderLayout.CENTER);
			this.add(botPanel, BorderLayout.SOUTH);
			
			modifyBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int check = JOptionPane.showConfirmDialog(null, "ȸ�������� �����Ͻðڽ��ϱ�?", "Modify Confirm", JOptionPane.YES_NO_OPTION);
					if(!(check == JOptionPane.YES_OPTION)) return;
//					MemberDTO member = membersMap.get(idField.getText());
					
					String id = idField.getText();
					String pw = pwField.getText();
					String name = nameField.getText();
					int age = Integer.parseInt(ageField.getText());
					String phoneNum = phoneField.getText();
					String address = addressField.getText();
					
//					System.out.printf("%s, %s, %s, %d, %s, %s", id, pw, name, age, phoneNum, address);
					
					MemberDTO member = new MemberDTO(id, pw, name, age, phoneNum, address);
					members.addMem(id, member);
					JOptionPane.showMessageDialog(null, "ȸ���������� ����!", "Alert", JOptionPane.INFORMATION_MESSAGE);
					
					pwField.setText(pw);
					nameField.setText(name);
					ageField.setText(String.valueOf(age));
					phoneField.setText(phoneNum);
					addressField.setText(address);
				}
			});
		}
		public void loginShowProfile(String id, String pw, String name, int age, String phoneNum, String address) {
			idField.setText(id);
			pwField.setText(pw);
			nameField.setText(name);
			if(age == 0) ageField.setText("");
			else ageField.setText(String.valueOf(age));
			phoneField.setText(phoneNum);
			addressField.setText(address);
		}
	}
	
	
	
	

	
	public void checkMemberLogin(boolean checkLogin) {
		if(checkLogin) {
			this.checkMemberLogin = checkLogin;
			memLoginBtn.setEnabled(false);
			memJoinBtn.setEnabled(false);
			memModifyPanel.modifyBtn.setEnabled(true);
			memLogoutBtn.setEnabled(true);
			bookSearchBtn.setEnabled(true);
		} else {
			this.checkMemberLogin = checkLogin;
			memLoginBtn.setEnabled(true);
			memJoinBtn.setEnabled(true);
			memModifyPanel.modifyBtn.setEnabled(false);
			memLogoutBtn.setEnabled(false);
			bookSearchBtn.setEnabled(false);
		}
	}
	
	public void checkAdminLogin(boolean checkLogin) {
		if(checkLogin) {
			adminMenu.setVisible(true);
			adminLogin.setEnabled(false);
		} else {
			adminMenu.setVisible(false);
			adminLogin.setEnabled(true);
		}
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
