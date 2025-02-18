package young.blaybus.domain.matching.repository;

import static young.blaybus.domain.matching.QMatching.matching;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import young.blaybus.domain.matching.enums.MatchingStatus;
import young.blaybus.domain.member.Member;

@Repository
@RequiredArgsConstructor
public class ListMatchingRepository {

  private final JPAQueryFactory queryFactory;


  public long countAccepted(Member member) {
    return queryFactory
      .selectFrom(matching)
      .where(
        matching.member.eq(member),
        matching.status.eq(MatchingStatus.ACCEPTED)
      )
      .fetch().size();

  }
}
