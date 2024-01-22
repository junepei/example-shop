package jpabook.jpashop.styleset;

import jpabook.jpashop.common.exception.BadRequestException;
import jpabook.jpashop.common.exception.InternalServerErrorException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StyleSetValidationService {
    public void validateLowestPriceStyleSetProducts(List<StyleSetProduct> styleSetProducts) {
        if (StyleSetType.values().length != styleSetProducts.size()) {
            List<StyleSetType> savedStyleSetTypes = styleSetProducts.stream().map(StyleSetProduct::getStyleSetType).collect(Collectors.toList());
            if (StyleSetType.values().length > styleSetProducts.size()) {
                //StyleSetType중 일부가 설정되지 않았을 때.
                for (StyleSetType styleSetType : StyleSetType.values()) {
                    if (!savedStyleSetTypes.contains(styleSetType)) {
                        throw new InternalServerErrorException("StyleSetProduct의 최저가 설정이 잘못 되었습니다. styleSetType : "
                                + styleSetType
                                + "의 상품이 없습니다."
                        );
                    }
                }
            } else {
                //StyleSetType중 중복해서 설정 되었을 때.
                Set<StyleSetType> problems = savedStyleSetTypes.stream()
                        .filter(i -> Collections.frequency(savedStyleSetTypes, i) > 1)
                        .collect(Collectors.toSet());
                throw new InternalServerErrorException("StyleSetProduct의 최저가 설정이 잘못 되었습니다. "
                        + problems.toString()
                        + "의 설정이 중복되었습니다."
                );
            }
        }
    }

    public void validateStyleSetTypeLabel(StyleSetType styleSetType, String styleSetTypeLabel) {
        if(styleSetType == null) {
            throw new BadRequestException("잘못된 카테고리명입니다. 카테고리명 : " + styleSetTypeLabel);
        }
    }
}
