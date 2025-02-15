package young.blaybus.domain.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class GetCenterCheckResponse {

    @Schema(description = "이름")
    String name;

    @Schema(description = "목욕 차량 여부")
    Boolean bathCarYn;

    @Schema(description = "등급")
    String grade;

    @Schema(description = "운영 기간")
    String operationPeriod;

    @Schema(description = "시/도")
    String city;

    @Schema(description = "구/군")
    String gu;

    @Schema(description = "동")
    String dong;

    @Schema(description = "한줄 소개")
    String introduction;
}
