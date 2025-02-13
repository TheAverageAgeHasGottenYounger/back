package young.blaybus.domain.enum_api.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ENUM 목록 응답 DTO")
public class EnumDto {

  @Schema(description = "ENUM 목록 응답 DTO")
  private String code;

  @Schema(description = "ENUM 목록 응답 DTO")
  private String value;

}
