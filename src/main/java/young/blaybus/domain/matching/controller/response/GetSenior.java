package young.blaybus.domain.matching.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@Schema(description = "어르신 기본 정보 객체")
public class GetSenior {

    @Schema(description = "어르신 ID")
    private long seniorId;

    @Schema(description = "프로필 사진")
    private String profileUrl;

    @Schema(description = "이름")
    private String name;

}
