package jpabook.jpashop;

import jpabook.jpashop.common.JpaShopUtils;
import jpabook.jpashop.common.exception.InternalServerErrorException;
import jpabook.jpashop.styleset.*;
import jpabook.jpashop.styleset.response.LowestPriceCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StyleSetTestServiceTest extends StyleSetTestData {

    @InjectMocks
    StyleSetService styleSetService;

    @Mock
    StyleSetProductRepository styleSetProductRepository;

    @Mock
    StyleSetBrandRepository styleSetBrandRepository;

    @DisplayName("getLowestPriceCollection - 성공적인 케이스")
    @Test
    public void testGetLowestPriceCollection() {
        when(styleSetProductRepository.findByStyleSetPriceTag(StyleSetPriceTag.LOWEST_PRICE)).thenReturn(getLowestPriceStyleSetProduct());

        LowestPriceCollection lowestPriceCollection = styleSetService.getLowestPriceCollection();

        assertThat(lowestPriceCollection.getTotalPrice()).isEqualTo(new BigDecimal(34100));
        assertThat(lowestPriceCollection.getProducts().size()).isEqualTo(8);
    }

    @DisplayName("getLowestPriceCollection - lowest price가 잘 못 설정(부족)")
    @Test
    public void testGetLowestPriceCollection_dataError1() {
        List<StyleSetProduct> styleSetProducts = getLowestPriceStyleSetProduct();
        List<StyleSetProduct> copiedStyleSetProducts = styleSetProducts.subList(1, styleSetProducts.size() -1);

        when(styleSetProductRepository.findByStyleSetPriceTag(StyleSetPriceTag.LOWEST_PRICE)).thenReturn(copiedStyleSetProducts);

        assertThatThrownBy(() -> styleSetService.getLowestPriceCollection()).isInstanceOf(InternalServerErrorException.class);
    }

    @DisplayName("getLowestPriceCollection - lowest price가 잘 못 설정(초과)")
    @Test
    public void testGetLowestPriceCollection_dataError2() {
        List<StyleSetProduct> styleSetProducts = getLowestPriceStyleSetProduct();
        StyleSetProduct additionalProduct = createStyleSetProduct(brandA, new BigDecimal(1500), StyleSetType.TOP, StyleSetPriceTag.LOWEST_PRICE);
        styleSetProducts.add(additionalProduct);

        when(styleSetProductRepository.findByStyleSetPriceTag(StyleSetPriceTag.LOWEST_PRICE)).thenReturn(styleSetProducts);

        assertThatThrownBy(() -> styleSetService.getLowestPriceCollection()).isInstanceOf(InternalServerErrorException.class);
    }
}
