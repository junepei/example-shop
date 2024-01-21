package jpabook.jpashop.styleset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleSetRepository extends JpaRepository<StyleSet, Long>, StyleSetRepositoryCustom {
}
