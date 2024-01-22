package jpabook.jpashop.styleset;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface StyleSetBrandRepository extends JpaRepository<StyleSetBrand, Long> {
    StyleSetBrand findTop1ByOrderByTotalPrice();

    @Transactional
    void deleteByBrandNo(long brandNo);
}
