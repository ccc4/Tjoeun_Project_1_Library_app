package dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import mainFrame.MainFrame;

public class BookAddDialog extends JDialog {
	
	
	public BookAddDialog(MainFrame frame, String title) {
		super(frame, title);
		
		JPanel centerPanel = new JPanel();
		JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton addBtn = new JButton("Add");
		JButton exitBtn = new JButton("Cancle");
		botPanel.add(addBtn);
		botPanel.add(exitBtn);
		
		JPanel settingPanel = new JPanel();
		
		
		
		
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(botPanel, BorderLayout.SOUTH);
		
		setSize(400, 600);
	}
}
