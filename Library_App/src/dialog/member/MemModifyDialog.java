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

import member.MemberDAO;
import member.MemberDTO;

public class MemModifyDialog extends JDialog {
	JTextField idField;
	JPasswordField pwField;
	JTextField nameField;
	JTextField ageField;
	JTextField phoneField;
	JTextArea addressField;
	
	MemberDTO member;
	
	public JButton modifyBtn;
	
	public MemModifyDialog(JFrame frame, String title, MemberDAO memberDAO, MemberDTO member) {
		super(frame, title, true);
		
		this.member = member;
		
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
		
		setSize(300, 450);
		setLocationRelativeTo(null);
		
		loginShowProfile();
//		if(memberDTO != null) 
		
		modifyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int check = JOptionPane.showConfirmDialog(null, "ȸ�������� �����Ͻðڽ��ϱ�?", "Modify Confirm", JOptionPane.YES_NO_OPTION);
				if(!(check == JOptionPane.YES_OPTION)) return;
//				MemberDTO member = membersMap.get(idField.getText());
				
				String id = idField.getText();
				String pw = pwField.getText();
				String name = nameField.getText();
				int age = Integer.parseInt(ageField.getText());
				String phoneNum = phoneField.getText();
				String address = addressField.getText();
				
//				System.out.printf("%s, %s, %s, %d, %s, %s", id, pw, name, age, phoneNum, address);
				
				MemberDTO member = new MemberDTO(id, pw, name, age, phoneNum, address);
				memberDAO.addMem(id, member);
				JOptionPane.showMessageDialog(null, "ȸ���������� ����!", "Alert", JOptionPane.INFORMATION_MESSAGE);
				
				setVisible(false);
//				pwField.setText(pw);
//				nameField.setText(name);
//				ageField.setText(String.valueOf(age));
//				phoneField.setText(phoneNum);
//				addressField.setText(address);
			}
		});
	}
	private void loginShowProfile() {
		
		idField.setText(member.getId());
		pwField.setText(member.getPw());
		nameField.setText(member.getName());
//		if(member == 0) ageField.setText("");
//		else 
		ageField.setText(String.valueOf(member.getAge()));
		phoneField.setText(member.getPhoneNum());
		addressField.setText(member.getAddress());
	}
}
