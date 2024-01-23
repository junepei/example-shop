package jpabook.jpashop.styleset;

import jpabook.jpashop.product.Product;
import jpabook.jpashop.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class StyleSetAggregateService {
    private final ProductRepository productRepository;
    private final StyleSetProductRepository styleSetProductRepository;
    private final StyleSetBrandRepository styleSetBrandRepository;


    /**
     * 브랜드의 상품중 StyleSetType(예제의 카테고리)의 최고가, 최저가가 있으면
     * 해당 브랜드외의 상품만으로 재계산하여 StyleSetType의 최고가, 최저가 상품을 지정.
     *
     * @param brandNo 브랜드 번호
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
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
        styleSetProductRepository.save(targetStyleSetProduct);
    }

    @Transactional
    public void modifyProduct(long productNo, StyleSetType styleSetType) {
        Optional<Product> targetOptional = productRepository.findById(productNo);
        if (targetOptional.isEmpty()) {
            return;
        }
        Optional<StyleSetProduct> optional = styleSetProductRepository.findById(productNo);

        optional.ifPresentOrElse(
                targetStyleSetProduct -> {
                    targetStyleSetProduct.setPrice(targetOptional.get().getPrice());
                    targetStyleSetProduct.setStyleSetType(styleSetType);
                    reflectStyleSetPriceTag(targetStyleSetProduct);
                },
                () -> {
                    StyleSetProduct targetStyleSetProduct = StyleSetProduct.of(targetOptional.get(), styleSetType);
                    reflectStyleSetPriceTag(targetStyleSetProduct);
                    styleSetProductRepository.save(targetStyleSetProduct);
                }
        );
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

    private Map<StyleSetPriceTag, StyleSetProduct> getStyleSetPriceTagMap(StyleSetProduct targetStyleSetProduct) {
        List<StyleSetPriceTag> styleSetPriceTags = new ArrayList<>();
        styleSetPriceTags.add(StyleSetPriceTag.LOWEST_PRICE);
        styleSetPriceTags.add(StyleSetPriceTag.BRAND_LOWEST_PRICE);
        styleSetPriceTags.add(StyleSetPriceTag.HIGHEST_PRICE);

        Map<StyleSetPriceTag, StyleSetProduct> map = new HashMap<>();
        List<StyleSetProduct> StyleSetProducts = styleSetProductRepository.findByStyleSetTypeAndStyleSetPriceTagIn(
                targetStyleSetProduct.getStyleSetType(), styleSetPriceTags);
        StyleSetProducts.forEach(styleSetProduct -> {
            if(styleSetProduct.getStyleSetPriceTag() == StyleSetPriceTag.LOWEST_PRICE
                    || styleSetProduct.getStyleSetPriceTag() == StyleSetPriceTag.HIGHEST_PRICE
                    || (styleSetProduct.getStyleSetPriceTag() ==
                    StyleSetPriceTag.BRAND_LOWEST_PRICE
                    && styleSetProduct.getBrandNo() == targetStyleSetProduct.getBrandNo())
            ){
                map.put(styleSetProduct.getStyleSetPriceTag(), styleSetProduct);
            }
        });

        return  map;
    }

    private void reflectStyleSetPriceTag(StyleSetProduct targetStyleSetProduct) {
        Map<StyleSetPriceTag, StyleSetProduct> map = getStyleSetPriceTagMap(targetStyleSetProduct);

        //1. 변경된 상품의 styleSetType의 최저가 찾아서 최저가 붙여주기
        Optional<StyleSetProduct> LowestOption =
                styleSetProductRepository.findTop1ByStyleSetTypeOrderByPrice(targetStyleSetProduct.getStyleSetType());

        if(LowestOption.isEmpty()) { //기존 LOWEST_PRICE가 없으면 변경된 상품이 LOWEST_PRICE상품이 됨(최초 등록 상품)
            targetStyleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.LOWEST_PRICE);
        } else if (targetStyleSetProduct.getPrice().compareTo(LowestOption.get().getPrice()) < 0){
            //변경된 상품이 최저가 일때 수정.
            targetStyleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.LOWEST_PRICE);
            if(map.containsKey(StyleSetPriceTag.LOWEST_PRICE) &&
                    map.get(StyleSetPriceTag.LOWEST_PRICE).getProductNo() != LowestOption.get().getProductNo()) {
                // 기존의 LOWEST_PRICE 상품이 존재하고 변경된 상품과 다른 상품이라면
                if (map.get(StyleSetPriceTag.LOWEST_PRICE).getBrandNo() != LowestOption.get().getBrandNo()) {
                    //변경된 상품과 브랜드가 다르다면 그 브랜드의 BRAND_LOWEST_PRICE 상품이 됨.
                    map.get(StyleSetPriceTag.LOWEST_PRICE).setStyleSetPriceTag(StyleSetPriceTag.BRAND_LOWEST_PRICE);
                } else {
                    //변경된 상품과 같은 브랜드라면 두번째로 낮은 가격의 상품이 되므로 NORMAL이 됨.
                    map.get(StyleSetPriceTag.LOWEST_PRICE).setStyleSetPriceTag(StyleSetPriceTag.NORMAL);
                }
            }
            //변동상품이 최저가라면 변동상품과 같은 브랜드의 BRAND_LOWEST_PRICE는 존재할 수 없음.
            if(map.containsKey(StyleSetPriceTag.BRAND_LOWEST_PRICE)) {
                map.get(StyleSetPriceTag.BRAND_LOWEST_PRICE).setStyleSetPriceTag(StyleSetPriceTag.NORMAL);
            }
        } else if (targetStyleSetProduct.getStyleSetPriceTag() == StyleSetPriceTag.LOWEST_PRICE) {
            //원래 LOWEST_PRICE 였는데 최저가가 아니라면 새로운 LOWEST_PRICE를 셋팅하고 변동상품은 NORMAL을 설정
            LowestOption.get().setStyleSetPriceTag(StyleSetPriceTag.LOWEST_PRICE);
            targetStyleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.NORMAL);
        }


        //2. 변경된 상품의 브랜드의 styleSetType 최저가 찾아서 LOWEST_PRICE 붙어 있지 않다면 BRAND_LOWEST_PRICE 붙여주기
        //변동된 상품이 LOWEST_PRICE상품이라면 BRAND_LOWEST_PRICE를 추가로 계산할 필요 없으므로 패스
        if (targetStyleSetProduct.getStyleSetPriceTag() != StyleSetPriceTag.LOWEST_PRICE) {
            Optional<StyleSetProduct> brandLowestOption =
                    styleSetProductRepository.findTop1ByStyleSetTypeAndBrandNoOrderByPrice(
                            targetStyleSetProduct.getStyleSetType(), targetStyleSetProduct.getBrandNo());
            if (brandLowestOption.isEmpty()
                    || targetStyleSetProduct.getPrice().compareTo(brandLowestOption.get().getPrice()) < 0) {
                // 변경 상품이 브랜드 최저가 라면 BRAND_LOWEST_PRICE를 붙여줌
                targetStyleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.BRAND_LOWEST_PRICE);
                if(map.containsKey(StyleSetPriceTag.BRAND_LOWEST_PRICE)
                 && map.get(StyleSetPriceTag.BRAND_LOWEST_PRICE).getProductNo() != targetStyleSetProduct.getProductNo()) {
                    //기존의 BRAND_LOWEST_PRICE 상품이 변경상품과 다르다면 기존 BRAND_LOWEST_PRICE상품은 NORMAL을 붙여줌.
                    map.get(StyleSetPriceTag.BRAND_LOWEST_PRICE).setStyleSetPriceTag(StyleSetPriceTag.NORMAL);
                }
            } else if (targetStyleSetProduct.getStyleSetPriceTag() == StyleSetPriceTag.BRAND_LOWEST_PRICE
            || !map.containsKey(StyleSetPriceTag.BRAND_LOWEST_PRICE)) {
                //원래 LOWEST_PRICE 였거나 (!map.containsKey(StyleSetPriceTag.BRAND_LOWEST_PRICE)로 확인)
                //원래 BRAND_LOWEST_PRICE였던 상품이라면 최저가가 아니라면 새로운 BRAND_LOWEST_PRICE 셋팅하고 변동상품은 NORMAL을 설정
                brandLowestOption.get().setStyleSetPriceTag(StyleSetPriceTag.BRAND_LOWEST_PRICE);
                targetStyleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.NORMAL);
            }
        }

        //3. styleSetType의 상품 찾아서 LOWEST_PRICE나 BRAND_LOWEST_PRICE가 붙어 잇지 않다면 HIGHEST_PRICE 붙여주기
        //1과 2작업으로 NORMAL로 변경된 상품이 HIGHEST_PRICE라면 이곳에서 HIGHEST_PRICE로 변경이 됨.
        Optional<StyleSetProduct> highestOptional =
                styleSetProductRepository.findTop1ByStyleSetTypeOrderByPriceDesc(targetStyleSetProduct.getStyleSetType());

        highestOptional.ifPresent(highestPrice -> {
            if (targetStyleSetProduct.getPrice().compareTo(highestPrice.getPrice()) > 0) {
                //변경상품이 최고가 이면 변경상품에 HIGHEST_PRICE를 붙여줌
                targetStyleSetProduct.setStyleSetPriceTag(StyleSetPriceTag.HIGHEST_PRICE);
                if(map.containsKey(StyleSetPriceTag.HIGHEST_PRICE)) {
                    //기존에 HIGHEST_PRICE 상품이 있었다면 NORMAL로 변경
                    map.get(StyleSetPriceTag.HIGHEST_PRICE).setStyleSetPriceTag(StyleSetPriceTag.NORMAL);
                }
            } else if(highestPrice.getStyleSetPriceTag() == StyleSetPriceTag.NORMAL){
                //Normal 일때만 HIGHEST_PRICE를 붙일 수 있음.
                highestOptional.get().setStyleSetPriceTag(StyleSetPriceTag.HIGHEST_PRICE);
            }
        });
    }
}

