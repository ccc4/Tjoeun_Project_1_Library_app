package mainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import book.BookList;
import dialog.admin.AdminLoginDialog;
import dialog.admin.BookDeleteDialog;
import dialog.book.BookAddDialog;
import dialog.member.MemJoinDialog;
import member.MemberDAO;
import member.MemberDTO;
import member.MemberList;

public class MainMenuBar extends JMenuBar{

	JMenu optionMenu = new JMenu("�ɼ�");
	JMenuItem adminLogin = new JMenuItem("������ �α���");
	JMenu adminMenu = new JMenu("������ �޴�");
	JMenuItem adminLogout = new JMenuItem("������ �α׾ƿ�");
	JMenu bookMenu = new JMenu("å ����");
	JMenuItem listBook = new JMenuItem("å ���");
	JMenuItem addBook = new JMenuItem("å ���");
	JMenuItem delBook = new JMenuItem("å ����");
	JMenu memMenu = new JMenu("ȸ�� ����");
	JMenuItem listMem = new JMenuItem("ȸ�� ���");
	JMenuItem addMem = new JMenuItem("ȸ�� ���");
	JMenuItem modMem = new JMenuItem("ȸ�� ����");
	JMenuItem delMem = new JMenuItem("ȸ�� ����");
	JMenu memberMenu = new JMenu("ȸ�� �޴�");
	JMenuItem mem_Change_Pw = new JMenuItem("��й�ȣ ����");
	JMenuItem mem__Del_ID = new JMenuItem("ȸ��Ż��");
	

	public MainMenuBar(MainFrame frame) {

		optionMenu.add(adminLogin);
		this.add(optionMenu);
		adminMenu.add(adminLogout);
		adminMenu.add(bookMenu);
		bookMenu.add(listBook);
		bookMenu.addSeparator();
		bookMenu.add(addBook);
		bookMenu.add(delBook);
		adminMenu.add(memMenu);
		memMenu.add(listMem);
		memMenu.addSeparator();
		memMenu.add(addMem);
		memMenu.add(modMem);
		memMenu.add(delMem);
		this.add(adminMenu);
		memberMenu.add(mem_Change_Pw);
		memberMenu.add(mem__Del_ID);
		this.add(memberMenu);

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

		this.listBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new BookList(frame.bookDAO);
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
		
		this.delBook.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BookDeleteDialog bookDeleteDialog = new BookDeleteDialog(frame, "å ����", frame.bookDAO);
				bookDeleteDialog.setVisible(true);
			}
		});
		
		this.listMem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new MemberList(frame.memberDAO);
			}
		});
		
		this.addMem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MemJoinDialog memJoinDialog = new MemJoinDialog(frame, "ȸ������");
				memJoinDialog.setMembers(frame.memberDAO);
				memJoinDialog.setVisible(true);
			}
		});
		
		this.modMem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		this.delMem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		this.mem__Del_ID.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String sessionID = frame.getSessionID();
				if(!(frame.memberDAO.getMembers().get(sessionID).getBooks_rentaled().size() == 0 && 
						frame.memberDAO.getMembers().get(sessionID).getBooks_rentaled().size() == 0)) {
					JOptionPane.showMessageDialog(null, "�����ϰų� ���� å�� ������ Ż�� �� �� �����ϴ�.\n�ݳ� �� �õ����ּ���.", "ȸ��Ż��", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					int ret = JOptionPane.showConfirmDialog(null, "���� Ż���Ͻðڽ��ϱ�?", "ȸ��Ż��", JOptionPane.YES_NO_OPTION);
					if(ret != JOptionPane.YES_OPTION) return;
					HashMap<String, MemberDTO> members = frame.memberDAO.getMembers();
					members.remove(sessionID);
					frame.memberDAO.saveMembersToFile(members);
					JOptionPane.showMessageDialog(null, "���������� Ż��Ǿ����ϴ�.", "ȸ��Ż��Ϸ�", JOptionPane.INFORMATION_MESSAGE);
					frame.member_Logout();
				}
			}
		});
	}
}
