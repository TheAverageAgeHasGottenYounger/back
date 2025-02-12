package young.blaybus.domain.senior;

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
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
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
import young.blaybus.domain.center.Center;
import young.blaybus.domain.senior.enums.CareGrade;
import young.blaybus.domain.senior.enums.Sex;
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
public class Senior {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("이름")
  private String name;

  @Comment("생년월일")
  private LocalDate birthday;

  @Enumerated(value = EnumType.STRING)
  @Comment("장기요양 등급")
  private CareGrade careGrade;

  @Comment("성별")
  private Sex sex;

  @Comment("주소")
  private String address;

  @Comment("프로필 사진")
  private String profileUrl;

  @Comment("시작 시간")
  private LocalTime startTime;

  @Comment("종료 시간")
  private LocalTime endTime;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime updatedTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "center_id")
  private Center center;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name="senior_day", joinColumns = @JoinColumn(name= "senior_id", referencedColumnName = "id"))
  @Enumerated(value = EnumType.STRING)
  private Set<DayOfWeek> daySet = new HashSet<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name="senior_food_assist", joinColumns = @JoinColumn(name= "senior_id", referencedColumnName = "id"))
  @Enumerated(value = EnumType.STRING)
  private Set<FoodAssist> foodAssistSet = new HashSet<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name="senior_move_assist", joinColumns = @JoinColumn(name= "senior_id", referencedColumnName = "id"))
  @Enumerated(value = EnumType.STRING)
  private Set<MoveAssist> moveAssistSet = new HashSet<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name="senior_toilet_assist", joinColumns = @JoinColumn(name= "senior_id", referencedColumnName = "id"))
  @Enumerated(value = EnumType.STRING)
  private Set<ToiletAssist> toiletAssistSet = new HashSet<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name="senior_life_assist", joinColumns = @JoinColumn(name= "senior_id", referencedColumnName = "id"))
  @Enumerated(value = EnumType.STRING)
  private Set<LifeAssist> lifeAssistSet = new HashSet<>();


}
