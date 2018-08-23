package member;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Members {
	private final String DIRNAME = ".\\init";
	private final String FILENAME = "members.txt";
	
//	ArrayList<MemberDTO> members;
	HashMap<String, MemberDTO> members;
	File file;
	
	public HashMap<String, MemberDTO> getMembers() {
		return members;
	}

	public void setMembers(HashMap<String, MemberDTO> members) {
		this.members = members;
	}

	public Members() {
		try {
			File dir = new File(DIRNAME);
			if(!dir.exists()) dir.mkdirs();
			this.file = new File(dir, FILENAME);
			if(!file.exists()) file.createNewFile();
			
			BufferedReader check = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			if(check.readLine() == null) {
				members = new HashMap<>();
				System.out.println("파일없어서 새로 만듦");
				saveMembersToFile(members);
			} else {
//				BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
				ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
				members = (HashMap<String, MemberDTO>) in.readObject();
				in.close();
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void addMem(String id, MemberDTO member) {
		this.members.put(id, member);
		saveMembersToFile(this.members);
	}
	
	public boolean checkMem(String id) {
		return this.members.containsKey(id);
	}
	
	public void saveMembersToFile(HashMap<String, MemberDTO> members) {
		try {
//			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			out.writeObject(members);
			out.flush();
			System.out.println("Members Save Success!");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
