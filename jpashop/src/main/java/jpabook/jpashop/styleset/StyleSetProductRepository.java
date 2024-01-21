package jpabook.jpashop.styleset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StyleSetProductRepository extends JpaRepository<StyleSetProduct, Long> {
    List<StyleSetProduct> findByStyleSetPriceTag(StyleSetPriceTag styleSetPriceTag);
}
