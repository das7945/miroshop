package miro.miroshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

  @Id @GeneratedValue
  @Column(name = "delivery_id")
  private Long id;

  @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
  private Order order;

  @Embedded
  private Address address;

  @Enumerated(EnumType.STRING)
  // Enum타입을 사용 할 경우 상단의 어노테이션을 무조건 써줘야함.
  // STRING을 사용하고 이유는 테이블생성 시 Enum클래스의 객체이름 그대로 쓰기때문
  //
  // ORDINAL을 사용할 경우 테이블생성시 먼저 있는 객체 순서대로 0이라는 이름으로 테이블에 저장됨.
  // 그러므로 차후 수정과정에서 레디와 콤프사이에 무언가 생길경우 기존에 있던 0,1이 아닌
  // 0, 1 ,2가 되므로 레디가 0, 임의의 테이블이 1, 콤프가 2가되므로 오류가 발생.
  private DeliveryStatus status; // READY, COMP



}
