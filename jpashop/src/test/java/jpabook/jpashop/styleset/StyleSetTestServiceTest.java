package jpabook.jpashop.styleset;

import jpabook.jpashop.common.JpaShopUtils;
import jpabook.jpashop.styleset.response.BrandLowestPriceCollection;
import jpabook.jpashop.styleset.response.CategoryLowestPriceCollection;
import jpabook.jpashop.styleset.response.LowestPriceCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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

    private static MockedStatic<JpaShopUtils> jpaShopUtils;

    @DisplayName("getLowestPriceCollection - 성공 케이스")
    @Test
    void testGetLowestPriceCollection() {
        List<StyleSetProduct> styleSetProducts = getLowestPriceStyleSetProduct();

        when(styleSetProductRepository.findByStyleSetPriceTag(StyleSetPriceTag.LOWEST_PRICE)).thenReturn(styleSetProducts);
        doNothing().when(styleSetValidationService).validateLowestPriceStyleSetProducts(styleSetProducts);


        LowestPriceCollection lowestPriceCollection = styleSetService.getLowestPriceCollection();

        assertThat(lowestPriceCollection.getTotalPrice()).isEqualTo(new BigDecimal(34100));
        assertThat(lowestPriceCollection.getProducts().size()).isEqualTo(8);
    }

    @DisplayName("getLowestPriceBrandCollection - 성공 케이스")
    @Test
    void getLowestPriceBrandCollection() {
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

    @DisplayName("getLowestPriceCategoryCollection - LOWEST_PRICE와 HIGHEST_PRICE가 존재하는 경우")
    @Test
    void testGetLowestPriceCategoryCollection1() {
        List<StyleSetProduct> styleSetProducts = new ArrayList<>();
        styleSetProducts.add(createStyleSetProduct(brandD, new BigDecimal(10100), StyleSetType.TOP, StyleSetPriceTag.HIGHEST_PRICE));
        styleSetProducts.add(createStyleSetProduct(brandD, new BigDecimal(5100), StyleSetType.TOP, StyleSetPriceTag.LOWEST_PRICE));
        String styleSetTypeLabel = "상의";

        List<StyleSetPriceTag> styleSetPriceTags = new ArrayList<>();
        styleSetPriceTags.add(StyleSetPriceTag.LOWEST_PRICE);
        styleSetPriceTags.add(StyleSetPriceTag.HIGHEST_PRICE);

        when(styleSetProductRepository.findByStyleSetTypeAndStyleSetPriceTagIn(StyleSetType.TOP, styleSetPriceTags)).thenReturn(styleSetProducts);

        CategoryLowestPriceCollection categoryLowestPriceCollection = styleSetService.getLowestPriceCategoryCollection(styleSetTypeLabel);

        assertThat(categoryLowestPriceCollection.getCategoryName()).isEqualTo(styleSetTypeLabel);
        assertThat(categoryLowestPriceCollection.getLowestPriceProduct().getPrice()).isEqualTo(new BigDecimal(5100));
        assertThat(categoryLowestPriceCollection.getHighestPriceProduct().getPrice()).isEqualTo(new BigDecimal(10100));
    }

    @DisplayName("getLowestPriceCategoryCollection - LOWEST_PRICE만 존재하는 경우. 다른 상품이 있음.")
    @Test
    void testGetLowestPriceCategoryCollection2() {
        List<StyleSetProduct> styleSetProducts = new ArrayList<>();
        styleSetProducts.add(createStyleSetProduct(brandD, new BigDecimal(5100), StyleSetType.TOP, StyleSetPriceTag.LOWEST_PRICE));

        StyleSetProduct additionalStyleSetProduct = createStyleSetProduct(brandD, new BigDecimal(10100), StyleSetType.TOP, StyleSetPriceTag.HIGHEST_PRICE);

        String styleSetTypeLabel = "상의";

        List<StyleSetPriceTag> styleSetPriceTags = new ArrayList<>();
        styleSetPriceTags.add(StyleSetPriceTag.LOWEST_PRICE);
        styleSetPriceTags.add(StyleSetPriceTag.HIGHEST_PRICE);

        when(styleSetProductRepository.findByStyleSetTypeAndStyleSetPriceTagIn(StyleSetType.TOP, styleSetPriceTags)).thenReturn(styleSetProducts);
        when(styleSetProductRepository.findTop1ByStyleSetTypeOrderByPriceDESC(StyleSetType.TOP)).thenReturn(additionalStyleSetProduct);

        CategoryLowestPriceCollection categoryLowestPriceCollection = styleSetService.getLowestPriceCategoryCollection(styleSetTypeLabel);

        assertThat(categoryLowestPriceCollection.getCategoryName()).isEqualTo(styleSetTypeLabel);
        assertThat(categoryLowestPriceCollection.getLowestPriceProduct().getPrice()).isEqualTo(new BigDecimal(5100));
        assertThat(categoryLowestPriceCollection.getHighestPriceProduct().getPrice()).isEqualTo(new BigDecimal(10100));
    }


    @DisplayName("getLowestPriceCategoryCollection - LOWEST_PRICE만 존재하는 경우. 다른 상품이 없음.")
    @Test
    void testGetLowestPriceCategoryCollection3() {
        List<StyleSetProduct> styleSetProducts = new ArrayList<>();
        styleSetProducts.add(createStyleSetProduct(brandD, new BigDecimal(5100), StyleSetType.TOP, StyleSetPriceTag.LOWEST_PRICE));

        String styleSetTypeLabel = "상의";

        List<StyleSetPriceTag> styleSetPriceTags = new ArrayList<>();
        styleSetPriceTags.add(StyleSetPriceTag.LOWEST_PRICE);
        styleSetPriceTags.add(StyleSetPriceTag.HIGHEST_PRICE);

        when(styleSetProductRepository.findByStyleSetTypeAndStyleSetPriceTagIn(StyleSetType.TOP, styleSetPriceTags)).thenReturn(styleSetProducts);
        when(styleSetProductRepository.findTop1ByStyleSetTypeOrderByPriceDESC(StyleSetType.TOP)).thenReturn(null);

        CategoryLowestPriceCollection categoryLowestPriceCollection = styleSetService.getLowestPriceCategoryCollection(styleSetTypeLabel);

        assertThat(categoryLowestPriceCollection.getCategoryName()).isEqualTo(styleSetTypeLabel);
        assertThat(categoryLowestPriceCollection.getLowestPriceProduct().getPrice()).isEqualTo(new BigDecimal(5100));
        assertThat(categoryLowestPriceCollection.getHighestPriceProduct().getPrice()).isEqualTo(new BigDecimal(5100));
    }

    @DisplayName("getLowestPriceCategoryCollection - 카테고리에 상품이 없음")
    @Test
    void testGetLowestPriceCategoryCollection4() {
        String styleSetTypeLabel = "상의";

        List<StyleSetPriceTag> styleSetPriceTags = new ArrayList<>();
        styleSetPriceTags.add(StyleSetPriceTag.LOWEST_PRICE);
        styleSetPriceTags.add(StyleSetPriceTag.HIGHEST_PRICE);

        when(styleSetProductRepository.findByStyleSetTypeAndStyleSetPriceTagIn(StyleSetType.TOP, styleSetPriceTags)).thenReturn(new ArrayList<>());

        CategoryLowestPriceCollection categoryLowestPriceCollection = styleSetService.getLowestPriceCategoryCollection(styleSetTypeLabel);

        assertThat(categoryLowestPriceCollection.getCategoryName()).isEqualTo(styleSetTypeLabel);
        assertThat(categoryLowestPriceCollection.getLowestPriceProduct()).isNull();
        assertThat(categoryLowestPriceCollection.getHighestPriceProduct()).isNull();
    }
}
