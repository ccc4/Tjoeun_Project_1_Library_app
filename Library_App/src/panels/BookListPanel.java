package panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BookListPanel extends JPanel {
	
	JSplitPane centerPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	JButton rentalBtn = new JButton("�뿩");
	JButton holdOnBtn = new JButton("����");
	
	Object[][] contents = null;
	Object[] column = {"����", "����", "�뿩����"};
	DefaultTableModel model = new DefaultTableModel(contents, column);
	JTable table = new JTable(model);
	JScrollPane tablePanel = new JScrollPane(table);
	
	JLabel bookImgLabel = new JLabel("�̹���"); // �̹��� �ҷ����� ��
	
	public BookListPanel() {
		this.setLayout(new BorderLayout());
		
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(botPanel, BorderLayout.SOUTH);
		
		botPanel.add(rentalBtn);
		botPanel.add(holdOnBtn);
		
		centerPanel.add(tablePanel);
		centerPanel.add(bookImgLabel);
		
		centerPanel.setDividerLocation(450);
		centerPanel.setEnabled(false);
	}
}
