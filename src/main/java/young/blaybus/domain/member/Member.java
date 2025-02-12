package young.blaybus.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import young.blaybus.domain.center.Center;
import young.blaybus.domain.member.enums.MemberRole;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {

  @Id
  private String id;

  @Comment("비밀번호")
  private String password;

  @Comment("이름")
  private String name;

  @Convert(converter = CryptoConverter.class)
  @Comment("전화번호")
  private String phoneNumber;

  @Comment("주소")
  private String address;

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

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime updatedTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "center_id")
  private Center center;

}
