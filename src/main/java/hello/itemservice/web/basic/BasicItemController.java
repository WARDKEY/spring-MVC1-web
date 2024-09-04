package hello.itemservice.web.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	// 상품 등록 폼 이동
	@GetMapping("/add")
	public String addForm(){

		return "basic/addForm";
	}

	// 상품 등록 처리
	// form-data 형식
	// @PostMapping("/add")
	// 매개변수 이름은 html의 input에 name을 확인
	public String addItemV1(@RequestParam String itemName, @RequestParam int price, @RequestParam Integer quantity, Model model){
		// 파라미터가 오면 item 객체 생성하여 값 담아준 다음
		Item item = new Item();
		item.setItemName(itemName);
		item.setPrice(price);
		item.setQuantity(quantity);

		// 그 item 객체를 저장
		itemRepository.save(item);

		model.addAttribute("item", item);

		return "basic/item";
	}

	// 상품 등록 처리
	/**
	 * @ModelAttribute("item") Item item
	 * model.addAttribute("item", item); 자동 추가 */
	// @PostMapping("/add")
	// 매개변수 이름은 html의 input에 name을 확인
	public String addItemV2(@ModelAttribute("item") Item item, Model model){

		// @ModelAttribute 사용하면 item 객체 자동 생성

		itemRepository.save(item);
		// model.addAttribute("item", item);	// 자동 추가되어 생략 가능

		return "basic/item";
	}

	// 상품 등록 처리
	/**
	 * @ModelAttribute name 생략 가능
	 * model.addAttribute(item); 자동 추가, 생략 가능
	 * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item */
	// @PostMapping("/add")
	// 매개변수 이름은 html의 input에 name을 확인
	public String addItemV3(@ModelAttribute Item item){
		// 이름값을 지우면 클래스명인 Item에서 첫 글자를 소문자로 바꾼 item이 이름이 된다.
		// @ModelAttribute 사용하면 item 객체 자동 생성

		itemRepository.save(item);
		// model.addAttribute("item", item);	// 자동 추가되어 생략 가능

		return "basic/item";
	}

	// 상품 등록 처리
	/**
	 * @ModelAttribute 자체 생략 가능
	 * model.addAttribute(item) 자동 추가 */
	// @PostMapping("/add")
	// 매개변수 이름은 html의 input에 name을 확인
	public String addItemV4(Item item){
		// @ModelAttribute를 생략하면 파라미터가 객체인 경우 자동으로 @ModelAttribute 적용
		// 이름값을 지우면 클래스명인 Item에서 첫 글자를 소문자로 바꾼 item이 이름이 된다.
		// @ModelAttribute 사용하면 item 객체 자동 생성

		itemRepository.save(item);
		// model.addAttribute("item", item);	// 자동 추가되어 생략 가능

		return "basic/item";
	}

	/**
	 * PRG - Post/Redirect/Get
	 */
	// @PostMapping("/add")
	// 매개변수 이름은 html의 input에 name을 확인
	public String addItemV5(Item item){
		// @ModelAttribute를 생략하면 파라미터가 객체인 경우 자동으로 @ModelAttribute 적용
		// 이름값을 지우면 클래스명인 Item에서 첫 글자를 소문자로 바꾼 item이 이름이 된다.
		// @ModelAttribute 사용하면 item 객체 자동 생성

		itemRepository.save(item);
		// model.addAttribute("item", item);	// 자동 추가되어 생략 가능

		// + item.getId()를 하면 URL 인커딩이 안 되어 위험하다.
		return "redirect:basic/items/" + item.getId();
	}

	/**
	 * RedirectAttributes
	 */
	@PostMapping("/add")
	// 매개변수 이름은 html의 input에 name을 확인
	public String addItemV6(Item item, RedirectAttributes redirectAttributes){
		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);

		// redirectAttribute에 넣은 값이 {itemId}에 들어감
		// 나머지는 쿼리 파라미터 형식으로 들어간다.
		return "redirect:/basic/items/{itemId}";
	}

	// 상품 수정 폼 이동
	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable Long itemId, Model model){
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/editForm";
	}

	// 상품 수정 처리
	@PostMapping("/{itemId}/edit")
	public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
		itemRepository.update(itemId, item);
		return "redirect:/basic/items/{itemId}";
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
