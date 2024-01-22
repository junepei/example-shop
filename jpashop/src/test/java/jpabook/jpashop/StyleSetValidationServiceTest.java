package jpabook.jpashop;

import jpabook.jpashop.common.exception.InternalServerErrorException;
import jpabook.jpashop.styleset.StyleSetPriceTag;
import jpabook.jpashop.styleset.StyleSetProduct;
import jpabook.jpashop.styleset.StyleSetType;
import jpabook.jpashop.styleset.StyleSetValidationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class StyleSetValidationServiceTest extends StyleSetTestData{
    @InjectMocks
    StyleSetValidationService styleSetValidationService;

    @DisplayName("testValidateLowestPriceStyleSetProducts - 성공")
    @Test
    void testValidateLowestPriceStyleSetProducts() {
        List<StyleSetProduct> styleSetProducts = getLowestPriceStyleSetProduct();

        assertThatCode(() -> styleSetValidationService.validateLowestPriceStyleSetProducts(styleSetProducts)).doesNotThrowAnyException();
    }

    @DisplayName("testValidateLowestPriceStyleSetProducts - lowest price가 잘 못 설정(부족)")
    @Test
    public void testGetLowestPriceCollection_dataError1() {
        List<StyleSetProduct> styleSetProducts = getLowestPriceStyleSetProduct();
        List<StyleSetProduct> copiedStyleSetProducts = styleSetProducts.subList(1, styleSetProducts.size() - 1);

        assertThatThrownBy(() -> styleSetValidationService.validateLowestPriceStyleSetProducts(copiedStyleSetProducts)).isInstanceOf(InternalServerErrorException.class);
    }

    @DisplayName("testValidateLowestPriceStyleSetProducts - lowest price가 잘 못 설정(초과)")
    @Test
    public void testGetLowestPriceCollection_dataError2() {
        List<StyleSetProduct> styleSetProducts = getLowestPriceStyleSetProduct();
        StyleSetProduct additionalProduct = createStyleSetProduct(brandA, new BigDecimal(1500), StyleSetType.TOP, StyleSetPriceTag.LOWEST_PRICE);
        styleSetProducts.add(additionalProduct);

        assertThatThrownBy(() -> styleSetValidationService.validateLowestPriceStyleSetProducts(styleSetProducts)).isInstanceOf(InternalServerErrorException.class);
    }
}
