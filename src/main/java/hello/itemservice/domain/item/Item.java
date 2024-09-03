package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// @Getter
// @Setter
// @Data는 잘 알고 써야 됨, 보통 @Getter, @Setter 사용
@Data
public class Item {
	private Long id;
	private String itemName;
	// Integer는 null값 저장 가능
	private Integer price;
	private Integer quantity;

	// 기본 생성자 생성
	public Item() {
	}

	// 아이디를 제외한 생성자 생성
	public Item(String itemName, Integer price, Integer quantity) {
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;
	}
}
