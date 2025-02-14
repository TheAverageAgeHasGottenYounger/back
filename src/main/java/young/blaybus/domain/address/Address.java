package young.blaybus.domain.address;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Embeddable
@Getter
@Setter
@ToString
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

}
