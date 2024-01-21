package jpabook.jpashop.styleset.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CategoryLowestPriceCollection {
    private String categoryName;
    private StyleSetProductResponse lowestPriceProduct;
    private StyleSetProductResponse highestPriceProduct;
}
