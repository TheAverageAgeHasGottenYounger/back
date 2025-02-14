package young.blaybus.domain.job_search;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class JobSearchArea {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String city;
  private String guGun;
  private String dong;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_search_id")
  private JobSearch jobSearch;
}