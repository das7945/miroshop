package miro.miroshop.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import miro.miroshop.domain.item.Item;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

  private final EntityManager em;

  public void save(Item item) {

    if (item.getId() == null) {
      // item은 JPA저장하기 전까지 id값이 없기때문에 Null값이면 새로운 객체로 신규저장을 의미하고
      em.persist(item);
    } else {
      // 만약 item이 존재하는 id값이 있다면 먼저 만들어진 data에 덮어 씌움.
      em.merge(item);
    }
  }

  public Item findOne(Long id) {
    return em.find(Item.class, id);
  }

  public List<Item> findAll() {
    return em.createQuery("select i from Item i", Item.class).getResultList();
  }

}
