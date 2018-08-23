package member;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class MemberList {
	
	public MemberList() {
		File dir = new File(".\\init");
		File file = new File(dir, "members.txt");
		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			HashMap<String, MemberDTO> members = (HashMap<String, MemberDTO>) in.readObject();
			
//			for(int i=0;i<members.size();i++) {
//				
//			}
			System.out.println(members.size());
			
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		new MemberList();
	}
}
