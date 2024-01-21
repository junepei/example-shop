package jpabook.jpashop.styleset;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StyleSetRepositoryCustom {
    List<StyleSetDTO> findLowestStyleSetDTO();
}
