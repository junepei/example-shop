package jpabook.jpashop.styleset.response;

import jpabook.jpashop.styleset.StyleSetType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class StyleSetProduct {
    private StyleSetType styleSetType;
    private String styleSetName;
    private long brandNo;
    private String brandName;
    private long productNo;
    private BigDecimal price;
}
