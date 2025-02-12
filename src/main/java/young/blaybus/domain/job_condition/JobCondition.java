package young.blaybus.domain.job_condition;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import young.blaybus.domain.matching.Matching;
import young.blaybus.util.enums.DayOfWeek;
import young.blaybus.util.enums.assist.FoodAssist;
import young.blaybus.util.enums.assist.LifeAssist;
import young.blaybus.util.enums.assist.MoveAssist;
import young.blaybus.util.enums.assist.ToiletAssist;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class JobCondition {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("주소")
  private String address;

  @Comment("종료 시간")
  private LocalTime startTime;

  @Comment("종료 시간")
  private LocalTime endTime;

  @Comment("급여")
  private Integer salary;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime updatedTime;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "matching_id")
  private Matching matching;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name="job_condition_day", joinColumns = @JoinColumn(name= "job_condition_id", referencedColumnName = "id"))
  @Enumerated(value = EnumType.STRING)
  private Set<DayOfWeek> daySet = new HashSet<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name="job_condition_food_assist", joinColumns = @JoinColumn(name= "job_condition_id", referencedColumnName = "id"))
  @Enumerated(value = EnumType.STRING)
  private Set<FoodAssist> foodAssistSet = new HashSet<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name="job_condition_move_assist", joinColumns = @JoinColumn(name= "job_condition_id", referencedColumnName = "id"))
  @Enumerated(value = EnumType.STRING)
  private Set<MoveAssist> moveAssistSet = new HashSet<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name="job_condition_toilet_assist", joinColumns = @JoinColumn(name= "job_condition_id", referencedColumnName = "id"))
  @Enumerated(value = EnumType.STRING)
  private Set<ToiletAssist> toiletAssistSet = new HashSet<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name="job_condition_life_assist", joinColumns = @JoinColumn(name= "job_condition_id", referencedColumnName = "id"))
  @Enumerated(value = EnumType.STRING)
  private Set<LifeAssist> lifeAssistSet = new HashSet<>();

}
