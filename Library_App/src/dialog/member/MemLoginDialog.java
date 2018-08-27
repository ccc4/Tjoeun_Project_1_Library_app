package dialog.member;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mainFrame.MainFrame;
import member.MemberDTO;
import member.MemberDAO;

public class MemLoginDialog extends JDialog {
	MemberDAO memberDAO;
//	HashMap<String, MemberDTO> membersMap;

	public void setMembers(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
//	
//	public void setMembersMap(HashMap<String, MemberDTO> membersMap) {
//		this.membersMap = membersMap;
//	}

	public MemLoginDialog(MainFrame frame, String title) {
		super(frame, title, true);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel botPanel = new JPanel(new GridLayout(1, 2));
		
		JButton loginBtn = new JButton("로그인");
		JButton ExitBtn = new JButton("취소");
		botPanel.add(loginBtn);
		botPanel.add(ExitBtn);
		
		JLabel idLabel = new JLabel("  ID  ");
		JLabel pwLabel = new JLabel("  PW  ");
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
		
		this.setSize(100, 130);
//		setResizable(false);
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
				
				if(memberDAO.checkExist(id)) {
					HashMap<String, MemberDTO> membersMap = memberDAO.getMembers();
					MemberDTO member = membersMap.get(id);
					if(member.getPw().equals(pw)) {
						JOptionPane.showMessageDialog(null, "로그인 성공!", "로그인 성공", JOptionPane.INFORMATION_MESSAGE);
						frame.member_Login_Success(id);
						frame.memRentalPanel.getList();
						frame.memReservePanel.getList();
						setVisible(false);
						
					} else {
						JOptionPane.showMessageDialog(null, "로그인 실패!\n아이디와 비밀번호를 확인해주세요.", "로그인 실패", JOptionPane.WARNING_MESSAGE);
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "로그인 실패!\n일치하는 회원정보가 존재하지 않습니다.", "로그인 실패", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
		});
	}
}
