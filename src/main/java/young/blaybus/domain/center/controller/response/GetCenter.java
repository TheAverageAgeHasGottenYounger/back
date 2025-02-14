package young.blaybus.domain.center.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "센터 등록 여부 응답 DTO")
public class GetCenter {

    @Schema(description = "메시지")
    private String message;

    @Schema(description = "여부")
    private boolean result;

}
