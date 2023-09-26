package miro.miroshop.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import miro.miroshop.domain.Delivery;
import miro.miroshop.domain.Member;
import miro.miroshop.domain.Order;
import miro.miroshop.domain.OrderItem;
import miro.miroshop.domain.item.Item;
import miro.miroshop.repository.ItemRepository;
import miro.miroshop.repository.MemberRepository;
import miro.miroshop.repository.OrderRepositoty;
import miro.miroshop.repository.OrderSearch;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepositoty orderRepositoty;
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;


  // 주문 //
  @Transactional
  public Long order(Long memberId, Long itemId, int count) {

    // 엔티티 조회
    Member member = memberRepository.findOne(memberId);
    Item item = itemRepository.findOne(itemId);

    // 배송정보 생성
    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());

    // 주문상품 생성
    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

    // 주문 생성
    Order order = Order.createOrder(member, delivery, orderItem);

    // 주문 저장
    orderRepositoty.save(order);

    return order.getId();
  }

  // 주문 취소
  @Transactional
  public void cancelOrder(Long orderId) {
    // 주문 엔티티 조회
    Order order = orderRepositoty.findOne(orderId);
    // 주문 취소
    order.cancel();
  }

  // 검색
  public List<Order> findOrders(OrderSearch orderSearch) {
    return orderRepositoty.findAllByString(orderSearch);
  }
}


