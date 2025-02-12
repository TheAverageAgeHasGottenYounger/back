package young.blaybus.domain.matching.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MatchingStatus {

  PENDING("수락 대기"),
  ACCEPTED("수락"),
  TUNE_REQUESTED("조율 요청"),
  REJECTED("거절");

  private final String value;
}
