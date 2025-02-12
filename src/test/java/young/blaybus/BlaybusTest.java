package young.blaybus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.repository.MemberRepository;

@SpringBootTest
public class BlaybusTest {

  @Autowired private MemberRepository memberRepository;

  @Test
  public void cryptoTest() {
    Member member = memberRepository.save(
      Member.builder()
        .id("id")
        .password("password")
        .profileUrl("profileUrl")
        .phoneNumber("010-1234-1234")
        .build()
    );

    System.out.println("member.getPhoneNumber() = " + member.getPhoneNumber());

  }

}
