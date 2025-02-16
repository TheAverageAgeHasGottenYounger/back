package young.blaybus.domain.senior.repository;

import static young.blaybus.domain.senior.QSenior.senior;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import young.blaybus.domain.center.Center;
import young.blaybus.domain.senior.controller.response.ListSeniorDto;

@Repository
@RequiredArgsConstructor
public class ListSeniorRepository {

  private final JPAQueryFactory queryFactory;

  public List<ListSeniorDto> getSeniorList(Center center) {
    return queryFactory.select(
        Projections.fields(
          ListSeniorDto.class,
          senior.id.as("seniorId"),
          senior.profileUrl,
          senior.name,
          senior.sex.stringValue().as("sex"),
          senior.birthday,
          senior.address
          )
      )
      .from(senior)
      .where(senior.center.eq(center))
      .fetch();
  }
}
