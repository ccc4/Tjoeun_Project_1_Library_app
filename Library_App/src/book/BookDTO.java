package book;

import java.io.Serializable;

public class BookDTO implements Serializable {
	
	public static final String BOOK_DIR = ".\\init\\BookImages";
	
	public static final int RENTABLE = -2; 			// 대여가능
	public static final int RETURN_LEFT = -1; 		// 반납 하루 남음
	public static final int RETURN_DAY = 0; 		// 반납일
	public static final int MIN_OVERDUE_DAY = 1; 	// 연체 시작
	
//	private int idx;
	private String bookName;
	private String bookAuthor;
	private int bookState;
	private String rentaledByWho;
	
	public BookDTO(String bookName, String bookAuthor, int bookState, String rentaledByWho) {
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookState = bookState;
		this.rentaledByWho = rentaledByWho;
	}
	
	
//	public int getIdx() {
//		return idx;
//	}
//	public void setIdx(int idx) {
//		this.idx = idx;
//	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public int getBookState() {
		return bookState;
	}
	public void setBookState(int bookState) {
		this.bookState = bookState;
	}
	public String getRentaledByWho() {
		return rentaledByWho;
	}
	public void setRentaledByWho(String rentaledByWho) {
		this.rentaledByWho = rentaledByWho;
	}
	
}
