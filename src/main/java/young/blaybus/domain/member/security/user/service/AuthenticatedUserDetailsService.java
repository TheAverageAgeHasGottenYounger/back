package young.blaybus.domain.member.security.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.member.security.user.details.AuthenticatedUserDetails;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticatedUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null && Objects.equals(id, member.getId())) return new AuthenticatedUserDetails(member); // 인증 성공시 AuthenticatedUserDetails 반환

        // 회원이 없을시 UsernameNotFoundException 예외
        throw new UsernameNotFoundException("회원을 찾을 수 없습니다.");
    }
}
