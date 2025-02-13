package young.blaybus.domain.member.request;

import lombok.*;

@Getter
@ToString
@Setter
@AllArgsConstructor
public class MemberRequest {
    private RoleRequest roleRequest;
    private MemberFirstStepRequest requestFirstStep;
    private MemberSecondStepRequest requestSecondStep;
    private MemberThirdStepRequest requestThirdStep;
}
