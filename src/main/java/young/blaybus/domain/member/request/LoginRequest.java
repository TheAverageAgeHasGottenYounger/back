package young.blaybus.domain.member.request;

public record LoginRequest(
    String id,
    String password
) { }
