package jpabook.jpashop.styleset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StyleSetProductRepository extends JpaRepository<StyleSetProduct, Long> {
    List<StyleSetProduct> findByStyleSetPriceTag(StyleSetPriceTag styleSetPriceTag);
    List<StyleSetProduct> findByStyleSetTypeAndStyleSetPriceTagIn(StyleSetType styleSetType, List<StyleSetPriceTag> styleSetPriceTags);

    StyleSetProduct findTop1ByStyleSetTypeOrderByPriceDESC(StyleSetType styleSetType);

    List<StyleSetProduct> findByBrandNoAndStyleSetPriceTagIn(Long brandNo, List<StyleSetPriceTag> styleSetPriceTag);
}
