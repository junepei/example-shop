package jpabook.jpashop.styleset;

import com.sun.istack.NotNull;
import jpabook.jpashop.product.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class StyleSetProduct {
    @Id
    private long productNo;
    @NotNull
    private StyleSetType styleSetType;
    @NotNull
    private StyleSetPriceTag styleSetPriceTag;
    @NotNull
    private long brandNo;
    @NotNull
    private String brandName;
    @NotNull
    private BigDecimal price;


    public static StyleSetProduct of(Product product, StyleSetType styleSetType) {
        StyleSetProduct styleSetProduct = new StyleSetProduct();
        styleSetProduct.setProductNo(product.getProductNo());
        styleSetProduct.setStyleSetType(styleSetType);
        styleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.NORMAL);
        styleSetProduct.setBrandNo(product.getBrandNo());
        styleSetProduct.setBrandName(product.getBrandName());
        styleSetProduct.setPrice(product.getPrice());
        return styleSetProduct;
    }

    public static StyleSetProduct of(Product product, StyleSetType styleSetType, StyleSetPriceTag styleSetPriceTag) {
        StyleSetProduct styleSetProduct = new StyleSetProduct();
        styleSetProduct.setProductNo(product.getProductNo());
        styleSetProduct.setStyleSetType(styleSetType);
        styleSetProduct.setStyleSetPriceTag(styleSetPriceTag);
        styleSetProduct.setBrandNo(product.getBrandNo());
        styleSetProduct.setBrandName(product.getBrandName());
        styleSetProduct.setPrice(product.getPrice());
        return styleSetProduct;
    }
}
