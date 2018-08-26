package member;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MemberList {

	public MemberList(MemberDAO memberDAO) {
		HashMap<String, MemberDTO> members = memberDAO.getMembers();

		System.out.printf("등록된 회원 수: %d 명\n", members.size());

		Set<String> keySet = members.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			MemberDTO member = members.get(key);
			
			int count_rentaled = member.getBooks_rentaled() == null ? 0 : member.getBooks_rentaled().size();
			int count_reserved = member.getBooks_reserved() == null ? 0 : member.getBooks_reserved().size();
			
			System.out.printf("id: %s // pw: %s // name: %s // age: %s // phone: %s // address: %s // 빌린 책 수: %d // 예약한 책 수: %d\n", key,
					member.getPw(), member.getName(), member.getAge(), member.getPhoneNum(), member.getAddress(),
					count_rentaled, count_reserved);
		}

	}
}
