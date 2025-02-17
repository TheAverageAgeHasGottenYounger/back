package young.blaybus.domain.address;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.util.StringUtils;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Comment("시/도")
    private String city;

    @Comment("구/군")
    private String district;

    @Comment("동")
    private String dong;

    @Column(name = "address_detail")
    @Comment("상세 주소")
    private String detail;

    @Override
    public String toString() {
        String str = city + " " + district + " " + dong;
        if (StringUtils.hasText(detail))  str += " " + detail;

        return str;
    }
}
