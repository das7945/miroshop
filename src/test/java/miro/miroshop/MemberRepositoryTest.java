package miro.miroshop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

// @RunWith(SpringRunner.class)은 JUnit 4의 어노테이션이므로
// 하단의 @ExtendWith(SpringExtension.class)교체함.
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberRepositoryTest {

  @Autowired MemberRepository memberReposiroty;

  @Test
  @Transactional
  @Rollback(false)
  //  테스트에서 트랜잭션을 사용하면 커밋을 안하는게 기본@Rollback(true)이므로
  //  @Rollback(false) 추가 해야 DB에서 확인할 수 있음.
  public void testMember() throws Exception {
    Member member = new Member();
    member.setUsername("memberA");

    Long saveId = memberReposiroty.save(member);
    Member findMember = memberReposiroty.find(saveId);

    Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    Assertions.assertThat(findMember).isEqualTo(member);
    //    System.out.println(findMember == member);
  }
}
