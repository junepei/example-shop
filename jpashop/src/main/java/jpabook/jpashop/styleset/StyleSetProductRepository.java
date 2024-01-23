package jpabook.jpashop.styleset;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface StyleSetProductRepository extends JpaRepository<StyleSetProduct, Long> {
    List<StyleSetProduct> findByStyleSetPriceTag(StyleSetPriceTag styleSetPriceTag);
    List<StyleSetProduct> findByStyleSetTypeAndStyleSetPriceTagIn(StyleSetType styleSetType, List<StyleSetPriceTag> styleSetPriceTags);

    List<StyleSetProduct> findByBrandNoAndStyleSetPriceTagIn(Long brandNo, List<StyleSetPriceTag> styleSetPriceTag);

    @Transactional
    Optional<StyleSetProduct> findByStyleSetTypeAndStyleSetPriceTag(StyleSetType styleSetType, StyleSetPriceTag styleSetPriceTag);

    @Transactional
    Optional<StyleSetProduct> findByBrandNoAndStyleSetTypeAndStyleSetPriceTag(
            Long brandNo,StyleSetType styleSetType, StyleSetPriceTag styleSetPriceTag);

    @Transactional
    Optional<StyleSetProduct> findTop1ByStyleSetTypeOrderByPriceDesc(StyleSetType styleSetType);

    @Transactional
    Optional<StyleSetProduct> findTop1ByStyleSetTypeOrderByPrice(StyleSetType styleSetType);

    @Transactional
    Optional<StyleSetProduct> findTop1ByStyleSetTypeAndBrandNoNotOrderByPriceDesc(StyleSetType styleSetType,
                                                                                  long brandNo);
    @Transactional
    Optional<StyleSetProduct> findTop1ByStyleSetTypeAndBrandNoNotOrderByPrice(StyleSetType styleSetType,
                                                                              long brandNo);


    @Transactional
    Optional<StyleSetProduct> findTop1ByStyleSetTypeAndBrandNoOrderByPrice(
            StyleSetType styleSetType, long brandNo);

    @Transactional
    Optional<StyleSetProduct> findTop1ByStyleSetTypeAndBrandNoOrderByPriceDesc(
            StyleSetType styleSetType, long brandNo);

    @Transactional
    void deleteByBrandNo(long brandNo);
}
