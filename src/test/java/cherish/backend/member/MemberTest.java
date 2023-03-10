package cherish.backend.member;

import cherish.backend.member.dto.MemberFormDto;
import cherish.backend.member.sub.Gender;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootTest
class MemberTest {

    @Autowired
    public MemberRepository memberRepository;

    @Autowired
    public EntityManager em;
    @Autowired
    public PasswordEncoder passwordEncoder ;

    @Test
    public void 회원가입(){
        //given
        MemberFormDto memberFormDto = getMemberFormDto();
        //when
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        Member savedMember = memberRepository.save(member);
        //then
        Assertions.assertThat(savedMember).isEqualTo(member);
        Assertions.assertThat(savedMember.getId()).isEqualTo(member.getId());
    }

    private static MemberFormDto getMemberFormDto() {
        MemberFormDto memberFormDto = new MemberFormDto("지수빈", "공주", "test@naver.com", "1234", true, Gender.None, LocalDateTime.now(), "공주");
        return memberFormDto;
    }

    @Test
    public void 비밀번호_변경(){
        MemberFormDto memberFormDto = getMemberFormDto();
        Member member = Member.createMember(memberFormDto,passwordEncoder);
        Member savedMember = memberRepository.save(member);
//        Long id = savedMember.getId();
        member.changePwd("12345",passwordEncoder);
        Assertions.assertThat(member.getPassword()).isEqualTo(savedMember.getPassword());
    }

}