package miro.miroshop.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import miro.miroshop.domain.Member;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

  // @PersistenceContext
  // JPA에서 제공하는 표준어노테이션
  private final EntityManager em;

  public void save(Member member) {
    em.persist(member);
  }

  public Member findOne(Long id) {
    return em.find(Member.class, id);
  }

  public List<Member> findAll() {
    return em.createQuery("select m from Member m", Member.class).getResultList();
  }

  public List<Member> findByName(String name){
    return em.createQuery("select m from Member m where m.name = :name", Member.class)
        .setParameter("name", name)
        .getResultList();
  }
}
