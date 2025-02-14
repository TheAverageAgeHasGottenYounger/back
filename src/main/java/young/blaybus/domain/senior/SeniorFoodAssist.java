package young.blaybus.domain.senior;

import jakarta.persistence.*;
import lombok.*;
import young.blaybus.util.enums.assist.FoodAssist;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SeniorFoodAssist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FoodAssist foodAssist;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "senior_id")
  private Senior senior;
}
