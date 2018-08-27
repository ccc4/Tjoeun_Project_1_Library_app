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

	JMenu optionMenu = new JMenu("옵션");
	JMenuItem adminLogin = new JMenuItem("관리자 로그인");
	JMenu adminMenu = new JMenu("관리자 메뉴");
	JMenuItem adminLogout = new JMenuItem("관리자 로그아웃");
	JMenu bookMenu = new JMenu("책 관리");
	JMenuItem listBook = new JMenuItem("책 목록");
	JMenuItem addBook = new JMenuItem("책 등록");
	JMenuItem delBook = new JMenuItem("책 삭제");
	JMenu memMenu = new JMenu("회원 관리");
	JMenuItem listMem = new JMenuItem("회원 목록");
	JMenuItem addMem = new JMenuItem("회원 등록");
	JMenuItem modMem = new JMenuItem("회원 수정");
	JMenuItem delMem = new JMenuItem("회원 삭제");
	JMenu memberMenu = new JMenu("회원 메뉴");
	JMenuItem mem_Change_Pw = new JMenuItem("비밀번호 변경");
	JMenuItem mem__Del_ID = new JMenuItem("회원탈퇴");
	

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

		this.listBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new BookList(frame.bookDAO);
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
		
		this.delBook.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BookDeleteDialog bookDeleteDialog = new BookDeleteDialog(frame, "책 삭제", frame.bookDAO);
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
				MemJoinDialog memJoinDialog = new MemJoinDialog(frame, "회원가입");
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
					JOptionPane.showMessageDialog(null, "예약하거나 빌린 책이 있으면 탈퇴를 할 수 없습니다.\n반납 후 시도해주세요.", "회원탈퇴", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					int ret = JOptionPane.showConfirmDialog(null, "정말 탈퇴하시겠습니까?", "회원탈퇴", JOptionPane.YES_NO_OPTION);
					if(ret != JOptionPane.YES_OPTION) return;
					HashMap<String, MemberDTO> members = frame.memberDAO.getMembers();
					members.remove(sessionID);
					frame.memberDAO.saveMembersToFile(members);
					JOptionPane.showMessageDialog(null, "성공적으로 탈퇴되었습니다.", "회원탈퇴완료", JOptionPane.INFORMATION_MESSAGE);
					frame.member_Logout();
				}
			}
		});
	}
}
