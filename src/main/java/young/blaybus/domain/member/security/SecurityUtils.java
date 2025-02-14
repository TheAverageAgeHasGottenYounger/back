package young.blaybus.domain.member.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import young.blaybus.api_response.exception.GeneralException;
import young.blaybus.api_response.status.ErrorStatus;

public class SecurityUtils {
    private static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new GeneralException(ErrorStatus.TOKEN_EXPIRED, "유효하지 않은 토큰 입니다.");
        }
        return authentication;
    }

    public static String getCurrentMemberName() {
        Authentication authentication = getAuthentication();
        return authentication.getName();
    }
}
