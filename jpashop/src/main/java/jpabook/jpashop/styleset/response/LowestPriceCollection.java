package jpabook.jpashop.styleset.response;

import jpabook.jpashop.styleset.StyleSetProduct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LowestPriceCollection {
    private final BigDecimal totalPrice;
    private final List<StyleSetProductResponse> products;

    public LowestPriceCollection(List<StyleSetProduct> styleSetProducts) {
        totalPrice = new BigDecimal(styleSetProducts.stream().mapToInt(p -> p.getPrice().intValue()).sum());
        products = styleSetProducts.stream().map(StyleSetProductResponse::of).collect(Collectors.toList());
    }
}
