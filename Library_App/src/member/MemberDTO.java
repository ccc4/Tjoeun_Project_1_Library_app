package member;

import java.io.Serializable;
import java.util.HashMap;

import book.BookDTO;

public class MemberDTO implements Serializable {
	
	public static final int MAX_RENTAL_BOOK_COUNT = 3;
	
	
//	private int memberNum;
	private String id;
	private String pw;
	private String Name;
	private int age;
	private String phoneNum;
	private String address;
	private HashMap<String, BookDTO> books_rentaled;
	private HashMap<String, BookDTO> books_reserved;
	
	
	public MemberDTO(String id, String pw, String name, int age, String phoneNum, String address,
			HashMap<String, BookDTO> books_rentaled, HashMap<String, BookDTO> books_reserved) {
		this.id = id;
		this.pw = pw;
		this.Name = name;
		this.age = age;
		this.phoneNum = phoneNum;
		this.address = address;
		this.books_rentaled = books_rentaled;
		this.books_reserved = books_reserved;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public HashMap<String, BookDTO> getBooks_rentaled() {
		return books_rentaled;
	}

	public void setBooks_rentaled(HashMap<String, BookDTO> books_rentaled) {
		this.books_rentaled = books_rentaled;
	}

	public HashMap<String, BookDTO> getBooks_reserved() {
		return books_reserved;
	}

	public void setBooks_reserved(HashMap<String, BookDTO> books_holdOn) {
		this.books_reserved = books_holdOn;
	}
	
	
	
}
