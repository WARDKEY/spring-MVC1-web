package hello.itemservice.domain.item;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemRepositoryTest {

	ItemRepository itemRepository = new ItemRepository();

	// 테스트가 끝날때마다 내용을 전부 지워줘야 함
	@AfterEach
	void afterEach(){
		itemRepository.clearStore();
	}

	@DisplayName("상품 저장 테스트")
	@Test
	void save(){
		//given
		// 테스트할 상품을 생성하고
		Item item = new Item("itemA", 10000, 10);
		//when
		// 상품을 저장한 뒤
		Item savedItem = itemRepository.save(item);

		//then
		// 저장한 상품의 아이디를 통해 상품을 찾고
		Item findItem = itemRepository.findById(item.getId());
		// 찾은 상품과 저장된 상품이 동일한지 확인
		assertThat(findItem).isEqualTo(savedItem);
	}

	@DisplayName("모든 상품 조회 테스트")
	@Test
	void findAll(){
		//given
		// 테스트할 상품들 생성
		Item item1 = new Item("item1", 10000, 10);
		Item item2 = new Item("item2", 20000, 20);

		// 상품들 저장
		itemRepository.save(item1);
		itemRepository.save(item2);

		//when
		// findAll()을 통해 모든 상품 조회
		List<Item> result = itemRepository.findAll();

		//then
		// 저장된 상품의 크기가 맞는지 확인
		assertThat(result.size()).isEqualTo(2);
		// 저장된 상품에 해당 상품이 포함되어 있는지 확인
		assertThat(result).contains(item1, item2);
	}

	@DisplayName("상품 수정 테스트")
	@Test
	void updateItem(){
		//given
		// 테스트할 상품 생성
		Item item = new Item("item1", 10000, 10);

		// 상품 저장 및 저장한 상품 아이디 저장
		Item savedItem = itemRepository.save(item);
		Long itemId = savedItem.getId();

		//when
		// 수정된 상품 생성
		Item updateParam = new Item("item2", 20000, 30);
		// 수정할 상품의 아이디를 통해 상품을 찾고 수정된 상품으로 수정
		itemRepository.update(itemId, updateParam);

		//then
		// 수정된 상품을 조죄
		Item findItem = itemRepository.findById(itemId);

		// 조회한 상품이 수정된 상품과 같은지 확인
		assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
		assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
		assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
	}
}