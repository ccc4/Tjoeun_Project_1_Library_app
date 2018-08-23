package dialog;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mainFrame.MainFrame;
import mainFrame.MainFrame.MemModifyPanel;
import member.MemberDTO;
import member.Members;

public class MemLoginDialog extends JDialog {
	Members members;
	HashMap<String, MemberDTO> membersMap;
	MemModifyPanel memModifyPanel;

	public void setMembers(Members members) {
		this.members = members;
	}
	
	public void setMembersMap(HashMap<String, MemberDTO> membersMap) {
		this.membersMap = membersMap;
	}

	public void setMemModifyPanel(MemModifyPanel memModifyPanel) {
		this.memModifyPanel = memModifyPanel;
	}

	public MemLoginDialog(MainFrame frame, String title) {
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
		setLocationRelativeTo(null);
		
		
		
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
						frame.checkMemberLogin(true);
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
