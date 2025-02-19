package young.blaybus.domain.senior;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import young.blaybus.domain.center.Center;
import young.blaybus.domain.senior.enums.CareGrade;
import young.blaybus.domain.senior.enums.Sex;
import young.blaybus.util.enums.CareStyle;

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

  @Enumerated(value = EnumType.STRING)
  @Comment("성별")
  private Sex sex;

  @Comment("주소")
  private String address;

  @Comment("프로필 사진")
  private String profileUrl;

  @Comment("급여")
  private Integer salary;

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

  @Enumerated(value = EnumType.STRING)
  @Comment("돌봄 스타일")
  private CareStyle careStyle;

  @Default
  @OneToMany(mappedBy = "senior", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SeniorDay> dayList = new ArrayList<>();

  @Default
  @OneToMany(mappedBy = "senior", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SeniorFoodAssist> foodAssistList = new ArrayList<>();

  @Default
  @OneToMany(mappedBy = "senior", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SeniorMoveAssist> moveAssistList = new ArrayList<>();

  @Default
  @OneToMany(mappedBy = "senior", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SeniorToiletAssist> toiletAssistList = new ArrayList<>();

  @Default
  @OneToMany(mappedBy = "senior", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SeniorLifeAssist> lifeAssistList = new ArrayList<>();

  public void update(String name, LocalDate birthday, Sex sex, String address, String profileUrl,
    LocalTime startTime, LocalTime endTime, CareStyle careStyle, Integer salary) {
    this.name = name;
    this.birthday = birthday;
    this.sex = sex;
    this.address = address;
    this.profileUrl = profileUrl;
    this.startTime = startTime;
    this.endTime = endTime;
    this.careStyle = careStyle;
    this.salary = salary;
  }
}
