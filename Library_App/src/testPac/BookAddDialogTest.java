package testPac;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import panels.ImagePanel;

public class BookAddDialogTest extends JFrame {
	BookAddDialogTest frame;
	
	JButton btn;
	
	
	public BookAddDialogTest() {
		super("test");
		this.frame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btn = new JButton("test");
		this.btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Dialog test = new Dialog(frame, "제목");
				test.setVisible(true);
				
			}
		});
		
		this.add(btn);
		setSize(200, 200);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new BookAddDialogTest();
	}
	
	class Dialog extends JDialog{
		DropTarget dt;
		JPanel contentPanel = new JPanel(new BorderLayout());
		JPanel botPanel = new JPanel(new GridLayout(3, 1));
		JPanel imagePanel;
		JLabel titleLabel = new JLabel("제목");
		JLabel authorLabel = new JLabel("저자");
		JTextField titleField = new JTextField();
		JTextField authorField = new JTextField();
		JPanel writePanel_1 = new JPanel(new BorderLayout());
		JPanel writePanel_2 = new JPanel(new BorderLayout());
		JPanel btnPanel = new JPanel(new FlowLayout());
		JButton imgBtn = new JButton("이미지");
		JButton addBtn = new JButton("추가");
		
		public Dialog(BookAddDialogTest frame, String title) {
			super(frame, title, true);
			
			
			imagePanel = new JPanel();
			writePanel_1.add(titleLabel, BorderLayout.WEST);
			writePanel_1.add(titleField, BorderLayout.CENTER);
			writePanel_2.add(authorLabel, BorderLayout.WEST);
			writePanel_2.add(authorField, BorderLayout.CENTER);
			btnPanel.add(imgBtn);
			btnPanel.add(addBtn);
			
			botPanel.add(writePanel_1);
			botPanel.add(writePanel_2);
			botPanel.add(btnPanel);
			contentPanel.add(imagePanel, BorderLayout.CENTER);
			contentPanel.add(botPanel, BorderLayout.SOUTH);
			
			this.add(contentPanel);
			
			this.setSize(100, 430);
			this.setLocationRelativeTo(null);
			
//			imagePanel.addComponentListener(new DropTargetAdapter() {
//				
//				@Override
//				public void drop(DropTargetDropEvent dtde) {
//					dtde.acceptDrop(dtde.getDropAction());
//					Transferable tf = dtde.getTransferable();
//					try {
//						List list = (List) tf.getTransferData(DataFlavor.javaFileListFlavor);
//						File imageFile = (File)list.get(0);
//						String imageFilePath = imageFile.getAbsolutePath();
//						ImageIcon icon = new ImageIcon(imageFilePath);
//						imagePanel.setIcon(icon);
//
//						/*
//						for (int i = 0; i < list.size(); i++)
//							System.out.println("" + list.get(i));
//						*/
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					
//				}
//			});
			
			
			
			
			
//			dt = new DropTarget(imagePanel, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
//				
//				@Override
//				public void drop(DropTargetDropEvent dtde) {
//					dtde.acceptDrop(dtde.getDropAction());
//					Transferable tf = dtde.getTransferable();
//					try {
//						List list = (List) tf.getTransferData(DataFlavor.javaFileListFlavor);
//						File imageFile = (File)list.get(0);
//						String imageFilePath = imageFile.getAbsolutePath();
//						ImageIcon icon = new ImageIcon(imageFilePath);
//						imagePanel.setIcon(icon);
//
//						/*
//						for (int i = 0; i < list.size(); i++)
//							System.out.println("" + list.get(i));
//						*/
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}, true, null);
		}
		
	}
}
