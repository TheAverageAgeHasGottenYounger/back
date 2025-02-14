package young.blaybus.domain.senior;


import jakarta.persistence.*;
import lombok.*;
import young.blaybus.util.enums.assist.ToiletAssist;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SeniorToiletAssist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ToiletAssist toiletAssist;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "senior_id")
  private Senior senior;
}
