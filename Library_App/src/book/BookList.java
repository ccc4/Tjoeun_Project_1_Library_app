package book;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class BookList {

	public BookList(BookDAO bookDAO) {
		HashMap<String, BookDTO> books = bookDAO.getBooks();

		System.out.printf("등록된 책 권 수: %d 권\n", books.size());

		Set<String> keySet = books.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			BookDTO book = books.get(key);
			System.out.printf("책 제목: %s // 저자: %s // 상태: %s // 빌려간사람: %s // 예약한사람: %s\n", key, book.getBookAuthor(),
					book.getBookState(), book.getRentaledByWho(), book.getReservedByWho());
		}

	}
}
