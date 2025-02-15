package young.blaybus.domain.matching.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import young.blaybus.domain.member.controller.response.GetMember;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
@Schema(description = "요양보호사 매칭 현황 조회 객체")
public class GetMatchingWorkerList {

    @Schema(description = "요양보호사 리스트")
    private List<GetMember> memberList;
}
