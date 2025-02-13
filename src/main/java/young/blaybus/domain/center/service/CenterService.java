package young.blaybus.domain.center.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import young.blaybus.domain.center.Center;
import young.blaybus.domain.center.repository.CenterRepository;
import young.blaybus.domain.center.request.CenterRequest;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.request.AdminRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 센터가 존재하는지 (존재하지 않다면 새로 등록, 존재한다면 존재한 센터를 반환)
 */

@Service
@RequiredArgsConstructor
public class CenterService {

    private final CenterRepository centerRepository;

    // 센터 등록
    @Transactional(rollbackOn = Exception.class)
    public void registerCenter(CenterRequest centerRequest, AdminRequest adminRequest) {
        LocalDateTime now = LocalDateTime.now();

        Center center = Center.builder()
                .name(centerRequest.name())
                .bathCarYn(centerRequest.bathCarYn())
                .grade(centerRequest.grade())
                .operationPeriod(centerRequest.operationPeriod())
                .address(centerRequest.address())
                .introduction(centerRequest.introduction())
                .createdTime(now)
                .build();

        centerRepository.save(center);
    }

    public Center findCenterName(String name) {
        Optional<Center> center = centerRepository.findByNameContaining(name);
        return center.orElse(null);
    }

}
