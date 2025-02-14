package young.blaybus.domain.senior;

import jakarta.persistence.*;
import lombok.*;
import young.blaybus.util.enums.assist.MoveAssist;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SeniorMoveAssist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MoveAssist moveAssist;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "senior_id")
  private Senior senior;
}
