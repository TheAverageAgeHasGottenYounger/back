package young.blaybus.domain.member.request;

import young.blaybus.domain.certificate.request.CertificateRequest;

import java.util.List;

public record MemberRequest(
    String profileImage,
    String name,
    String phoneNumber,
    String address,
    List<CertificateRequest> certificate,
    String id,
    String password,
    String role,
    Boolean carYn,
    Boolean dementiaEducationYn,
    String career,
    String careerPeriod,
    String introduction
) { }
