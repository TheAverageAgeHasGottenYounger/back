package young.blaybus.domain.senior.repository;

import static young.blaybus.domain.certificate.QCertificate.certificate;
import static young.blaybus.domain.job_search.QJobSearch.jobSearch;
import static young.blaybus.domain.matching.QMatching.matching;
import static young.blaybus.domain.member.QMember.member;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import young.blaybus.domain.certificate.enums.CertificateType;
import young.blaybus.domain.matching.enums.MatchingStatus;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.senior.Senior;

@Repository
@RequiredArgsConstructor
public class ListRecommendRepository {

  private final JPAQueryFactory queryFactory;


  public List<Member> getRecommendList(Senior senior) {
    return queryFactory.selectFrom(member)
      .innerJoin(jobSearch).on(jobSearch.member.eq(member)).fetchJoin()
      .innerJoin(certificate).on(certificate.member.eq(member)).fetchJoin()
      .where(
        member.center.isNull(), // 요양 보호사만
        certificate.type.eq(CertificateType.CARE), // 요양 보호사 자격증 필수
        member.notIn( // 거절 이력 있으면 추천 X
          JPAExpressions.select(matching.member)
            .from(matching)
            .where(
              matching.member.eq(member),
              matching.senior.eq(senior),
              matching.status.eq(MatchingStatus.REJECTED))
        )
      )
      .groupBy(member)
      .fetch();
  }
}
