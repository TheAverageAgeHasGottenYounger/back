package young.blaybus.domain.center;

import jakarta.persistence.*;

import java.time.LocalDateTime;
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
import young.blaybus.config.jpa.BooleanToYNConverter;
import young.blaybus.config.jpa.CryptoConverter;
import young.blaybus.domain.address.Address;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.senior.Senior;

@Entity
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Center {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("센터명")
  private String name;

  @Convert(converter = CryptoConverter.class)
  @Comment("센터 전화번호")
  private String phoneNumber;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(columnDefinition = "char(1) default 'N'")
  @Comment("목욕 차량 보유 여부")
  private Boolean bathCarYn;

  @Comment("등급")
  private String grade;

  @Comment("운영 기간")
  private String operationPeriod;

  @Embedded
  @Comment("주소")
  private Address address;

  @Comment("한 줄 소개")
  private String introduction;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime updatedTime;

  @Default
  @OneToMany(mappedBy = "center", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Member> memberList = new ArrayList<>();

  @Default
  @OneToMany(mappedBy = "center", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Senior> seniorList = new ArrayList<>();

}
