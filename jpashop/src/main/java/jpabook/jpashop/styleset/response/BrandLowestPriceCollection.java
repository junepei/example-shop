package jpabook.jpashop.styleset.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class BrandLowestPriceCollection {
    private String brandName;
    private BigDecimal totalPrice;
    private List<StyleSetProductResponse> products;
}
