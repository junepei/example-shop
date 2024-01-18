package jpabook.jpashop.styleset.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class LowestPriceCollection {
    private BigDecimal totalPrice;
    private List<StyleSetProduct> products;
}
