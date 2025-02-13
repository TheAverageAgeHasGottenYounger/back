package young.blaybus.domain.center.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import young.blaybus.domain.center.Center;
import young.blaybus.domain.center.repository.CenterRepository;
import young.blaybus.domain.center.request.CreateCenterRequest;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.enums.MemberRole;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.member.request.CreateAdminRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CenterService {

    private final CenterRepository centerRepository;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 센터 등록
    @Transactional(rollbackOn = Exception.class)
    public void registerCenter(CreateCenterRequest centerRequest, CreateAdminRequest adminRequest) {
        LocalDateTime now = LocalDateTime.now();

        // 회원 리스트
        List<Member> members = new ArrayList<>();
        Member member = memberRepository.findById(adminRequest.id()).orElse(null);
        if (member != null) members.add(member);

        Center center = Center.builder()
                .name(centerRequest.name())
                .bathCarYn(centerRequest.bathCarYn())
                .grade(centerRequest.grade())
                .operationPeriod(centerRequest.operationPeriod())
                .address(centerRequest.address())
                .introduction(centerRequest.introduction())
                .createdTime(now)
                .memberList(members)
                .build();

        centerRepository.save(center);

        if (member != null) {
            MemberRole role = MemberRole.ADMIN;
            Member adminMember = Member.builder()
                    .id(adminRequest.id())
                    .password(bCryptPasswordEncoder.encode(adminRequest.password()))
                    .name(adminRequest.name())
                    .phoneNumber(adminRequest.phoneNumber())
                    .address(adminRequest.address())
                    .carYn(adminRequest.carYn())
                    .profileUrl("")
                    .role(role)
                    .center(center)
                    .createdTime(now)
                    .build();
            memberRepository.save(adminMember);
        }
    }

    // 센터 이름으로 센터 검색
    public Center findCenterName(String name) {
        Optional<Center> center = centerRepository.findByNameContaining(name);
        return center.orElse(null);
    }
}
