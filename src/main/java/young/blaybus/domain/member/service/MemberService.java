package young.blaybus.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
import young.blaybus.domain.member.controller.response.GetAdmin;
import young.blaybus.domain.member.controller.response.GetCenterCheckResponse;
import young.blaybus.domain.member.controller.response.GetMember;
import young.blaybus.domain.member.enums.MemberRole;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.s3_file.service.S3FileService;

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
    private final S3FileService s3FileService;

    // 관리자 회원 등록
    @Transactional(rollbackOn = Exception.class)
    public void adminRegisterMember(CreateAdminRequest adminRequest, MultipartFile profileImage) {
        LocalDateTime now = LocalDateTime.now();

        String s3FileUrl = null;
        if (profileImage != null) s3FileUrl = s3FileService.uploadS3File(profileImage).getFileUrl();

        MemberRole role = MemberRole.ADMIN;
        Member member = Member.builder()
                .id(adminRequest.id())
                .password(bCryptPasswordEncoder.encode(adminRequest.password()))
                .phoneNumber(adminRequest.phoneNumber())
                .address(new Address(adminRequest.city(), adminRequest.gu(), adminRequest.dong(), null))
                .carYn(adminRequest.carYn())
                .profileUrl(s3FileUrl)
                .role(role)
                .createdTime(now)
                .build();

        // DB에 데이터 영구 저장
        memberRepository.save(member);
    }

    // 요양보호사 회원 등록
    @Transactional(rollbackOn = Exception.class)
    public void workerRegisterMember(CreateMemberRequest memberRequest, MultipartFile profileImage) {
        LocalDateTime now = LocalDateTime.now();

        String s3FileUrl = null;
        if (profileImage != null) s3FileUrl = s3FileService.uploadS3File(profileImage).getFileUrl();

        MemberRole role = MemberRole.WORKER;
        Member member = Member.builder()
                .id(memberRequest.id())
                .password(bCryptPasswordEncoder.encode(memberRequest.password()))
                .name(memberRequest.name())
                .phoneNumber(memberRequest.phoneNumber())
                .address(new Address(memberRequest.city(), memberRequest.gu(), memberRequest.dong(), null))
                .profileUrl(s3FileUrl)
                .role(role)
                .carYn(memberRequest.carYn())
                .dementiaEducationYn(memberRequest.dementiaEducationYn())
                .career(memberRequest.career())
                .introduction(memberRequest.introduction())
                .careerPeriod(memberRequest.careerPeriod())
                .createdTime(now)
                .build();

        // DB에 데이터 영구 저장
        memberRepository.save(member);

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
        GetMember getMember = null;
        GetAdmin getAdmin = null;
        if (member != null) {
            if (certificate.isPresent() && member.getRole() == MemberRole.WORKER) {
                certificate.get().forEach(c -> {
                    getCertificate.add(new CreateCertificateRequest(c.getType().getValue(), c.getNumber(), c.getType().getValue()));
                });

                getMember = GetMember.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .phoneNumber(member.getPhoneNumber())
                        .city(member.getAddress().getCity())
                        .gu(member.getAddress().getDistrict())
                        .dong(member.getAddress().getDong())
                        .certificate(getCertificate)
                        .build();

                return getMember;
            } else if (member.getRole() == MemberRole.ADMIN) {
                Center center = centerRepository.findById(member.getCenter().getId()).orElse(null);

                if (center != null) {
                    getAdmin = GetAdmin.builder()
                            .id(member.getId())
                            .city(member.getAddress().getCity())
                            .gu(member.getAddress().getDistrict())
                            .dong(member.getAddress().getDong())
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
            } else return null;
        }

        return null;
    }

}
