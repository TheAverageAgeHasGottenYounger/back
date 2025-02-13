package young.blaybus.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import young.blaybus.domain.certificate.Certificate;
import young.blaybus.domain.certificate.enums.CertificateGrade;
import young.blaybus.domain.certificate.enums.CertificateType;
import young.blaybus.domain.certificate.repository.CertificateRepository;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.enums.MemberRole;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.member.request.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * MemberService : 회원에 대한 서비스 제공
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CertificateRepository certificateRepository;

    // 회원 등록
    @Transactional(rollbackOn = Exception.class)
    public void registerMember(MemberRequest memberRequest) {
        // 현재 시간
        LocalDateTime now = LocalDateTime.now();
        now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Member member = null;
        if (memberRequest.role().equals(MemberRole.ADMIN.name().toLowerCase())) {
            MemberRole role = MemberRole.ADMIN;
            member = Member.builder()
                    .id(memberRequest.id())
                    .password(memberRequest.password())
                    .name(memberRequest.name())
                    .phoneNumber(memberRequest.phoneNumber())
                    .address(memberRequest.address())
                    .profileUrl("")
                    .role(role)
                    .createdTime(now)
                    .build();
        } else {
            MemberRole role = MemberRole.WORKER;
            member = Member.builder()
                    .id(memberRequest.id())
                    .password(memberRequest.password())
                    .name(memberRequest.name())
                    .phoneNumber(memberRequest.phoneNumber())
                    .address(memberRequest.address())
                    .profileUrl("")
                    .role(role)
                    .carYn(memberRequest.carYn())
                    .dementiaEducationYn(memberRequest.dementiaEducationYn())
                    .career(memberRequest.career())
                    .introduction(memberRequest.introduction())
                    .careerPeriod(memberRequest.careerPeriod())
                    .createdTime(now)
                    .build();
        }

        // DB에 데이터 영구 저장
        memberRepository.save(member);

        Member finalMember = member;
        memberRequest.certificate().forEach(c -> {
            CertificateType type = switch (c.getType()) {
                case "요양보호사" -> CertificateType.CARE;
                case "사회복지사" -> CertificateType.SOCIAL;
                case "간호조무사" -> CertificateType.NURSE;
                default -> null;
            };

            CertificateGrade grade = switch (c.getGrade()) {
                case "1급" -> CertificateGrade.GRADE1;
                case "2급" -> CertificateGrade.GRADE2;
                default -> null;
            };

            Certificate certificate = Certificate.builder()
                    .type(type)
                    .number(c.getNumber())
                    .grade(grade)
                    .member(finalMember)
                    .createdTime(now)
                    .build();

            certificateRepository.save(certificate);
        });
    }

    // 회원 아이디 중복 체크
    public String duplicationIdCheck(String memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member != null) return null;
        return "OK";
    }

    // 회원 이름 중복 체크
    public String duplicationNameCheck(String memberName) {
        List<Member> member = memberRepository.findByName(memberName).orElse(null);
        assert member != null;
        if (!member.isEmpty()) return null;
        return "OK";
    }

    // 관리자

}
