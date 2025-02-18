package young.blaybus.domain.job_search;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import young.blaybus.util.enums.DayOfWeek;

import java.time.LocalTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobSearchTimeSlot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private DayOfWeek day;

  @Comment("시작 시간")
  private LocalTime startTime;

  @Comment("종료 시간")
  private LocalTime endTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_search_id")
  private JobSearch jobSearch;
}