package mainFrame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import mainFrame.MainFrame.MemModifyPanel;

public class mainFrameUI extends Container {
	
	JButton memLoginBtn = new JButton("로그인");
	JButton memJoinBtn = new JButton("회원가입");
	JButton memLogoutBtn = new JButton("로그아웃");
	JPanel memLoginPanel = new JPanel(new GridLayout(1, 3));
	
	MemModifyPanel memModifyPanel;
	JPanel memProfilePanel = new JPanel();
	
	JButton memRentalInfoTest = new JButton("대여정보");
//	JPanel memRentalInfoPanel = new JPanel();
	JButton memServInfoTest = new JButton("예약정보");
//	JPanel memServInfoPanel = new JPanel();
	JPanel memPanel = new JPanel(new GridLayout(3, 1));
	
	JButton bookSearchBtn = new JButton("책 검색");
	JPanel bookSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	JButton bookListTest = new JButton("책리스트");
//	JPanel bookListPanel = new JPanel();
	JButton bookInfoTest = new JButton("도서정보");
//	JPanel bookInfoPanel = new JPanel();
	JPanel bookListInfoPanel = new JPanel(new GridLayout(2, 1));
	
	JPanel bookPanel = new JPanel(new BorderLayout());
	
	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
	
	
	
	
	public mainFrameUI() {
		memLoginPanel.add(memLoginBtn);
		memLoginPanel.add(memJoinBtn);
		memLoginPanel.add(memLogoutBtn);
		
//		memPanel.add(memLoginPanel);
		memModifyPanel = new MemModifyPanel();
		memProfilePanel.setLayout(new BorderLayout());
		memProfilePanel.add(memLoginPanel, BorderLayout.NORTH);
		memProfilePanel.add(memModifyPanel, BorderLayout.CENTER);
		
//		memPanel.add(memModifyPanel);
		memPanel.add(memProfilePanel);
		memPanel.add(memRentalInfoTest);
		memPanel.add(memServInfoTest);
		
		
		bookSearchPanel.add(bookSearchBtn);
		
		bookListInfoPanel.add(bookListTest);
		bookListInfoPanel.add(bookInfoTest);
		
		bookPanel.add(bookSearchPanel, BorderLayout.NORTH);
		bookPanel.add(bookListInfoPanel, BorderLayout.CENTER);
		
		
		splitPane.add(bookPanel);
		splitPane.add(memPanel);

		
		
		
		this.add(splitPane);
		
		splitPane.setDividerLocation(600);
		splitPane.setEnabled(false);
		memLogoutBtn.setEnabled(false);
		memModifyPanel.modifyBtn.setEnabled(false);
	}
}
