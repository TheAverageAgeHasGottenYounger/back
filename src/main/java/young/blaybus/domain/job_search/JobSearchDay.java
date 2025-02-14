package young.blaybus.domain.job_search;

import jakarta.persistence.*;
import lombok.*;
import young.blaybus.util.enums.DayOfWeek;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class JobSearchDay {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private DayOfWeek day;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_search_id")
  private JobSearch jobSearch;
}
