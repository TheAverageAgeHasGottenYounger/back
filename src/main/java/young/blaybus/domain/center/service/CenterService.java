package young.blaybus.domain.center.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import young.blaybus.domain.address.Address;
import young.blaybus.domain.center.Center;
import young.blaybus.domain.center.controller.response.GetCenter;
import young.blaybus.domain.center.repository.CenterRepository;
import young.blaybus.domain.center.controller.request.CreateCenterRequest;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.enums.MemberRole;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.member.controller.request.CreateAdminRequest;

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
        Optional<List<Center>> centers = centerRepository.findByNameContaining(centerRequest.name());

        Center registerCenter = null;
        boolean isCenterRegister = false;
        if (centers.isPresent()) {
            for (Center center : centers.get()) {
                if (center.getName().equals(centerRequest.name())) {
                    registerCenter = center;
                    isCenterRegister = true;
                }
            }
        }

        // 해당 센터가 등록이 되어 있지 않을 경우
        if (!isCenterRegister) {
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
                    .address(new Address(centerRequest.city(), centerRequest.gu(), centerRequest.dong(), null))
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
                        .phoneNumber(adminRequest.phoneNumber())
                        .address(new Address(adminRequest.city(), adminRequest.gu(), adminRequest.dong(), null))
                        .carYn(adminRequest.carYn())
                        .profileUrl("")
                        .role(role)
                        .center(center)
                        .createdTime(now)
                        .build();

                memberRepository.save(adminMember);
            }
        } else {
            LocalDateTime now = LocalDateTime.now();

            // 회원 리스트
            List<Member> members = new ArrayList<>();
            Member member = memberRepository.findById(adminRequest.id()).orElse(null);
            if (member != null) members.add(member);

            if (member != null) {
                MemberRole role = MemberRole.ADMIN;
                Member adminMember = Member.builder()
                        .id(adminRequest.id())
                        .password(bCryptPasswordEncoder.encode(adminRequest.password()))
                        .phoneNumber(adminRequest.phoneNumber())
                        .address(new Address(adminRequest.city(), adminRequest.gu(), adminRequest.dong(), null))
                        .carYn(adminRequest.carYn())
                        .profileUrl("")
                        .role(role)
                        .center(registerCenter)
                        .createdTime(now)
                        .build();

                memberRepository.save(adminMember);
            }
        }
    }

    // 센터 이름으로 센터 등록 여부 조회
    public GetCenter isRegistrationCenterByName(String name) {
        Optional<List<Center>> centers = centerRepository.findByNameContaining(name);

        String centerName = null;
        if (centers.isPresent()) {
            for (Center center : centers.get()) {
                if (center.getName().equals(name)) centerName = center.getName();
            }
        }

        if (centerName != null) return new GetCenter("해당 센터는 현재 등록되어 있습니다.", true);
        return new GetCenter("해당 센터는 현재 등록되어 있지 않습니다.", false);
    }


}
