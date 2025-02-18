package young.blaybus.domain.matching.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@Schema(description = "매칭 진행중인 어르신 조회 응답 객체")
public class GetProgressMatchingSeniors {

    @Schema(description = "어르신 ID")
    private String seniorId;

    @Schema(description = "프로필 사진")
    private String profileUrl;

    @Schema(description = "성함")
    private String name;

    @Schema(description = "성별")
    private String sex;

    @Schema(description = "생년월일")
    private String birthday;

    @Schema(description = "주소")
    private String address;
}
