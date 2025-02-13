package young.blaybus.domain.member.service;

import jakarta.servlet.http.Cookie;
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

    // 임시 데이터를 저장할 Map
    private final Map<String, MemberRequest> tempMember = new HashMap<>();

    // 요양보호사인지 관리자인지 체크 (프론트에서 이렇게 요청해야함 : 요양보호사 => worker, 관리자 => admin)
    public void memberTypeCheck(String type, HttpServletResponse response) {
        MemberRole role = type.equals("admin") ? MemberRole.ADMIN : MemberRole.WORKER;
        String tempId = UUID.randomUUID().toString(); // 임시 데이터를 저장하기 위해 필요한 아이디
        tempMember.put(tempId, new MemberRequest(new RoleRequest(role), null, null, null));

        // 지속적으로 tempId를 받아서 진행해야하기 때문에 쿠키로 관리하고 이 쿠키는 회원가입이 완료되면 삭제
        Cookie cookie = new Cookie("TI", tempId);
        cookie.setPath("/");
        response.addCookie(cookie);

        System.out.println(tempMember.values());

        log.info("Role 데이터 임시 저장 완료");
    }

    // 스탭별로 회원가입 진행 (제일 끝 단계는 회원가입을 진행 -> DB에 저장)
    // 요양보호사
    public void workerJoinFirstStep(HttpServletRequest request, MemberFirstStepRequest memberRequest) {
        String tempId = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("TI")) tempId = cookie.getValue();
        }

        // 1차 정보 수집
        tempMember.get(tempId).setRequestFirstStep(memberRequest);
        System.out.println(tempMember.values());
    }

    public void workerJoinSecondStep(HttpServletRequest request, MemberSecondStepRequest memberRequest) {
        String tempId = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("TI")) tempId = cookie.getValue();
        }

        // 2차 정보 수집
        tempMember.get(tempId).setRequestSecondStep(memberRequest);
        System.out.println(tempMember.values());
    }

    public MemberRequest workerJoinThirdStep(HttpServletRequest request, MemberThirdStepRequest memberRequest) {
        String tempId = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("TI")) tempId = cookie.getValue();
        }

        // 3차 정보 수집
        tempMember.get(tempId).setRequestThirdStep(memberRequest);
        System.out.println(tempMember.values());

        return tempMember.get(tempId);
    }

    // 회원 등록
    @Transactional(rollbackOn = Exception.class)
    public void registerMember(MemberRequest memberRequest, HttpServletRequest request, HttpServletResponse response) {
        Member member = null;
        LocalDateTime now = LocalDateTime.now();
        now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        switch (memberRequest.getRoleRequest().role()) {
            case WORKER: {
                member = Member.builder()
                        .id(memberRequest.getRequestSecondStep().id())
                        .password(memberRequest.getRequestSecondStep().password())
                        .name(memberRequest.getRequestFirstStep().name())
                        .phoneNumber(memberRequest.getRequestFirstStep().phoneNumber())
                        .address(memberRequest.getRequestFirstStep().address())
                        .profileUrl("")
                        .role(memberRequest.getRoleRequest().role())
                        .carYn(memberRequest.getRequestThirdStep().carYn())
                        .dementiaEducationYn(memberRequest.getRequestThirdStep().dementiaEducationYn())
                        .career(memberRequest.getRequestThirdStep().career())
                        .introduction(memberRequest.getRequestThirdStep().introduction())
                        .careerPeriod(memberRequest.getRequestThirdStep().careerPeriod())
                        .createdTime(now)
                        .build();

                break;
            }
            case ADMIN: {
                member = Member.builder()
                        .id(memberRequest.getRequestSecondStep().id())
                        .password(memberRequest.getRequestSecondStep().password())
                        .name(memberRequest.getRequestFirstStep().name())
                        .phoneNumber(memberRequest.getRequestFirstStep().phoneNumber())
                        .address(memberRequest.getRequestFirstStep().address())
                        .profileUrl("")
                        .role(memberRequest.getRoleRequest().role())
                        .createdTime(now)
                        .build();
                break;
            }
        }

        // 임시 아이디 쿠키 삭제
        String tempId = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("TI")) {
                tempId = cookie.getValue();
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        tempMember.remove(tempId);

        // DB에 데이터 영구 저장
        memberRepository.save(member);

        Member finalMember = member;
        System.out.println(memberRequest.getRequestFirstStep().certificate());
        memberRequest.getRequestFirstStep().certificate().forEach(c -> {
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
