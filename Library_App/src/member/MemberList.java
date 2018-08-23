package member;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MemberList {
	
	public MemberList() {
		File dir = new File(".\\init");
		File file = new File(dir, "members.txt");
		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			HashMap<String, MemberDTO> members = (HashMap<String, MemberDTO>) in.readObject();
			
			System.out.printf("등록된 회원 수: %d 명\n", members.size());
			
			Set<String> keySet = members.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			while(keyIterator.hasNext()) {
				String key = keyIterator.next();
				MemberDTO member = members.get(key);
				System.out.printf("id: %s // pw: %s // name: %s // age: %s // phone: %s // address: %s // count: %s\n", 
						key, member.getPw(), member.getName(), member.getAge(), member.getPhoneNum(), member.getAddress(), member.getCountRentalBook());
			}
			
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		new MemberList();
	}
}
