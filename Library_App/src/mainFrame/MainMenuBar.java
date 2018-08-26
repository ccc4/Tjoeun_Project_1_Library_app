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
	JMenuItem adminLogin = new JMenuItem("������ �α���");
	JMenu adminMenu = new JMenu("Admin Menu");
	JMenuItem adminLogout = new JMenuItem("������ �α׾ƿ�");
	JMenu bookMenu = new JMenu("å ����");
	JMenuItem listBook = new JMenuItem("å ���");
	JMenuItem addBook = new JMenuItem("å ���");
	JMenuItem modBook = new JMenuItem("å ����");
	JMenuItem delBook = new JMenuItem("å ����");
	JMenu memMenu = new JMenu("ȸ�� ����");
	JMenuItem listMem = new JMenuItem("ȸ�� ���");
	JMenuItem addMem = new JMenuItem("ȸ�� ���");
	JMenuItem modMem = new JMenuItem("ȸ�� ����");
	JMenuItem delMem = new JMenuItem("ȸ�� ����");

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
				AdminLoginDialog adminLoginDialog = new AdminLoginDialog(frame, "������ �α���");
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
				BookAddDialog bookAddDialog = new BookAddDialog(frame, "å ���");
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
