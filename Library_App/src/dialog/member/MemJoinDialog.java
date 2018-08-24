package dialog.member;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

		
//		getContentPane().setLayout(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(botPanel, BorderLayout.SOUTH);
		
		setSize(300, 450);
		setLocationRelativeTo(null);
		
		
		
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
//				nameField.setText("");
//				ageField.setText("");
//				phoneField.setText("");
//				addressField.setText("");
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
				
//				System.out.printf("%s, %s, %s, %d, %s, %s", id, pw, name, age, phoneNum, address);
				
				MemberDTO newMember = new MemberDTO(id, pw, name, age, phoneNum, address);
				if(memberDAO.checkExist(id)) {
					JOptionPane.showMessageDialog(null, "이미 가입된 아이디가 있습니다.", "Alert", JOptionPane.WARNING_MESSAGE);
				} else {
					memberDAO.addMem(id, newMember);
					JOptionPane.showMessageDialog(null, "회원가입 성공!", "Alert", JOptionPane.INFORMATION_MESSAGE);
				}
				
				
				setVisible(false);
//				idField.setText("");
//				pwField.setText("");
//				nameField.setText("");
//				ageField.setText("");
//				phoneField.setText("");
//				addressField.setText("");
			}
		});
	}
}
