package young.blaybus.domain.job_search.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import young.blaybus.domain.address.Address;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description = "근무 가능 지역 응답 객체")
public class JobSearchAreaResponse {
    Address address;
}
