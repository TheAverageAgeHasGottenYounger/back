package young.blaybus.domain.job_search;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import young.blaybus.domain.member.Member;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class JobSearch {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("시작 시간")
  private LocalTime startTime;

  @Comment("종료 시간")
  private LocalTime endTime;

  @Comment("희망 급여")
  private Integer salary;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime updatedTime;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Builder.Default
  @OneToMany(mappedBy = "jobSearch", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<JobSearchArea> jobSearchAreas = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "jobSearch", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<JobSearchDay> dayList = new ArrayList<>();

}
