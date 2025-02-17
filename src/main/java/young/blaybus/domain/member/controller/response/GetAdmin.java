package young.blaybus.domain.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import young.blaybus.domain.center.controller.request.CreateCenterRequest;

@Getter
@Builder
@ToString
@AllArgsConstructor
@Schema(description = "관리자 조회 응답 객체")
public class GetAdmin {

    @Schema(description = "프로필 사진")
    String profileUrl;

    @Schema(description = "전화번호")
    String phoneNumber;

    @Schema(description = "차량 소유 여부")
    Boolean carYn;

    @Schema(description = "아이디")
    String id;

    @Schema(description = "센터")
    GetCenterCheckResponse center;
}
