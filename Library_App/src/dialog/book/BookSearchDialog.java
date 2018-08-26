package dialog.book;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainFrame.MainFrame;

public class BookSearchDialog extends JDialog {
	
	JTextField searchField = new JTextField(15);
	JButton searchBtn = new JButton("°Ë»ö");
	
	JPanel topPanel = new JPanel();
	JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	public BookSearchDialog(MainFrame frame, String title) {
		super(frame, title, true);
		
		this.setLayout(new GridLayout(2, 1));
		
		topPanel.add(searchField);
		botPanel.add(searchBtn);
		
		this.add(topPanel);
		this.add(botPanel);
		

		setSize(200, 100);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		
		
		searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
