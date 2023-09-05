package miro.miroshop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

  @Autowired MemberRepository memberReposiroty;

  @Test
  @Transactional
  //  @Rollback(false)
  public void testMember() throws Exception {
    Member member = new Member();
    member.setUsername("memberA");

    Long saveId = memberReposiroty.save(member);
    Member findMember = memberReposiroty.find(saveId);

    Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
  }
}
