package young.blaybus.domain.senior.repository;

import static young.blaybus.domain.job_seek.QJobSeek.jobSeek;
import static young.blaybus.domain.senior.QSenior.senior;
import static young.blaybus.domain.senior.QSeniorDay.seniorDay;
import static young.blaybus.domain.senior.QSeniorFoodAssist.seniorFoodAssist;
import static young.blaybus.domain.senior.QSeniorLifeAssist.seniorLifeAssist;
import static young.blaybus.domain.senior.QSeniorMoveAssist.seniorMoveAssist;
import static young.blaybus.domain.senior.QSeniorToiletAssist.seniorToiletAssist;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import young.blaybus.domain.senior.controller.response.DetailSeniorResponse;
import young.blaybus.util.enums.DayOfWeek;
import young.blaybus.util.enums.assist.FoodAssist;
import young.blaybus.util.enums.assist.LifeAssist;
import young.blaybus.util.enums.assist.MoveAssist;
import young.blaybus.util.enums.assist.ToiletAssist;

@Repository
@RequiredArgsConstructor
public class DetailSeniorRepository {

  private final JPAQueryFactory queryFactory;


  public DetailSeniorResponse getSenior(Long seniorId) {
    return queryFactory.select(
        Projections.fields(
          DetailSeniorResponse.class,
          senior.id.as("seniorId"),
          senior.name,
          senior.careGrade,
          senior.profileUrl,
          senior.sex,
          senior.birthday,
          senior.address,
          jobSeek.salary,
          senior.startTime,
          senior.endTime
        )
      )
      .from(senior)
      .leftJoin(jobSeek).on(senior.eq(jobSeek.senior))
      .where(senior.id.eq(seniorId))
      .fetchOne();
  }

  public List<DayOfWeek> getSeniorDayList(Long seniorId) {
    return queryFactory.select(
        seniorDay.day
      )
      .from(seniorDay)
      .where(seniorDay.senior.id.eq(seniorId))
      .fetch();
  }

  public List<LifeAssist> getLifeAssistList(Long seniorId) {
    return queryFactory.select(
        seniorLifeAssist.lifeAssist
      )
      .from(seniorLifeAssist)
      .where(seniorLifeAssist.senior.id.eq(seniorId))
      .fetch();
  }

  public List<FoodAssist> getFoodAssistList(Long seniorId) {
    return queryFactory.select(
        seniorFoodAssist.foodAssist
      )
      .from(seniorFoodAssist)
      .where(seniorFoodAssist.senior.id.eq(seniorId))
      .fetch();
  }

  public List<MoveAssist> getMoveAssistList(Long seniorId) {
    return queryFactory.select(
        seniorMoveAssist.moveAssist
      )
      .from(seniorMoveAssist)
      .where(seniorMoveAssist.senior.id.eq(seniorId))
      .fetch();
  }

  public List<ToiletAssist> getToiletAssistList(Long seniorId) {
    return queryFactory.select(
        seniorToiletAssist.toiletAssist
      )
      .from(seniorToiletAssist)
      .where(seniorToiletAssist.senior.id.eq(seniorId))
      .fetch();
  }
}
