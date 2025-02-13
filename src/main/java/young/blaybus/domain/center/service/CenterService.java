package young.blaybus.domain.center.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import young.blaybus.domain.center.Center;
import young.blaybus.domain.center.repository.CenterRepository;
import young.blaybus.domain.center.request.CenterRequest;
import young.blaybus.domain.member.request.AdminRequest;

import java.time.LocalDateTime;
import java.util.Optional;

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
