package young.blaybus.domain.test.repository;

import static young.blaybus.domain.test.QTest.*;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import young.blaybus.domain.test.controller.response.DetailTestResponse;

/**
 * 전 QueryDsl이 편해서 사용하고 있지만,
 * JPQL이 편하시면 Interface Repository에서 작업하시면 됩니다!
 */
@Repository
@RequiredArgsConstructor
public class DetailTestRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 테스트 상세 조회
   */
  public DetailTestResponse getTest(Long testId) {
    return queryFactory
      .select(
        Projections.fields(
          DetailTestResponse.class,
          test.id.as("testId"),
          test.title,
          test.content
          )
      )
      .from(test)
      .where(test.id.eq(testId))
      .fetchOne();
  }
}
