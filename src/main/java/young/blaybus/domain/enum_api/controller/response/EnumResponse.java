package young.blaybus.domain.enum_api.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ENUM 목록 응답 객체")
public class EnumResponse {

  @Schema(description = "ENUM 목록")
  private List<EnumDto> enumList;

}
