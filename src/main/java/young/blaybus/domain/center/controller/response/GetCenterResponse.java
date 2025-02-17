package young.blaybus.domain.center.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@Tag(name = "센터 정보 조회 응답 객체")
public class GetCenterResponse {

    @Schema(description = "센터 ID")
    private String id;

    @Schema(description = "이름")
    private String name;
}
