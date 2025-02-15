package young.blaybus.domain.matching.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import young.blaybus.domain.certificate.Certificate;
import young.blaybus.domain.certificate.repository.CertificateRepository;
import young.blaybus.domain.certificate.request.CreateCertificateRequest;
import young.blaybus.domain.matching.Matching;
import young.blaybus.domain.matching.controller.request.PatchStatusRequest;
import young.blaybus.domain.matching.controller.response.GetMatching;
import young.blaybus.domain.matching.controller.response.GetMatchingSeniorList;
import young.blaybus.domain.matching.controller.response.GetMatchingWorkerList;
import young.blaybus.domain.matching.controller.response.GetSenior;
import young.blaybus.domain.matching.enums.MatchingStatus;
import young.blaybus.domain.matching.repository.MatchingRepository;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.controller.response.GetMember;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.senior.Senior;
import young.blaybus.domain.senior.repository.SeniorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MemberRepository memberRepository;
    private final SeniorRepository seniorRepository;
    private final MatchingRepository matchingRepository;
    private final CertificateRepository certificateRepository;

    // 매칭 요청
    @Transactional(rollbackOn = Exception.class)
    public void matchingRequest(String workerId, long seniorId) {
        Member member = memberRepository.findById(workerId).orElse(null);
        Senior senior = seniorRepository.findById(seniorId).orElse(null);

        Matching matching = Matching.builder()
                .senior(senior)
                .member(member)
                .status(MatchingStatus.PENDING)
                .build();

        matchingRepository.save(matching);
    }

    // 매칭 현황 조회 (특정)
    @Transactional(rollbackOn = Exception.class)
    public GetMatching getMatching(String workerId, String seniorId) {
        Matching matching = matchingRepository.findBySenior_IdAndMember_Id(Long.parseLong(seniorId), workerId);
        List<Certificate> certificate =  certificateRepository.findByMember(matching.getMember()).orElse(null);

        List<CreateCertificateRequest> certificates = new ArrayList<>();
        if (certificate != null) {
            certificate.forEach(c -> {
                certificates.add(new CreateCertificateRequest(c.getType().getValue(), c.getNumber(), c.getGrade().getValue()));
            });
        }

        return GetMatching.builder()
                .matchingId(matching.getId())
                .senior(
                    GetSenior.builder()
                        .seniorId(matching.getSenior().getId())
                        .profileUrl(matching.getSenior().getProfileUrl())
                        .name(matching.getSenior().getName())
                        .build()
                )
                .member(
                    GetMember.builder()
                        .id(matching.getMember().getId())
                        .name(matching.getMember().getName())
                        .phoneNumber(matching.getMember().getPhoneNumber())
                        .city(matching.getMember().getAddress().getCity())
                        .gu(matching.getMember().getAddress().getDistrict())
                        .dong(matching.getMember().getAddress().getDong())
                        .certificate(certificates)
                        .carYn(matching.getMember().getCarYn())
                        .dementiaEducationYn(matching.getMember().getDementiaEducationYn())
                        .career(matching.getMember().getCareer())
                        .careerPeriod(matching.getMember().getCareerPeriod())
                        .introduction(matching.getMember().getIntroduction())
                        .build()
                )
                .matchingStatus(matching.getStatus())
                .build();
    }

    // 매칭 현황 어르신 리스트 조회 -> 요양보호사 쪽에서 어르신 매칭 현황 조회
    @Transactional(rollbackOn = Exception.class)
    public GetMatchingSeniorList getMatchingSeniorList(String workerId) {
        List<Matching> matching = matchingRepository.findByMember_Id(workerId);

        List<GetSenior> responseList = new ArrayList<>();
        matching.forEach(m -> {
            responseList.add(GetSenior.builder()
                .seniorId(m.getSenior().getId())
                .profileUrl(m.getSenior().getProfileUrl())
                .name(m.getSenior().getName())
                .build()
            );
        });

        return GetMatchingSeniorList.builder()
                .seniorList(responseList)
                .build();
    }

    // 매칭 현황 요양보호사 리스트 조회 -> 관리자 쪽에서 요양보호사 매칭 현황 조회
    @Transactional(rollbackOn = Exception.class)
    public GetMatchingWorkerList getMatchingWorkerList(String seniorId) {
        List<Matching> matching = matchingRepository.findBySenior_Id(Long.parseLong(seniorId));

        List<GetMember> responseList = new ArrayList<>();
        matching.forEach(m -> {
            List<Certificate> certificate =  certificateRepository.findByMember(m.getMember()).orElse(null);
            List<CreateCertificateRequest> certificates = new ArrayList<>();
            if (certificate != null) {
                certificate.forEach(c -> {
                    certificates.add(new CreateCertificateRequest(c.getType().getValue(), c.getNumber(), c.getGrade().getValue()));
                });
            }

            responseList.add(
                GetMember.builder()
                    .id(m.getMember().getId())
                    .name(m.getMember().getName())
                    .phoneNumber(m.getMember().getPhoneNumber())
                    .city(m.getMember().getAddress().getCity())
                    .gu(m.getMember().getAddress().getDistrict())
                    .dong(m.getMember().getAddress().getDong())
                    .certificate(certificates)
                    .carYn(m.getMember().getCarYn())
                    .dementiaEducationYn(m.getMember().getDementiaEducationYn())
                    .career(m.getMember().getCareer())
                    .careerPeriod(m.getMember().getCareerPeriod())
                    .introduction(m.getMember().getIntroduction())
                    .build()
            );
        });

        return GetMatchingWorkerList.builder()
                .memberList(responseList)
                .build();
    }

    // 매칭 상태 갱신
    @Transactional(rollbackOn = Exception.class)
    public void matchingStatusPatch(PatchStatusRequest statusRequest) {
        Matching matching = matchingRepository.findBySenior_IdAndMember_Id(Long.parseLong(statusRequest.seniorId()), statusRequest.workerId());

        Matching matchingUpdateObject = Matching.builder()
                .id(matching.getId())
                .senior(matching.getSenior())
                .member(matching.getMember())
                .status(statusRequest.status())
                .build();

        matchingRepository.save(matchingUpdateObject);
    }

}
