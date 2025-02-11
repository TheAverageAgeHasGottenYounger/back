package young.blaybus.api_response.exception;


import young.blaybus.api_response.dto.ApiDto;

public interface BaseErrorCode {

    ApiDto getReasonHttpStatus();
}
