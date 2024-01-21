package jpabook.jpashop.styleset.response;

import jpabook.jpashop.styleset.StyleSetProduct;
import jpabook.jpashop.styleset.StyleSetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class StyleSetProductResponse {
    private StyleSetType styleSetType;
    private String styleSetName;
    private long brandNo;
    private String brandName;
    private long productNo;
    private BigDecimal price;

    public static StyleSetProductResponse of(StyleSetProduct styleSetProduct) {
        StyleSetProductResponse styleSetProductResponse = new StyleSetProductResponse();
        styleSetProductResponse.setStyleSetType(styleSetProduct.getStyleSetType());
        styleSetProductResponse.setStyleSetName(styleSetProduct.getStyleSetType().getName());
        styleSetProductResponse.setBrandNo(styleSetProduct.getBrandNo());
        styleSetProductResponse.setBrandName(styleSetProduct.getBrandName());
        styleSetProductResponse.setProductNo(styleSetProduct.getProductNo());
        styleSetProductResponse.setPrice(styleSetProduct.getPrice());
        return styleSetProductResponse;
    }
}
