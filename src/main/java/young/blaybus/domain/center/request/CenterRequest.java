package young.blaybus.domain.center.request;

public record CenterRequest(
    String name,
    Boolean bathCarYn,
    String grade,
    String operationPeriod,
    String address,
    String introduction
) { }
