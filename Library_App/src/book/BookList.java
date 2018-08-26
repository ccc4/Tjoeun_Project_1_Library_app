package book;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class BookList {

	public BookList(BookDAO bookDAO) {
		HashMap<String, BookDTO> books = bookDAO.getBooks();

		System.out.printf("��ϵ� å �� ��: %d ��\n", books.size());

		Set<String> keySet = books.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			BookDTO book = books.get(key);
			System.out.printf("å ����: %s // ����: %s // ����: %s // ���������: %s // �����ѻ��: %s\n", key, book.getBookAuthor(),
					book.getBookState(), book.getRentaledByWho(), book.getReservedByWho());
		}

	}
}
