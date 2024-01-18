package jpabook.jpashop.styleset.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class BrandLowestPriceCollection {
    private BigDecimal totalPrice;
    private String brandName;
    private List<StyleSetProduct> products;
}
