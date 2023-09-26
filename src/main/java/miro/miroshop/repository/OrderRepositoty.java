package miro.miroshop.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import miro.miroshop.domain.Order;

@Repository
@RequiredArgsConstructor
public class OrderRepositoty {

  private final EntityManager em;

  public void save(Order order) {
    em.persist(order);
  }

  public Order findOne(Long id) {
    return em.find(Order.class, id);
  }

  // ** 동적쿼리는 작성하는 첫번째 방법 ** //
  public List<Order> findAllByString(OrderSearch orderSearch) {

    String jpql = "select o from Order o join o.member m";
    boolean isFirstCondition = true; // 첫번째 검색이라는

    // 주문 상태 검색
    if (orderSearch.getOrderStatus() != null) {
      if (isFirstCondition) {
        jpql += " where"; // 첫번째 검색이 맞다
        isFirstCondition = false;
      } else {
        jpql += " and"; // 두번째 검색이라면
      }
      jpql += " o.status = :status";
    }

    // 회원 이름 검색
    if (StringUtils.hasText(orderSearch.getMemberName())) {
      if (isFirstCondition) {
        jpql += " where";
        isFirstCondition = false;
      } else {
        jpql += " and";
      }
      jpql += " m.name like :name";
    }

    TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000); // 최대: sql 조회시
    // 최대 1000개까지
    // 조회

    if (orderSearch.getOrderStatus() != null) {
      query = query.setParameter("status", orderSearch.getOrderStatus());
    }
    if (StringUtils.hasText(orderSearch.getMemberName())) {
      query = query.setParameter("name", orderSearch.getMemberName());
    }

    return query.getResultList();
  }
  // ** 동적쿼리는 작성하는 첫번째 방법 ** //



  // ** 동적쿼리는 작성하는 두번째 방법 ** //
  // JPA 표준 스펙의 Criteria
  // 단점: 코드를 보고 어떤 SQL문인지 이해하기 어렵기 때문에 유지보수가 힘들다.
  public List<Order> findByCriteria(OrderSearch orderSearch) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Order> cq = cb.createQuery(Order.class);

    Root<Order> o = cq.from(Order.class);
    Join<Object, Object> m = o.join("member", JoinType.INNER);

    List<Predicate> criteria = new ArrayList<>();

    // 주문 상태 검색
    if (orderSearch.getOrderStatus() != null) {
      Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
      criteria.add(status);
    }

    // 회원 이름 검색
    if (StringUtils.hasText(orderSearch.getMemberName())) {
      Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
      criteria.add(name);
    }

    cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
    TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
    return query.getResultList();
  }
  // ** 동적쿼리는 작성하는 두번째 방법 ** //



  // return em.createQuery("select o from Order o join o.member m" +
  // "where o.status = :status and m.name like :name", Order.class)
  // .setParameter("status", orderSearch.getOrderStatus())
  // .setParameter("name", orderSearch.getMemberName())
  // // .setFirstResult(100) // 시작: sql 조회시 100번째부터 조회
  // .setMaxResults(1000) // 최대: sql 조회시 최대 1000개까지 조회
  // .getResultList();

}