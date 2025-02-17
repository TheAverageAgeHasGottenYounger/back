package young.blaybus.domain.center.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import young.blaybus.config.jpa.BooleanToYNConverter;
import young.blaybus.domain.address.Address;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.senior.Senior;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
@Schema(description = "센터 상세 정보 조회 응답 객체")
public class GetCenterDetailInforResponse {

    @Schema(description = "센터 ID")
    private String id;

    @Schema(description = "센터 이름")
    private String name;

    @Schema(description = "목욕 차량 보유 여부")
    private Boolean bathCarYn;

    @Schema(description = "등급")
    private String grade;

    @Schema(description = "운영 기간")
    private String operationPeriod;

    @Schema(description = "시/도")
    private String city;

    @Schema(description = "구/군")
    private String gu;

    @Schema(description = "동")
    private String dong;

    @Comment("한 줄 소개")
    private String introduction;
}
