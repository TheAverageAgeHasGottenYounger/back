package young.blaybus.domain.job_search;

import jakarta.persistence.*;
import lombok.*;
import young.blaybus.domain.address.Address;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class JobSearchArea {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Address address;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_search_id")
  private JobSearch jobSearch;
}