package jpabook.jpashop.styleset;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class StyleSetBrand {

    @Id
    private long brandNo;
    @NotNull
    private String brandName;

    private BigDecimal totalPrice; //가장 낮은 가격. 8개 카테고리의 상품이 모두 없다면 0으로 저장.
}
