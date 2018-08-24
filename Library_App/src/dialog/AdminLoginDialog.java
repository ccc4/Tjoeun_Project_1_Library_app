package dialog;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mainFrame.MainFrame;

public class AdminLoginDialog extends JDialog {
	private static final String ADMIN_ID = "abcd";
	private static final String ADMIN_PW = "1234";
	
	public AdminLoginDialog(MainFrame frame, String title) {
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
				
				if(id.equals(ADMIN_ID) && pw.equals(ADMIN_PW)) {
					frame.admin_Login();
					frame.check_Admin_Login();
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "로그인 실패!", "Login Fail", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
}
