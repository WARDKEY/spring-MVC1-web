package hello.itemservice.web.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

	private final ItemRepository itemRepository;

	// DI됨, @RequiredArgConstructor로 대체 가능
	// @Autowired
	// public BasicItemController(ItemRepository itemRepository) {
	// 	this.itemRepository = itemRepository;
	// }

	// 상품 목록 조회
	@GetMapping
	public String items(Model model){
		// 모델을 매개변수로 받고
		// 리스트에 모든 상품 찾아서 담고
		List<Item> items = itemRepository.findAll();
		// 모델에 items라는 이름으로 담아준 다음
		model.addAttribute("items", items);
		// 논리적인 뷰 이름 리턴(위치는 resources/static/templates)
		return "basic/items";
	}

	// 상품 상세 조회
	@GetMapping("/{itemId}")
	public String item(@PathVariable Long itemId, Model model){
		// 매개변수로 모델과 상품 아이디를 받고
		// 상품 아이디로 해당 상품을 찾은 다음
		Item item = itemRepository.findById(itemId);
		// 모델에 item이라는 이름으로 찾은 상품 담아줌
		model.addAttribute("item", item);
		// 뷰 이름 리턴
		return "basic/item";
	}

	/**
	 * 테스트용 데이터 추가
	 */
	@PostConstruct
	public void init() {
		itemRepository.save(new Item("itemA", 10000, 10));
		itemRepository.save(new Item("itemB", 20000, 20));
	}
}
