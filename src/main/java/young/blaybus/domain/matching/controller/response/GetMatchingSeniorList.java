package young.blaybus.domain.matching.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
@Schema(description = "어르신 매칭 현황 조회 객체")
public class GetMatchingSeniorList {

    @Schema(description = "어르신")
    private List<GetSenior> seniorList;
}
