package jpabook.jpashop.styleset;

import jpabook.jpashop.common.Response;
import jpabook.jpashop.styleset.response.BrandLowestPriceCollection;
import jpabook.jpashop.styleset.response.CategoryLowestPriceCollection;
import jpabook.jpashop.styleset.response.LowestPriceCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/style-set")
@RequiredArgsConstructor
@RestController
public class StyleSetController {

    private final StyleSetService styleSetService;
    /**
     * 구현1 : 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     * @return
     */
    @GetMapping("/collections/lowest-price")
    public ResponseEntity<Response<LowestPriceCollection>> getLowestPriceCollection() {
        return ResponseEntity.ok(Response.success(
                styleSetService.getLowestPriceCollection()
        ));
    }

    /**
     * 구현2 : 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
     * @return
     */
    @GetMapping("/collections/brands/lowest-price")
    public ResponseEntity<Response<BrandLowestPriceCollection>> getLowestPriceBrandCollection() {
        return ResponseEntity.ok(Response.success(
                styleSetService.getLowestPriceBrandCollection()
        ));
    }

    /**
     * 구현3 :  카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
     * @param name
     * @return
     */
    @GetMapping("/collections/categories/lowest-price")
    public ResponseEntity<Response<CategoryLowestPriceCollection>> getLowestPriceCategoryCollection(@RequestParam("name") String name) {
        return ResponseEntity.ok(Response.success(new CategoryLowestPriceCollection()));
    }
}
