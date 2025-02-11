package young.blaybus.api_response.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus {
    OK(HttpStatus.OK, "COMMON200", "요청 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
