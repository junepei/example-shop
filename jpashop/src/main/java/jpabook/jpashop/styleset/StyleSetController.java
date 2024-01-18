package jpabook.jpashop.styleset;


import jpabook.jpashop.common.Response;
import jpabook.jpashop.styleset.response.BrandLowestPriceCollection;
import jpabook.jpashop.styleset.response.CategoryLowestPriceCollection;
import jpabook.jpashop.styleset.response.LowestPriceCollection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/style-set")
@RestController
public class StyleSetController {

    /**
     * 구현1 : 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     * @return
     */
    @GetMapping("/collections/lowest-price")
    public ResponseEntity<Response<LowestPriceCollection>> getLowestPriceCollection() {
        return ResponseEntity.ok(Response.success(new LowestPriceCollection()));
    }

    @GetMapping("/collections/brands/lowest-price")
    public ResponseEntity<Response<BrandLowestPriceCollection>> getLowestPriceBrandCollection() {
        return ResponseEntity.ok(Response.success(new BrandLowestPriceCollection()));
    }

    @GetMapping("/collections/categories/lowest-price")
    public ResponseEntity<Response<CategoryLowestPriceCollection>> getLowestPriceCategoryCollection(@RequestParam("name") String name) {
        return ResponseEntity.ok(Response.success(new CategoryLowestPriceCollection()));
    }
}
