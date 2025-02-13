package young.blaybus.domain.center.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "센터 등록 요청 객체")
public record CreateCenterRequest(
    @Schema(description = "이름")
    String name,

    @Schema(description = "목욕 차량 여부")
    Boolean bathCarYn,

    @Schema(description = "등급")
    String grade,

    @Schema(description = "운영 기간")
    String operationPeriod,

    @Schema(description = "주소")
    String address,

    @Schema(description = "한줄 소개")
    String introduction
) { }
