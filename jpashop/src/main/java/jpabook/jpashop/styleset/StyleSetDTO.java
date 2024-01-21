package jpabook.jpashop.styleset;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StyleSetDTO {
    private StyleSetType styleSetType;
    private long brandNo;
    private String brandName;
    private BigDecimal price;
}
