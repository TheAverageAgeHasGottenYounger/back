package young.blaybus.domain.matching.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@ToString
@AllArgsConstructor
@Schema(description = "매칭중인 어르신 목록 조회 응답 객체")
public class GetMatchingSeniorsList {

    @Schema(description = "매칭중인 어르신 목록")
    private List<GetMatchingSeniors> seniorList;
}
