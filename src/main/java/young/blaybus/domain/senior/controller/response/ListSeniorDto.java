package young.blaybus.domain.senior.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "어르신 목록 조회 응답 DTO")
public class ListSeniorDto {

  private long seniorId;

  private String profileUrl;

  private String name;

  private String sex;

  private LocalDate birthday;

  private String address;

}
