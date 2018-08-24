package book;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class BookList {
	
	public BookList() {
		File dir = new File(".\\init");
		File file = new File(dir, "Books.txt");
		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			HashMap<String, BookDTO> books = (HashMap<String, BookDTO>) in.readObject();
			
			System.out.printf("��ϵ� å �� ��: %d ��\n", books.size());
			
			Set<String> keySet = books.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			while(keyIterator.hasNext()) {
				String key = keyIterator.next();
				BookDTO book = books.get(key);
				System.out.printf("å ����: %s // ����: %s // ����: %s // ���������: %s\n", 
						key, book.getBookAuthor(), book.getBookState(), book.getRentaledByWho());
			}
			
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		new BookList();
	}
}
