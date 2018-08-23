package member;

import java.io.Serializable;

public class MemberDTO implements Serializable {
	
	public static final int MAX_RENTAL_BOOK_COUNT = 3;
	
	
//	private int memberNum;
	private String id;
	private String pw;
	private String Name;
	private int age;
	private String phoneNum;
	private String address;
	private int countRentalBook;
	
	public MemberDTO() {
		
	}
	
	public MemberDTO(String id, String pw, String name, int age, String phoneNum, String address) { 
		this.id = id;
		this.pw = pw;
		Name = name;
		this.age = age;
		this.phoneNum = phoneNum;
		this.address = address;
		this.countRentalBook = 0;
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

	public int getCountRentalBook() {
		return countRentalBook;
	}

	public void setCountRentalBook(int countRentalBook) {
		this.countRentalBook = countRentalBook;
	}
	
	
	
}
