package miro.miroshop.service;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import miro.miroshop.domain.Address;
import miro.miroshop.domain.Member;
import miro.miroshop.domain.Order;
import miro.miroshop.domain.OrderStatus;
import miro.miroshop.domain.item.Book;
import miro.miroshop.domain.item.Item;
import miro.miroshop.exception.NotEnoughStockException;
import miro.miroshop.repository.OrderRepositoty;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

  @Autowired
  EntityManager em;
  @Autowired
  OrderService orderService;
  @Autowired
  OrderRepositoty orderRepositoty;

  @Test
  // @Rollback(value = false)
  public void 상품주문() throws Exception {
    // given
    Member member = createMember();

    Item items = createBook("JPA", 10000, 10);

    // Book book2 = new Book();
    // book2.setName("스프링2");
    // book2.setPrice(10000);
    // book2.setStockQuantity(10);
    // em.persist(book2);

    int orderCount = 2;

    // when
    Long orderId = orderService.order(member.getId(), items.getId(), orderCount);

    // then
    Order getOrder = orderRepositoty.findOne(orderId);

    Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
    Assertions.assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
    Assertions.assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
    Assertions.assertEquals(8, items.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
  }

  @Test
  //  @Rollback(value = false)
  public void 상품주문_재고수량초과() throws Exception {
    // given
    Member member = createMember();
    Item items = createBook("JPA", 10000, 10);

    int orderCount = 11;

    // when
    NotEnoughStockException exception = Assertions.assertThrows(
        NotEnoughStockException.class,
        () -> orderService.order(member.getId(), items.getId(), orderCount),
        "재고 수량 부족으로 예외가 발생해야함."
        );

    // then
    //    String errorMessage = exception.getMessage(); // 예외 메시지 가져오기
    //    Assertions.assertEquals("재고가 부족합니다.", errorMessage, "예외 메시지 확인");
    // 다른 예외 속성을 검증할 수도 있음

    //    // when
    //    orderService.order(member.getId(), items.getId(), orderCount);
    //
    //    // then
    //    fail("재고 수량 부족으로 예외가 발생해야함.");

  }


  @Test
  public void 주문취소() throws Exception {
    // given
    Member member = createMember();
    Book item = createBook("JPA", 10000, 10);

    int orderCount = 2;

    Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

    orderService.cancelOrder(orderId);

    Order getOrder = orderRepositoty.findOne(orderId);

    Assertions.assertEquals(OrderStatus.CANCEL,  getOrder.getStatus(),"주문 취소시 상태는 CANCEL 이다.");
    Assertions.assertEquals(10, item.getStockQuantity(),"주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");

  }


  private Book createBook(String name, int price, int StockQuantity) {
    Book book = new Book();
    book.setName(name);
    book.setPrice(price);
    book.setStockQuantity(StockQuantity);
    em.persist(book);
    return book;
  }


  private Member createMember() {
    Member member = new Member();
    member.setName("회원1");
    member.setAddress(new Address("서울", "강가", "000-000"));
    em.persist(member);
    return member;
  }

}
