package young.blaybus.domain.member;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import young.blaybus.config.jpa.BooleanToYNConverter;
import young.blaybus.config.jpa.CryptoConverter;
import young.blaybus.domain.address.Address;
import young.blaybus.domain.center.Center;
import young.blaybus.domain.member.enums.MemberRole;
import young.blaybus.util.enums.CareStyle;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {

  @Id
  @Column(name = "member_id")
  private String id;

  @Comment("비밀번호")
  private String password;

  @Comment("이름")
  private String name;

  @Convert(converter = CryptoConverter.class)
  @Comment("전화번호")
  private String phoneNumber;

  @Embedded
  @Comment("주소")
  private Address address;

  @Comment("프로필 이미지")
  private String profileUrl;

  @Enumerated(value = EnumType.STRING)
  @Comment("역할")
  private MemberRole role;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(columnDefinition = "char(1) default 'N'")
  @Comment("차량 소유 여부")
  private Boolean carYn;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(columnDefinition = "char(1) default 'N'")
  @Comment("치매 교육 여부")
  private Boolean dementiaEducationYn;

  @Comment("주요 경력")
  private String career;

  @Comment("한 줄 소개")
  private String introduction;

  @Comment("경력 기간")
  private String careerPeriod;

  @Enumerated(value = EnumType.STRING)
  @Comment("돌봄 스타일")
  private CareStyle careStyle;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime updatedTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "center_id")
  private Center center;
}
