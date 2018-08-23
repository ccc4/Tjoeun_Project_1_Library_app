package book;

public class BookDTO {
	public static final int RENTABLE = -2;
	public static final int RETURN_LEFT = -1;
	public static final int RETURN_DAY = 0;
	public static final int MIN_OVERDUE_DAY = 1;
	
	
	private String bookName;
	private String bookWriter;
	private String bookImgName;
	private int bookState;
	private String rentaledByWho;
	
	public BookDTO(String bookName, String bookWriter, String bookImgName, int bookState, String rentaledByWho) {
		super();
		this.bookName = bookName;
		this.bookWriter = bookWriter;
		this.bookImgName = bookImgName;
		this.bookState = bookState;
		this.rentaledByWho = rentaledByWho;
	}
	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookWriter() {
		return bookWriter;
	}
	public void setBookWriter(String bookWriter) {
		this.bookWriter = bookWriter;
	}
	public String getBookImgName() {
		return bookImgName;
	}
	public void setBookImgName(String bookImgName) {
		this.bookImgName = bookImgName;
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
