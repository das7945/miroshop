/**
 *
 */
package miro.miroshop.service;

import static org.assertj.core.api.Assertions.fail;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import miro.miroshop.domain.Member;
import miro.miroshop.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired MemberService memberService;
  @Autowired MemberRepository memberRepository;

  @Test
  //  @Rollback(value = false)
  public void 회원가입() throws Exception {
    // given
    Member member = new Member();
    member.setName("Han");
    // when
    Long saveId = memberService.join(member);

    //then
    Assertions.assertThat(member).isEqualTo(memberRepository.findOne(saveId));

  }

  @Test
  public void 중복회원예외() throws Exception {
    // given
    Member member1 = new Member();
    member1.setName("han");

    Member member2 = new Member();
    member2.setName("han");

    // when
    memberService.join(member1);
    try {
      memberService.join(member2);
    } catch (IllegalStateException e) {
      return;
    }

    //then
    fail("예외가 발생 했습니다.");
  }

}
