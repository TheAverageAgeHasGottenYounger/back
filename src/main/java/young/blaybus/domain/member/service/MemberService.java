package young.blaybus.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import young.blaybus.api_response.exception.GeneralException;
import young.blaybus.api_response.status.ErrorStatus;
import young.blaybus.domain.address.Address;
import young.blaybus.domain.center.Center;
import young.blaybus.domain.center.repository.CenterRepository;
import young.blaybus.domain.certificate.Certificate;
import young.blaybus.domain.certificate.enums.CertificateGrade;
import young.blaybus.domain.certificate.enums.CertificateType;
import young.blaybus.domain.certificate.repository.CertificateRepository;
import young.blaybus.domain.certificate.request.CreateCertificateRequest;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.controller.request.CreateAdminRequest;
import young.blaybus.domain.member.controller.request.CreateMemberRequest;
import young.blaybus.domain.member.controller.response.CurrentMemberResponse;
import young.blaybus.domain.member.controller.response.GetAdmin;
import young.blaybus.domain.member.controller.response.GetCenterCheckResponse;
import young.blaybus.domain.member.controller.response.GetMember;
import young.blaybus.domain.member.enums.MemberRole;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.member.security.SecurityUtils;
import young.blaybus.domain.member.security.jwt.provider.JwtProvider;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CertificateRepository certificateRepository;
    private final CenterRepository centerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    // 관리자 회원 등록
    @Transactional
    public Member adminRegisterMember(CreateAdminRequest adminRequest) {
        LocalDateTime now = LocalDateTime.now();

        String profileUrl = "https://theaverageagegottenyounger.s3.ap-northeast-2.amazonaws.com/blaybus-basic-profile-image.png";
        if (StringUtils.hasText(adminRequest.profileUrl())) profileUrl = adminRequest.profileUrl();

        Center center = centerRepository.findByName(adminRequest.center().name());
        MemberRole role = MemberRole.ADMIN;
        Member member = Member.builder()
                .id(adminRequest.id())
                .password(bCryptPasswordEncoder.encode(adminRequest.password()))
                .phoneNumber(adminRequest.phoneNumber())
                .carYn(adminRequest.carYn())
                .profileUrl(profileUrl)
                .role(role)
                .center(center)
                .createdTime(now)
                .build();

        return memberRepository.save(member);
    }

    // 요양보호사 회원 등록
    @Transactional
    public void workerRegisterMember(CreateMemberRequest memberRequest) {
        LocalDateTime now = LocalDateTime.now();

        String profileUrl = "https://theaverageagegottenyounger.s3.ap-northeast-2.amazonaws.com/blaybus-basic-profile-image.png";
        if (StringUtils.hasText(memberRequest.profileUrl())) profileUrl = memberRequest.profileUrl();

        MemberRole role = MemberRole.WORKER;
        Member member = Member.builder()
                .id(memberRequest.id())
                .password(bCryptPasswordEncoder.encode(memberRequest.password()))
                .name(memberRequest.name())
                .phoneNumber(memberRequest.phoneNumber())
                .address(new Address(memberRequest.city(), memberRequest.gu(), memberRequest.dong(), null))
                .profileUrl(profileUrl)
                .role(role)
                .carYn(memberRequest.carYn())
                .dementiaEducationYn(memberRequest.dementiaEducationYn())
                .career(memberRequest.career())
                .introduction(memberRequest.introduction())
                .careerPeriod(memberRequest.careerPeriod())
                .createdTime(now)
                .careStyle(memberRequest.careStyle())
                .build();

        memberRepository.save(member);

        memberRequest.certificate().forEach(c -> {
            CertificateType type = switch (c.getType()) {
                case "요양보호사" -> CertificateType.CARE;
                case "사회복지사" -> CertificateType.SOCIAL;
                case "간호조무사" -> CertificateType.NURSE;
                default -> null;
            };

            CertificateGrade grade = null;
            if (c.getGrade() != null) {
                grade = switch (c.getGrade()) {
                    case "1급" -> CertificateGrade.GRADE1;
                    case "2급" -> CertificateGrade.GRADE2;
                    default -> null;
                };
            }

            Certificate certificate = Certificate.builder()
                        .type(type)
                        .number(c.getNumber())
                        .grade(grade)
                        .member(member)
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

    // 회원 조회
    public Object getMember(String memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Optional<List<Certificate>> certificate = certificateRepository.findByMember(member);

        List<CreateCertificateRequest> getCertificate = new ArrayList<>();
        GetMember getMember;
        GetAdmin getAdmin;
        if (member != null) {
            if (certificate.isPresent() && member.getRole() == MemberRole.WORKER) {
                certificate.get().forEach(c -> {
                    getCertificate.add(new CreateCertificateRequest(c.getType().getValue(), c.getNumber(), c.getGrade().getValue()));
                });

                getMember = GetMember.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .profileUrl(member.getProfileUrl())
                        .phoneNumber(member.getPhoneNumber())
                        .city(member.getAddress().getCity())
                        .gu(member.getAddress().getDistrict())
                        .dong(member.getAddress().getDong())
                        .certificate(getCertificate)
                        .profileUrl(member.getProfileUrl())
                        .style(member.getCareStyle().getValue())
                        .build();

                return getMember;
            } else if (member.getRole() == MemberRole.ADMIN) {
                Center center = centerRepository.findById(member.getCenter().getId()).orElse(null);

                if (center != null) {
                    getAdmin = GetAdmin.builder()
                            .id(member.getId())
                            .profileUrl(member.getProfileUrl())
                            .carYn(member.getCarYn())
                            .phoneNumber(member.getPhoneNumber())
                            .center(
                                GetCenterCheckResponse.builder()
                                    .name(center.getName())
                                    .bathCarYn(center.getBathCarYn())
                                    .operationPeriod(center.getOperationPeriod())
                                    .city(center.getAddress().getCity())
                                    .gu(center.getAddress().getDistrict())
                                    .dong(center.getAddress().getDong())
                                    .introduction(center.getIntroduction())
                                    .grade(center.getGrade())
                                    .build()
                            ).build();

                    return getAdmin;
                }
            }
        }

        return null;
    }

    public String login(String id, String password) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty() || !bCryptPasswordEncoder.matches(password, optionalMember.get().getPassword())) {
          throw new GeneralException(ErrorStatus.BAD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.createAccessToken(id, String.valueOf(optionalMember.get().getRole()));
    }

    public CurrentMemberResponse getCurrentMember() {
        Member member = memberRepository.findById(SecurityUtils.getCurrentMemberName())
          .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST));
        return CurrentMemberResponse.builder()
          .memberId(member.getId())
          .role(member.getRole())
          .build();
    }
}
