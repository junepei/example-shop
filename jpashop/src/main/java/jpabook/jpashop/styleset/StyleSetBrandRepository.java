package jpabook.jpashop.styleset;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StyleSetBrandRepository extends JpaRepository<StyleSetBrand, Long> {
    StyleSetBrand findTop1OrderByTotalPrice();
}
