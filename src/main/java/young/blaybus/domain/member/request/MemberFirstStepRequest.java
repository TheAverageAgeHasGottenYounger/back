package young.blaybus.domain.member.request;

import young.blaybus.domain.certificate.request.CertificateRequest;

import java.util.List;

/**
 * 회원가입 첫번째 화면에서 보낼 데이터
 */

public record MemberFirstStepRequest(
        String profileImage,
        String name,
        String phoneNumber,
        String address,
        List<CertificateRequest> certificate
) { }
