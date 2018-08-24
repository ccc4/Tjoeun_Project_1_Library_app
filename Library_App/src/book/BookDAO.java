package book;

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
import java.util.HashMap;

public class BookDAO {
	private final String DIRNAME = ".\\init";
	private final String FILENAME = "Books.txt";
	
	int idx;
	
	HashMap<String, BookDTO> books;
	File file;
	
	public HashMap<String, BookDTO> getBooks() {
		return books;
	}
	public void setBooks(HashMap<String, BookDTO> books) {
		this.books = books;
	}
	
	public BookDAO() {
		try {
			File dir = new File(DIRNAME);
			if(!dir.exists()) dir.mkdirs();
			this.file = new File(dir, FILENAME);
			if(!file.exists()) file.createNewFile();
			
			BufferedReader check = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			if(check.readLine() == null) {
				books = new HashMap<>();
				System.out.println("파일없어서 새로 만듦");
				saveBooksToFile(books);
//				idx = 0;
			} else {
				ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
				books = (HashMap<String, BookDTO>) in.readObject();
				in.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkExist(String bookName) {
		return this.books.containsKey(bookName);
	}
	
	public void addBook(String bookName, BookDTO newBook) {
//		newBook.setIdx(++idx);
		this.books.put(bookName, newBook);
		saveBooksToFile(this.books);
	}
	
	public void saveBooksToFile(HashMap<String, BookDTO> books) {
		try {
//			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			ObjectOutputStream bookOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			bookOut.writeObject(books);
			bookOut.flush();
			
			// 이미지 저장 구현할 곳
			
			System.out.println("Books Save Success!");
			bookOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
