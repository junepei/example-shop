package jpabook.jpashop.styleset.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class CategoryLowestPriceCollection {
    private String categoryName;
    private StyleSetProductResponse lowestPriceProduct;
    private StyleSetProductResponse highestPriceProduct;

    public CategoryLowestPriceCollection(String categoryName, List<StyleSetProductResponse> styleSetProductResponses) {
        this.categoryName = categoryName;

        Optional<StyleSetProductResponse> lowestOption = styleSetProductResponses.stream().min((product1, product2) -> {
            return product1.getPrice().intValue() - product2.getPrice().intValue();
        });
        lowestOption.ifPresent(styleSetProductResponse -> this.lowestPriceProduct = styleSetProductResponse);

        Optional<StyleSetProductResponse> highestOption = styleSetProductResponses.stream().max((product1, product2) -> {
            return product1.getPrice().intValue() - product2.getPrice().intValue();
        });
        highestOption.ifPresent(styleSetProductResponse -> this.highestPriceProduct = styleSetProductResponse);
    }
}
