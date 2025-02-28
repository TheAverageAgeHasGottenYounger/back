package young.blaybus.domain.center.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import young.blaybus.domain.address.Address;
import young.blaybus.domain.center.Center;
import young.blaybus.domain.center.controller.response.GetCenter;
import young.blaybus.domain.center.controller.response.GetCenterDetailInforResponse;
import young.blaybus.domain.center.controller.response.GetCenterResponse;
import young.blaybus.domain.center.controller.response.GetSeniorCountResponse;
import young.blaybus.domain.center.repository.CenterRepository;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.controller.request.CreateAdminRequest;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.member.security.SecurityUtils;
import young.blaybus.domain.senior.Senior;
import young.blaybus.domain.senior.repository.SeniorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class CenterService {

  private final CenterRepository centerRepository;
  private final SeniorRepository seniorRepository;
  private final MemberRepository memberRepository;
  
  
    // 센터 등록
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

  // 센터 조회
  @Transactional
  public GetCenterResponse getCenterCheck(String centerName) {
    Center center1 = centerRepository.findByName(centerName);
    return GetCenterResponse.builder()
      .id(String.valueOf(center1.getId()))
      .name(center1.getName())
      .build();
  }

  // 센터 상세 조회
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

    center = center.toBuilder()
      .id(center.getId())
      .name(adminRequest.center().name())
      .bathCarYn(adminRequest.center().bathCarYn())
      .grade(adminRequest.center().grade())
      .operationPeriod(adminRequest.center().operationPeriod())
      .address(new Address(adminRequest.center().city(), adminRequest.center().gu(), adminRequest.center().dong(), null))
      .introduction(adminRequest.center().introduction())
      .phoneNumber(adminRequest.phoneNumber())
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

    // 소속 센터에 등록된 어르신 수
    public GetSeniorCountResponse getSeniorCount() {
        String adminId = SecurityUtils.getCurrentMemberName();
        Optional<Member> member = memberRepository.findById(adminId);

        AtomicInteger seniorCount = new AtomicInteger();
        if (member.isPresent()) {
            Center center = centerRepository.findById(member.get().getCenter().getId()).orElse(null);
            if (center != null) {
                List<Senior> senior = seniorRepository.findByCenterId(center.getId());
                senior.forEach(s -> seniorCount.getAndIncrement());
            }
        }

        return GetSeniorCountResponse.builder()
                .seniorCount(seniorCount.get())
                .build();
    }

}
