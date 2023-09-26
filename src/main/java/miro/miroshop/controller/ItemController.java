package miro.miroshop.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.RequiredArgsConstructor;
import miro.miroshop.domain.item.Book;
import miro.miroshop.domain.item.Item;
import miro.miroshop.service.ItemService;

@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/items/new")
  private String crateForm(Model model) {
    model.addAttribute("form", new BookForm());
    return "items/createItemForm";
  }

  @PostMapping("/items/new")
  private String create(BookForm form) {
    Book book = new Book();
    book.setName(form.getName());
    book.setPrice(form.getPrice());
    book.setStockQuantity(form.getStockQuantity());
    book.setAuthor(form.getAuthor());
    book.setIsbn(form.getIsbn());


    itemService.saveItem(book);
    return "redirect:/";
  }

  @GetMapping("/items")
  public String list(Model model) {
    List<Item> items = itemService.findItems();
    model.addAttribute("items", items);
    return "items/itemList";
  }

  @GetMapping("/items/{itemId}/edit")
  public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
    Book item = (Book) itemService.findOne(itemId);

    BookForm form = new BookForm();
    form.setId(item.getId());
    form.setName(item.getName());
    form.setPrice(item.getPrice());
    form.setStockQuantity(item.getStockQuantity());
    form.setAuthor(item.getAuthor());
    form.setIsbn(item.getIsbn());

    model.addAttribute("form", form);
    return "items/updateItemForm";
  }

  @PostMapping("/items/{itemId}/edit")
  // @ModelAttribute("form")는 form이라는 이름을 통해
  // 타임리프 구문중 <form th:object="${form}" method="post">에서 ${form}과 동일한 이름을 통해
  // 객체를 받음.
  public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {

    itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
    //    System.out.println("디비에서 꺼내온 book 객체: " + form.getName());
    //    Book book = new Book();
    //    book.setId(form.getId());
    //    book.setName(form.getName());
    //    book.setPrice(form.getPrice());
    //    book.setStockQuantity(form.getStockQuantity());
    //    book.setAuthor(form.getAuthor());
    //    book.setIsbn(form.getIsbn());
    //
    //    itemService.saveItem(book);
    //    System.out.println("수정되어 다시 저장하는 book 객체: " + form.getName());
    return "redirect:/items";
  }
}
