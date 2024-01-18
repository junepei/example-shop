package jpabook.jpashop.styleset.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CategoryLowestPriceCollection {
    private String categoryName;
    private StyleSetProduct lowestPriceProduct;
    private StyleSetProduct highestPriceProduct;
}
