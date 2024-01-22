package jpabook.jpashop.styleset;

import jpabook.jpashop.brand.Brand;
import jpabook.jpashop.product.Category;
import jpabook.jpashop.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class StyleSetTestData {

    protected static final Brand brandA = new Brand(1, "A");
    protected static final Brand brandB = new Brand(2, "B");
    protected static final Brand brandC = new Brand(3, "C");
    protected static final Brand brandD = new Brand(4, "D");
    protected static final Brand brandE = new Brand(5, "E");
    protected static final Brand brandF = new Brand(6, "F");
    protected static final Brand brandG = new Brand(7, "G");
    protected static final Brand brandH = new Brand(8, "H");
    protected static final Brand brandI = new Brand(9, "I");
    protected static final Category category = Category.createForTest();

    /**
     * 예제의 최저가 상품을 더미로 가져옴.
     * 카테고리 별로 최저가 겹치는 상품은 brand의 내림차순 정렬로 앞의 brand의 상품을 최저가로 설정.
     * @return
     */
    List<StyleSetProduct> getLowestPriceStyleSetProduct(){
        Product productSneakers = Product.createForTest(brandA.getBrandNo(), brandA.getBrandName(), BigDecimal.valueOf(9000), category);
        Product productBag = Product.createForTest(brandA.getBrandNo(), brandA.getBrandName(), BigDecimal.valueOf(2000), category);
        StyleSetProduct styleSetProductSneakers = StyleSetProduct.of(productSneakers, StyleSetType.SNEAKERS, StyleSetPriceTag.LOWEST_PRICE);
        StyleSetProduct styleSetProductBag = StyleSetProduct.of(productBag, StyleSetType.BAG, StyleSetPriceTag.LOWEST_PRICE);

        Product productTop = Product.createForTest(brandC.getBrandNo(), brandC.getBrandName(), BigDecimal.valueOf(10000), category);
        StyleSetProduct styleSetProductTop = StyleSetProduct.of(productTop, StyleSetType.TOP, StyleSetPriceTag.LOWEST_PRICE);

        Product productPants = Product.createForTest(brandD.getBrandNo(), brandD.getBrandName(), BigDecimal.valueOf(3000), category);
        Product productHat = Product.createForTest(brandD.getBrandNo(), brandD.getBrandName(), BigDecimal.valueOf(1500), category);
        StyleSetProduct styleSetProductPants = StyleSetProduct.of(productPants, StyleSetType.PANTS, StyleSetPriceTag.LOWEST_PRICE);
        StyleSetProduct styleSetProductHat = StyleSetProduct.of(productHat, StyleSetType.HAT, StyleSetPriceTag.LOWEST_PRICE);

        Product productOuter = Product.createForTest(brandE.getBrandNo(), brandE.getBrandName(), BigDecimal.valueOf(5000), category);
        StyleSetProduct styleSetProductOuter = StyleSetProduct.of(productOuter, StyleSetType.OUTER, StyleSetPriceTag.LOWEST_PRICE);

        Product productAccessories = Product.createForTest(brandF.getBrandNo(), brandF.getBrandName(), BigDecimal.valueOf(1900), category);
        StyleSetProduct styleSetProductAccessories = StyleSetProduct.of(productAccessories, StyleSetType.ACCESSORIES, StyleSetPriceTag.LOWEST_PRICE);

        Product productSocks = Product.createForTest(brandI.getBrandNo(), brandI.getBrandName(), BigDecimal.valueOf(1700), category);
        StyleSetProduct styleSetProductSocks = StyleSetProduct.of(productSocks, StyleSetType.SOCKS, StyleSetPriceTag.LOWEST_PRICE);

        ArrayList<StyleSetProduct> result = new ArrayList<>();
        result.add(styleSetProductSneakers);
        result.add(styleSetProductBag);
        result.add(styleSetProductTop);
        result.add(styleSetProductPants);
        result.add(styleSetProductHat);
        result.add(styleSetProductOuter);
        result.add(styleSetProductAccessories);
        result.add(styleSetProductSocks);

        return result;
    }

    StyleSetProduct createStyleSetProduct(Brand brand, BigDecimal price, StyleSetType styleSetType, StyleSetPriceTag styleSetPriceTag) {
        Product product = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), price, category);
        return StyleSetProduct.of(product, styleSetType, styleSetPriceTag);
    }

    StyleSetBrand createStyleSetBrand(Brand brand, BigDecimal totalPrice) {
        return new StyleSetBrand(brand.getBrandNo(), brand.getBrandName(), totalPrice);
    }
}
