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
import young.blaybus.domain.job_search.request.JobSearchAreaRequest;
import young.blaybus.domain.job_search.request.UpdateJobSearchRequest;
import young.blaybus.domain.member.Member;
import young.blaybus.util.enums.DayOfWeek;

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

  public void updateFromDto(UpdateJobSearchRequest request){
    if(request.startTime()!=null) {
      this.startTime = LocalTime.parse(request.startTime());
    }

    if(request.endTime()!=null) {
      this.endTime = LocalTime.parse(request.endTime());
    }

    if(request.salary()!=null) {
      this.salary = request.salary();
    }

    if(!request.jobSearchAreas().isEmpty()) {
      this.jobSearchAreas.clear();
        for (JobSearchAreaRequest jobSearchAreaRequest : request.jobSearchAreas()) {
          this.jobSearchAreas.add(JobSearchArea.builder()
                  .jobSearch(this)
                  .address(jobSearchAreaRequest.address())
                  .build());
        }
    }

    if(!request.dayList().isEmpty()) {
      this.dayList.clear();
      for (DayOfWeek dayOfWeek : request.dayList()) {
        this.dayList.add(JobSearchDay.builder()
                .jobSearch(this)
                .day(dayOfWeek)
                .build());
      }
    }
  }


}
