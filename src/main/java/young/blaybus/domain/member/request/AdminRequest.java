package young.blaybus.domain.member.request;

import young.blaybus.domain.center.request.CenterRequest;

public record AdminRequest(
    String profileImage,
    String name,
    String phoneNumber,
    String address,
    String id,
    String password,
    String role,
    CenterRequest center
) { }
