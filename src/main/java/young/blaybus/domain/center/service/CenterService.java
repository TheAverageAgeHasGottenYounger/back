package young.blaybus.domain.center.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import young.blaybus.domain.address.Address;
import young.blaybus.domain.center.Center;
import young.blaybus.domain.center.controller.response.GetCenter;
import young.blaybus.domain.center.controller.response.GetCenterDetailInforResponse;
import young.blaybus.domain.center.controller.response.GetCenterResponse;
import young.blaybus.domain.center.repository.CenterRepository;
import young.blaybus.domain.member.Member;
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

    @Transactional
    public GetCenterResponse registerCenter(String centerName) {
        Center center = Center.builder()
                .name(centerName)
                .build();

        centerRepository.save(center);

        Center center1 = centerRepository.findByName(centerName);
        return GetCenterResponse.builder()
                .id(String.valueOf(center1.getId()))
                .name(center1.getName())
                .build();
    }

    @Transactional
    public GetCenterResponse getCenterCheck(String centerName) {
        Center center1 = centerRepository.findByName(centerName);
        return GetCenterResponse.builder()
                .id(String.valueOf(center1.getId()))
                .name(center1.getName())
                .build();
    }

    @Transactional
    public GetCenterDetailInforResponse getCenterDetailInfor(String centerName) {
        Center center = centerRepository.findByName(centerName);
        return GetCenterDetailInforResponse.builder()
                .id(String.valueOf(center.getId()))
                .name(center.getName())
                .city(center.getAddress().getCity())
                .gu(center.getAddress().getDistrict())
                .dong(center.getAddress().getDong())
                .operationPeriod(center.getOperationPeriod())
                .grade(center.getGrade())
                .bathCarYn(center.getBathCarYn())
                .introduction(center.getIntroduction())
                .build();
    }

    // 센터 상세 정보 등록 (등록된 센터에 상세 정보 포함해 수정)
    @Transactional
    public void registerCenterDetailInfor(Member member, CreateAdminRequest adminRequest) {
        if (member != null) System.out.println(member.getId());

        Center center = centerRepository.findByName(adminRequest.center().name());
        LocalDateTime now = LocalDateTime.now();

        center = center.toBuilder()
                    .id(center.getId())
                    .name(adminRequest.center().name())
                    .bathCarYn(adminRequest.center().bathCarYn())
                    .grade(adminRequest.center().grade())
                    .operationPeriod(adminRequest.center().operationPeriod())
                    .address(new Address(adminRequest.center().city(), adminRequest.center().gu(), adminRequest.center().dong(), null))
                    .introduction(adminRequest.center().introduction())
                    .updatedTime(now)
                    .build();

        centerRepository.save(center);
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
