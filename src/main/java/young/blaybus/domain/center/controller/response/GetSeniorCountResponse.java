package young.blaybus.domain.center.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@Schema(description = "소속 센터에 등록된 어르신 수 응답 객체")
public class GetSeniorCountResponse {

    @Schema(description = "어르신 수")
    private int seniorCount;
}
