package young.blaybus.domain.job_search.request;

import io.swagger.v3.oas.annotations.media.Schema;
import young.blaybus.domain.address.Address;

@Schema(description = "근무 가능 지역 입력 객체")
public record JobSearchAreaRequest(
        Address address
) {
}
