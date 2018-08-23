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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import member.MemberDTO;
import member.Members;

public class MainFrame extends JFrame {
	
	
	
	// -------------------------------------------------------------------
	
	Members members;
	HashMap<String, MemberDTO> membersMap;
	
	boolean checkLogin = false;
	
	
	public MainFrame() {
		super("도서관 애플리케이션");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		generateEvents(this);
		
		setSize(1000, 900);
		setResizable(false);
		setVisible(true);
		
		
		members = new Members();
	}
	
	public void generateEvents(JFrame frame) {
		this.memJoinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemJoinDialog memJoinDialog = new MemJoinDialog(frame, "회원가입");
				memJoinDialog.setVisible(true);
				//모달로 설정
				
			}
		});
		
		this.memLoginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemLoginDialog memLoginDialog = new MemLoginDialog(frame, "회원가입");
				memLoginDialog.setVisible(true);
			}
		});
		
		this.memLogoutBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		this.memLogoutBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int check = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "Logout Confirm", JOptionPane.YES_NO_OPTION);
				if(!(check == JOptionPane.YES_OPTION)) return;
				checkLoginMethod(false);
				membersMap = null;
				memModifyPanel.loginShowProfile("", "", "", 0, "", "");
			}
		});
	}
	
	
	
	
	class MemJoinDialog extends JDialog {
		
		public MemJoinDialog(JFrame frame, String title) {
			super(frame, title, true);
			
			JPanel centerPanel = new JPanel(new GridLayout(2, 1));
			JPanel botPanel = new JPanel(new FlowLayout());
			
			JButton joinBtn = new JButton("Join");
			JButton exitBtn = new JButton("Exit");
			botPanel.add(joinBtn);
			botPanel.add(exitBtn);
			
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
			JTextField idField = new JTextField();
			JPasswordField pwField = new JPasswordField();
			JTextField nameField = new JTextField();
			JTextField ageField = new JTextField();
			JTextField phoneField = new JTextField();
			A_FieldPanel.add(idField);
			A_FieldPanel.add(pwField);
			A_FieldPanel.add(nameField);
			A_FieldPanel.add(ageField);
			A_FieldPanel.add(phoneField);
			
			A_Panel.add(A_LabelPanel, BorderLayout.WEST);
			A_Panel.add(A_FieldPanel, BorderLayout.CENTER);
			
			JLabel addressLabel = new JLabel("Address");
			JTextArea addressField = new JTextArea();
			JScrollPane addressPanel = new JScrollPane(addressField);
			
			B_Panel.add(addressLabel, BorderLayout.WEST);
			B_Panel.add(addressPanel, BorderLayout.CENTER);
			
			idLabel.setHorizontalAlignment(SwingConstants.CENTER);
			pwLabel.setHorizontalAlignment(SwingConstants.CENTER);
			nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			ageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			phoneNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
			addressLabel.setHorizontalAlignment(SwingConstants.CENTER);

			
//			getContentPane().setLayout(new BorderLayout());
			this.add(centerPanel, BorderLayout.CENTER);
			this.add(botPanel, BorderLayout.SOUTH);
			
			setSize(300, 450);
			
			exitBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					nameField.setText("");
					ageField.setText("");
					phoneField.setText("");
					addressField.setText("");
				}
			});
			
			joinBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// 회원가입 구현
					
					String id = idField.getText();
					String pw = pwField.getText();
					String name = nameField.getText();
					int age = Integer.parseInt(ageField.getText());
					String phoneNum = phoneField.getText();
					String address = addressField.getText();
					
//					System.out.printf("%s, %s, %s, %d, %s, %s", id, pw, name, age, phoneNum, address);
					
					MemberDTO member = new MemberDTO(id, pw, name, age, phoneNum, address);
					if(members.checkMem(id)) {
						JOptionPane.showMessageDialog(null, "이미 가입된 아이디가 있습니다.", "Alert", JOptionPane.WARNING_MESSAGE);
					} else {
						members.addMem(id, member);
						JOptionPane.showMessageDialog(null, "회원가입 성공!", "Alert", JOptionPane.INFORMATION_MESSAGE);
					}
					
					
					setVisible(false);
					idField.setText("");
					pwField.setText("");
					nameField.setText("");
					ageField.setText("");
					phoneField.setText("");
					addressField.setText("");
				}
			});
		}
	}
	
	class MemLoginDialog extends JDialog {
		public MemLoginDialog(JFrame frame, String title) {
			super(frame, title, true);
			
			JPanel centerPanel = new JPanel(new BorderLayout());
			JPanel botPanel = new JPanel(new GridLayout(1, 2));
			
			JButton loginBtn = new JButton("Login");
			JButton ExitBtn = new JButton("Exit");
			botPanel.add(loginBtn);
			botPanel.add(ExitBtn);
			
			JLabel idLabel = new JLabel("ID");
			JLabel pwLabel = new JLabel("PW");
			JPanel labelPanel = new JPanel(new GridLayout(2, 1));
			labelPanel.add(idLabel);
			labelPanel.add(pwLabel);
			JTextField idField = new JTextField();
			JPasswordField pwField = new JPasswordField();
			JPanel fieldPanel = new JPanel(new GridLayout(2, 1));
			fieldPanel.add(idField);
			fieldPanel.add(pwField);
			centerPanel.add(labelPanel, BorderLayout.WEST);
			centerPanel.add(fieldPanel, BorderLayout.CENTER);
			
			this.add(centerPanel, BorderLayout.CENTER);
			this.add(botPanel, BorderLayout.SOUTH);
			
			idLabel.setHorizontalAlignment(SwingConstants.CENTER);
			pwLabel.setHorizontalAlignment(SwingConstants.CENTER);
			
			setSize(100, 150);
			
			ExitBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
			
			loginBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String id = idField.getText();
					String pw = pwField.getText();
					
					if(members.checkMem(id)) {
						membersMap = members.getMembers();
						MemberDTO member = membersMap.get(id);
						if(member.getPw().equals(pw)) {
							JOptionPane.showMessageDialog(null, "로그인 성공!", "Login Success", JOptionPane.INFORMATION_MESSAGE);
							checkLoginMethod(true);
							memModifyPanel.loginShowProfile(member.getId(), member.getPw(), member.getName(), member.getAge(), member.getPhoneNum(), member.getAddress());
							setVisible(false);
							
						} else {
							JOptionPane.showMessageDialog(null, "로그인 실패!\n비밀번호 틀림", "Login Fail", JOptionPane.WARNING_MESSAGE);
							return;
						}
					} else {
						JOptionPane.showMessageDialog(null, "로그인 실패!\n아이디 확인 필요", "Login Fail", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
			});
		}
	}
	
	class MemModifyPanel extends JPanel {
		
	}
	

	
	public void checkLoginMethod(boolean checkLogin) {
		if(checkLogin) {
			this.checkLogin = checkLogin;
			memLoginBtn.setEnabled(false);
			memJoinBtn.setEnabled(false);
			memModifyPanel.modifyBtn.setEnabled(true);
			memLogoutBtn.setEnabled(true);
		} else {
			this.checkLogin = checkLogin;
			memLoginBtn.setEnabled(true);
			memJoinBtn.setEnabled(true);
			memModifyPanel.modifyBtn.setEnabled(false);
			memLogoutBtn.setEnabled(false);
		}
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
