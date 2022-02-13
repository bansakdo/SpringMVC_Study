package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String save() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                          @RequestParam int price,
                          @RequestParam Integer quantity,
                          Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    // ModelAttribute에서 이름을 지정해주면, 지정된 이름으로 자동으로 model에 attribute로 추가해줌.
    // 이 때에 파라미터로 model을 받지 않아도 됨.
    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {

        itemRepository.save(item);
        //model.addAttribute("item", item);         // 자동 추가. 생략 가능

        return "basic/item";
    }

    // ModelAttribute에서 이름을 지정해주지 않는다면, 이름을 자동으로 지정해줌.
    // 클래스 명의 첫글자를 소문자로 바꿔준 이름. ex) Item -> item
   // @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    // ModelAttribute 생략 가능. 이름 변경하여 자동으로 model에 넣어주는 것은 동일
    //@PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId(); // PRG (Post Redirect Get)
    }

     @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
         Item savedItem = itemRepository.save(item);
         redirectAttributes.addAttribute("itemId", savedItem.getId());
         redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}"; // 여기에 넣지 않으면 파라미터. 즉, URL 뒤 ?에 붙어 온다.
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 10000, 20));
    }
}
