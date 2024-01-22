package jpabook.jpashop;

import jpabook.jpashop.styleset.*;
import jpabook.jpashop.styleset.response.BrandLowestPriceCollection;
import jpabook.jpashop.styleset.response.LowestPriceCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StyleSetTestServiceTest extends StyleSetTestData {

    @InjectMocks
    StyleSetService styleSetService;

    @Mock
    StyleSetProductRepository styleSetProductRepository;

    @Mock
    StyleSetBrandRepository styleSetBrandRepository;

    @Mock
    StyleSetValidationService styleSetValidationService;

    @DisplayName("getLowestPriceCollection - 성공 케이스")
    @Test
    public void testGetLowestPriceCollection() {
        List<StyleSetProduct> styleSetProducts = getLowestPriceStyleSetProduct();

        when(styleSetProductRepository.findByStyleSetPriceTag(StyleSetPriceTag.LOWEST_PRICE)).thenReturn(styleSetProducts);
        doNothing().when(styleSetValidationService).validateLowestPriceStyleSetProducts(styleSetProducts);


        LowestPriceCollection lowestPriceCollection = styleSetService.getLowestPriceCollection();

        assertThat(lowestPriceCollection.getTotalPrice()).isEqualTo(new BigDecimal(34100));
        assertThat(lowestPriceCollection.getProducts().size()).isEqualTo(8);
    }

    @DisplayName("getLowestPriceBrandCollection - 성공 케이스")
    @Test
    public void getLowestPriceBrandCollection() {
        StyleSetBrand styleSetBrand = createStyleSetBrand(brandD, new BigDecimal(36100));
        List<StyleSetProduct> styleSetProducts = new ArrayList<>();
        styleSetProducts.add(createStyleSetProduct(brandD, new BigDecimal(10100), StyleSetType.TOP, StyleSetPriceTag.BRAND_LOWEST_PRICE));
        styleSetProducts.add(createStyleSetProduct(brandD, new BigDecimal(5100), StyleSetType.OUTER, StyleSetPriceTag.BRAND_LOWEST_PRICE));
        styleSetProducts.add(createStyleSetProduct(brandD, new BigDecimal(3000), StyleSetType.PANTS, StyleSetPriceTag.LOWEST_PRICE));
        styleSetProducts.add(createStyleSetProduct(brandD, new BigDecimal(9500), StyleSetType.SNEAKERS, StyleSetPriceTag.BRAND_LOWEST_PRICE));
        styleSetProducts.add(createStyleSetProduct(brandD, new BigDecimal(2500), StyleSetType.BAG, StyleSetPriceTag.BRAND_LOWEST_PRICE));
        styleSetProducts.add(createStyleSetProduct(brandD, new BigDecimal(1500), StyleSetType.HAT, StyleSetPriceTag.LOWEST_PRICE));
        styleSetProducts.add(createStyleSetProduct(brandD, new BigDecimal(2400), StyleSetType.SOCKS, StyleSetPriceTag.BRAND_LOWEST_PRICE));
        styleSetProducts.add(createStyleSetProduct(brandD, new BigDecimal(2000), StyleSetType.ACCESSORIES, StyleSetPriceTag.BRAND_LOWEST_PRICE));

        ArrayList<StyleSetPriceTag> styleSetPriceTags = new ArrayList<>();
        styleSetPriceTags.add(StyleSetPriceTag.LOWEST_PRICE);
        styleSetPriceTags.add(StyleSetPriceTag.BRAND_LOWEST_PRICE);

        when(styleSetBrandRepository.findTop1OrderByTotalPrice()).thenReturn(styleSetBrand);
        when(styleSetProductRepository.findByBrandNoAndStyleSetPriceTagIn(brandD.getBrandNo(), styleSetPriceTags)).thenReturn(styleSetProducts);

        BrandLowestPriceCollection brandLowestPriceCollection = styleSetService.getLowestPriceBrandCollection();

        assertThat(brandLowestPriceCollection.getBrandName()).isEqualTo(brandD.getBrandName());
        assertThat(brandLowestPriceCollection.getTotalPrice()).isEqualTo(new BigDecimal(36100));
        assertThat(brandLowestPriceCollection.getProducts().size()).isEqualTo(8);
    }

}
