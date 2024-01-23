package jpabook.jpashop.styleset;

import jpabook.jpashop.common.JpaShopUtils;
import jpabook.jpashop.styleset.response.BrandLowestPriceCollection;
import jpabook.jpashop.styleset.response.CategoryLowestPriceCollection;
import jpabook.jpashop.styleset.response.LowestPriceCollection;
import jpabook.jpashop.styleset.response.StyleSetProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StyleSetService {

    private final StyleSetValidationService styleSetValidationService;
    private final StyleSetProductRepository styleSetProductRepository;
    private final StyleSetBrandRepository styleSetBrandRepository;

    public LowestPriceCollection getLowestPriceCollection() {
        List<StyleSetProduct> styleSetProducts = styleSetProductRepository.findByStyleSetPriceTag(StyleSetPriceTag.LOWEST_PRICE);
        styleSetValidationService.validateLowestPriceStyleSetProducts(styleSetProducts);
        return new LowestPriceCollection(styleSetProducts);
    }

    public BrandLowestPriceCollection getLowestPriceBrandCollection() {
        StyleSetBrand styleSetBrand = styleSetBrandRepository.findTop1ByOrderByTotalPrice();
        ArrayList<StyleSetPriceTag> styleSetPriceTags = new ArrayList<>();
        styleSetPriceTags.add(StyleSetPriceTag.LOWEST_PRICE);
        styleSetPriceTags.add(StyleSetPriceTag.BRAND_LOWEST_PRICE);
        List<StyleSetProduct> styleSetProducts = styleSetProductRepository.findByBrandNoAndStyleSetPriceTagIn(styleSetBrand.getBrandNo(), styleSetPriceTags);

        styleSetValidationService.validateLowestPriceStyleSetProducts(styleSetProducts);

        return new BrandLowestPriceCollection(styleSetBrand.getBrandName(), styleSetBrand.getTotalPrice(), styleSetProducts.stream().map(StyleSetProductResponse::of).collect(Collectors.toList()));
    }

    public CategoryLowestPriceCollection getLowestPriceCategoryCollection(String styleSetTypeLabel) {
        StyleSetType styleSetType = JpaShopUtils.getStyleSetTypeByLabel(styleSetTypeLabel);
        styleSetValidationService.validateStyleSetTypeLabel(styleSetType, styleSetTypeLabel);

        List<StyleSetPriceTag> styleSetPriceTags = new ArrayList<>();
        styleSetPriceTags.add(StyleSetPriceTag.LOWEST_PRICE);
        styleSetPriceTags.add(StyleSetPriceTag.HIGHEST_PRICE);

        List<StyleSetProduct> styleSetProducts = styleSetProductRepository.findByStyleSetTypeAndStyleSetPriceTagIn(styleSetType, styleSetPriceTags);

        //styleSetProducts 인자가 한개도 없는 경우 : StyleSetType 에 상품이 하나도 등록되지 않은 경우.
        // 처리 >> 상품이 비어있는 채로 내려보낸다.

        //styleSetProducts 인자가 한개인 경우 : StyleSetType의 우선순위는 LOWEST_PRICE > BRAND_LOWEST_PRICE 이므로 상품이 적을때는 HIGHEST_PRICE 인 상품이 없을 수 있다.
        // 처리 >> 다시 DB를 조회하여 최고가 상품을 찾아서 내려보낸다. 다시 조회를 해도 한개 라면 최저가와 최고가 상품을 동일상품으로 내려보낸다.
        if (styleSetProducts.size() == 1) {
            Optional<StyleSetProduct> optional = styleSetProductRepository.findTop1ByStyleSetTypeOrderByPriceDesc(styleSetType);
            optional.ifPresent(styleSetProducts::add);
        }

        //getLabel() NPE위험 경고. 위에 validateStyleSetTypeName을 통해 이미 검증
        return new CategoryLowestPriceCollection(styleSetType.getLabel(),
                styleSetProducts.stream().map(StyleSetProductResponse::of).collect(Collectors.toList()));
    }
}
