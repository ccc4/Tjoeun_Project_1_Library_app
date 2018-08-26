package mainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import book.BookList;
import dialog.admin.AdminLoginDialog;
import dialog.book.BookAddDialog;
import member.MemberList;

public class MainMenuBar extends JMenuBar{

	JMenu optionMenu = new JMenu("Option");
	JMenuItem adminLogin = new JMenuItem("관리자 로그인");
	JMenu adminMenu = new JMenu("Admin Menu");
	JMenuItem adminLogout = new JMenuItem("관리자 로그아웃");
	JMenu bookMenu = new JMenu("책 관리");
	JMenuItem listBook = new JMenuItem("책 목록");
	JMenuItem addBook = new JMenuItem("책 등록");
	JMenuItem modBook = new JMenuItem("책 수정");
	JMenuItem delBook = new JMenuItem("책 삭제");
	JMenu memMenu = new JMenu("회원 관리");
	JMenuItem listMem = new JMenuItem("회원 목록");
	JMenuItem addMem = new JMenuItem("회원 등록");
	JMenuItem modMem = new JMenuItem("회원 수정");
	JMenuItem delMem = new JMenuItem("회원 삭제");

	public MainMenuBar(MainFrame frame) {

		optionMenu.add(adminLogin);
		this.add(optionMenu);
		adminMenu.add(adminLogout);
		adminMenu.add(bookMenu);
		bookMenu.add(listBook);
		bookMenu.addSeparator();
		bookMenu.add(addBook);
		bookMenu.add(modBook);
		bookMenu.add(delBook);
		adminMenu.add(memMenu);
		memMenu.add(listMem);
		memMenu.addSeparator();
		memMenu.add(addMem);
		memMenu.add(modMem);
		memMenu.add(delMem);
		this.add(adminMenu);

		this.adminLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AdminLoginDialog adminLoginDialog = new AdminLoginDialog(frame, "관리자 로그인");
				adminLoginDialog.setVisible(true);

			}
		});

		this.adminLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.admin_Logout();
			}
		});

		this.listMem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new MemberList(frame.memberDAO);
			}
		});

		this.addBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BookAddDialog bookAddDialog = new BookAddDialog(frame, "책 등록");
				bookAddDialog.setBookDAO(frame.bookDAO);
				bookAddDialog.setVisible(true);
			}
		});

		this.listBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new BookList(frame.bookDAO);
			}
		});
	}
}
