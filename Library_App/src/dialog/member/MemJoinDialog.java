package dialog.member;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mainFrame.MainFrame;
import member.MemberDTO;
import member.MemberDAO;

public class MemJoinDialog extends JDialog {
	MemberDAO memberDAO;
	
	public void setMembers(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public MemJoinDialog(MainFrame frame, String title) {
		super(frame, title, true);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		JPanel botPanel = new JPanel(new FlowLayout());
		
		JButton joinBtn = new JButton("가입");
		JButton exitBtn = new JButton("취소");
		botPanel.add(joinBtn);
		botPanel.add(exitBtn);
		
		JPanel A_Panel = new JPanel(new BorderLayout());
		JPanel B_Panel = new JPanel(new BorderLayout());
		centerPanel.add(A_Panel);
		centerPanel.add(B_Panel);
		
		JPanel A_LabelPanel = new JPanel(new GridLayout(6, 1));
		JLabel idLabel = new JLabel("ID");
		JLabel pwLabel = new JLabel("PW");
		JLabel pwLabel_Check = new JLabel("확인");
		JLabel nameLabel = new JLabel("이름");
		JLabel ageLabel = new JLabel("나이");
		JLabel phoneNumLabel = new JLabel("번호");
		A_LabelPanel.add(idLabel);
		A_LabelPanel.add(pwLabel);
		A_LabelPanel.add(pwLabel_Check);
		A_LabelPanel.add(nameLabel);
		A_LabelPanel.add(ageLabel);
		A_LabelPanel.add(phoneNumLabel);
		
		JPanel A_FieldPanel = new JPanel(new GridLayout(6, 1));
		JTextField idField = new JTextField();
		JPasswordField pwField = new JPasswordField();
		JPasswordField pwField_Check = new JPasswordField();
		JTextField nameField = new JTextField();
		JTextField ageField = new JTextField();
		JTextField phoneField = new JTextField();
		A_FieldPanel.add(idField);
		A_FieldPanel.add(pwField);
		A_FieldPanel.add(pwField_Check);
		A_FieldPanel.add(nameField);
		A_FieldPanel.add(ageField);
		A_FieldPanel.add(phoneField);
		
		A_Panel.add(A_LabelPanel, BorderLayout.WEST);
		A_Panel.add(A_FieldPanel, BorderLayout.CENTER);
		
		JLabel addressLabel = new JLabel("주소");
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

		this.add(centerPanel, BorderLayout.CENTER);
		this.add(botPanel, BorderLayout.SOUTH);
		
		setSize(200, 350);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		joinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 회원가입 구현
				
				try {
					String id = idField.getText().trim();
					String pw = pwField.getText().trim();
					if(!pw.equals(pwField_Check.getText().trim())) {
						JOptionPane.showMessageDialog(null, "비밀번호 재확인 필요", "비밀번호 재확인 필요", JOptionPane.WARNING_MESSAGE);
						return;
					}
					String name = nameField.getText().trim();
					int age = Integer.parseInt(ageField.getText().trim());
					String phoneNum = phoneField.getText().trim();
					String address = addressField.getText().trim();
					
//				System.out.printf("%s, %s, %s, %d, %s, %s", id, pw, name, age, phoneNum, address);
					
					MemberDTO newMember = new MemberDTO(id, pw, name, age, phoneNum, address, null, null);
					if(memberDAO.checkExist(id)) {
						JOptionPane.showMessageDialog(null, "이미 가입된 아이디가 있습니다.", "Alert", JOptionPane.WARNING_MESSAGE);
					} else {
						memberDAO.addMem(id, newMember);
						JOptionPane.showMessageDialog(null, "회원가입 성공!", "Alert", JOptionPane.INFORMATION_MESSAGE);
					}
					
					
					setVisible(false);
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "나이에는 숫자만 입력가능합니다.", "Exception", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
		});
	}
}
