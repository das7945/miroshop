package miro.miroshop.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import miro.miroshop.domain.Member;
import miro.miroshop.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
// readOnly = true옵션을 적용 할 경우 이름과 같이 읽기전용을 의미하며 그에대한 **최적화**를 의미함.
// 단. set과 같이 쓰기에 적용 할 경우 수정이 안되므로 주의해야함.
// 가급적 읽기에 경우 적용해주며, 상황에 따라 유연하게 사용해야함.
@RequiredArgsConstructor
// final이 있는 필드만 가지고 생성자를 만들어줌 하단의 표시된 부분이 해당됨.
public class MemberService {

  // @Autowired(필드인젝션방법)
  // //스프링 빈에 등록된 MemberRepository 인젝션해줌
  // private MemberRepository memberRepository;

  private final MemberRepository memberRepository;

  // **@RequiredArgsConstructor** 사용 할 경우 자동생성
  // public MemberService(MemberRepository memberRepository) {
  // this.memberRepository= memberRepository;
  // }

  // 회원가입
  @Transactional
  // 위와같이 별도 지정 할 경우 해당 메서드는 우선순위가 되며 클래스에 적용된 옵션값이 적용이 안됨.
  public Long join(Member member) {
    validateDuplicateMember(member); // 중복회원체크
    memberRepository.save(member);

    return member.getId();
  }

  private void validateDuplicateMember(Member member) {
    List<Member> findMembers = memberRepository.findByName(member.getName());
    if (!findMembers.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }

  // 회원 전체조회

  public List<Member> findMembers() {
    return memberRepository.findAll();
  }


  public Member findOne(Long memberId) {
    return memberRepository.findOne(memberId);
  }

}
