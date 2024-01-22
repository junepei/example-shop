package jpabook.jpashop.styleset;

import jpabook.jpashop.product.Product;
import jpabook.jpashop.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StyleSetAggregateService {
    private final ProductRepository productRepository;
    private final StyleSetProductRepository styleSetProductRepository;
    private final StyleSetBrandRepository styleSetBrandRepository;


    /**
     * 브랜드의 상품중 StyleSetType(예제의 카테고리)의 최고가, 최저가가 있으면
     * 해당 브랜드외의 상품만으로 재계산하여 StyleSetType의 최고가, 최저가 상품을 지정.
     * @param brandNo 브랜드 번호
     */
    @Transactional
    public void removeBrand(long brandNo) {
        List<StyleSetPriceTag> styleSetPriceTagList = new ArrayList<>();
        styleSetPriceTagList.add(StyleSetPriceTag.LOWEST_PRICE);
        styleSetPriceTagList.add(StyleSetPriceTag.HIGHEST_PRICE);

        //브랜드의 상품중 최고가, 최저가 표시가된 상품을 가져옴. 최고가, 최저가 재계산 대상 상품
        List<StyleSetProduct> styleSetProducts = styleSetProductRepository.findByBrandNoAndStyleSetPriceTagIn(brandNo, styleSetPriceTagList);

        //타브랜드의 상품으로 최고가, 최저가 지정
        styleSetProducts.forEach(styleSetProduct ->
                calculateMinAndMaxPrice(
                        styleSetProduct.getBrandNo(),
                        styleSetProduct.getStyleSetType(),
                        styleSetProduct.getStyleSetPriceTag()));

        styleSetBrandRepository.deleteByBrandNo(brandNo);
        styleSetProductRepository.deleteByBrandNo(brandNo);
    }

    private void calculateMinAndMaxPrice(long brandNo, StyleSetType styleSetType, StyleSetPriceTag styleSetPriceTag) {
        if (styleSetPriceTag == StyleSetPriceTag.HIGHEST_PRICE) {
            Optional<StyleSetProduct> optional = styleSetProductRepository.findTop1ByStyleSetTypeAndBrandNoNotOrderByPriceDesc(styleSetType, brandNo);
            optional.ifPresent(styleSetProduct ->
                    styleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.HIGHEST_PRICE));


        } else if (styleSetPriceTag == StyleSetPriceTag.LOWEST_PRICE) {
            Optional<StyleSetProduct> optional = styleSetProductRepository.findTop1ByStyleSetTypeAndBrandNoNotOrderByPrice(styleSetType, brandNo);
            optional.ifPresent(styleSetProduct ->
                    styleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.LOWEST_PRICE));
        }
    }

    @Transactional
    public void removeProduct(long productNo) {
        Optional<StyleSetProduct> targetOptional = styleSetProductRepository.findById(productNo);
        if (targetOptional.isEmpty()) {
            return;
        }

        StyleSetProduct targetStyleSetProduct = targetOptional.get();
        styleSetProductRepository.delete(targetStyleSetProduct);
        reflectStyleSetPriceTagByRemove(targetStyleSetProduct);
    }

    @Transactional
    public void addProduct(long productNo, StyleSetType styleSetType) {
        Optional<Product> targetOptional = productRepository.findById(productNo);
        if (targetOptional.isEmpty()) {
            return;
        }

        StyleSetProduct targetStyleSetProduct = StyleSetProduct.of(targetOptional.get(), styleSetType);
        reflectStyleSetPriceTag(targetStyleSetProduct);
    }

    @Transactional
    public void modifyProduct(long productNo, StyleSetType styleSetType) {
        Optional<Product> targetOptional = productRepository.findById(productNo);
        if (targetOptional.isEmpty()) {
            return;
        }

        StyleSetProduct targetStyleSetProduct = StyleSetProduct.of(targetOptional.get(), styleSetType);
        reflectStyleSetPriceTag(targetStyleSetProduct);
    }

    private void reflectStyleSetPriceTagByRemove(StyleSetProduct targetStyleSetProduct) {
        switch (targetStyleSetProduct.getStyleSetPriceTag()) {
            case LOWEST_PRICE:
                Optional<StyleSetProduct> LowestOptional = styleSetProductRepository.findTop1ByStyleSetTypeOrderByPrice(
                        targetStyleSetProduct.getStyleSetType()
                );
                LowestOptional.ifPresent(styleSetProduct ->
                        styleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.LOWEST_PRICE));
                break;
            case HIGHEST_PRICE:
                Optional<StyleSetProduct> highestOptional = styleSetProductRepository.findTop1ByStyleSetTypeOrderByPriceDesc(
                        targetStyleSetProduct.getStyleSetType()
                );
                highestOptional.ifPresent(styleSetProduct ->
                        styleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.HIGHEST_PRICE));
                break;
            case BRAND_LOWEST_PRICE:
                Optional<StyleSetProduct> brandLowestOptional = styleSetProductRepository.findTop1ByStyleSetTypeAndBrandNoOrderByPrice(
                        targetStyleSetProduct.getStyleSetType(),
                        targetStyleSetProduct.getBrandNo()
                );
                brandLowestOptional.ifPresent(styleSetProduct ->
                        styleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.BRAND_LOWEST_PRICE));
                break;
        }
    }

    private void reflectStyleSetPriceTag(StyleSetProduct targetStyleSetProduct) {
        // 카테고리(StyleSetType)의 최저가인지 확인.
        Optional<StyleSetProduct> lowestOptional = styleSetProductRepository.findTop1ByStyleSetTypeOrderByPrice(
                targetStyleSetProduct.getStyleSetType());

        if(lowestOptional.isPresent() &&
                lowestOptional.get().getPrice().compareTo(targetStyleSetProduct.getPrice()) < 0) {
            targetStyleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.LOWEST_PRICE);
            lowestOptional.get().setStyleSetPriceTag(StyleSetPriceTag.BRAND_LOWEST_PRICE);
            return;
        }

        // 브랜드 중 카테고리(StyleSetType)의 최저가인지 확인.
        Optional<StyleSetProduct> brandLowestOptional = styleSetProductRepository.findTop1ByStyleSetTypeAndBrandNoOrderByPrice(
                targetStyleSetProduct.getStyleSetType(), targetStyleSetProduct.getBrandNo());

        if(brandLowestOptional.isPresent() &&
                brandLowestOptional.get().getPrice().compareTo(targetStyleSetProduct.getPrice()) < 0) {
            targetStyleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.BRAND_LOWEST_PRICE);
            brandLowestOptional.get().setStyleSetPriceTag(StyleSetPriceTag.NORMAL);
            return;
        }

        // 카테고리(StyleSetType)의 최고가인지 확인.
        Optional<StyleSetProduct> higestOptional = styleSetProductRepository.findTop1ByStyleSetTypeOrderByPriceDesc(
                targetStyleSetProduct.getStyleSetType());

        if(higestOptional.isPresent() &&
                higestOptional.get().getPrice().compareTo(targetStyleSetProduct.getPrice()) > 0) {
            targetStyleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.HIGHEST_PRICE);
            higestOptional.get().setStyleSetPriceTag(StyleSetPriceTag.NORMAL);
        }
    }
}

