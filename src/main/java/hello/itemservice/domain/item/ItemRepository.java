package hello.itemservice.domain.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

// 컴포넌트 스캔의 대상이 됨
@Repository
public class ItemRepository {

	// 실제론 HashMap 사용 안 함
	// **여러 개가 동시에 접근 불가**, 싱글톤으로 생성되고 여러 쓰레드가 동시에 접근함
	// 맵 사용하고 싶으면 ConcurrentHashMap() 사용
	private  static final Map<Long, Item> store = new HashMap<>();
	private static long sequence = 0L;

	// 상품을 저장하는 메서드
	public Item save(Item item){
		// 메서드를 호출할 때 아이디 값을 1씩 증가시키고
		item.setId(++sequence);
		// 그 증가시킨 아이디를 맵에 저장
		store.put(item.getId(), item);
		return item;
	}

	// 아이디로 상품 조회
	public Item findById(Long id){
		return store.get(id);
	}

	// 전체 조회
	public List<Item> findAll(){
		// 리스트로 반환하는데 store 맵 안에 있는 값들을 전부 넣음
		return new ArrayList<>(store.values());
	}

	// 상품 업데이트
	public void update(Long itemId, Item updateParam){
		// 먼저 아이디로 상품을 찾은 뒤
		Item findItem = findById(itemId);
		// 찾은 상품의 상태를 업데이트
		// 보통 DTO 생성하고 넣음
		findItem.setItemName(updateParam.getItemName());
		findItem.setPrice(updateParam.getPrice());
		findItem.setQuantity(updateParam.getQuantity());
	}

	// 저장되어 있는 상품 다 지우기
	public void clearStore() {
		// map의 함수인 clear()로 map에 저장된 것들 다 삭제
		store.clear();
	}

}
