package miro.miroshop.repository;

import lombok.Getter;
import lombok.Setter;
import miro.miroshop.domain.OrderStatus;


@Getter
@Setter
public class OrderSearch {

  private String memberName;  // 회원 이름
  private OrderStatus OrderStatus; // 주문 상태 [ORDER, CANCEL]
}
