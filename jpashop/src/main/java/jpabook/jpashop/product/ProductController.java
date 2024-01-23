package jpabook.jpashop.product;

import jpabook.jpashop.common.Response;
import jpabook.jpashop.product.request.AddProductRequest;
import jpabook.jpashop.product.request.ModifyProductRequest;
import jpabook.jpashop.product.response.AddProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {
    private final ProductService productService;

    @DeleteMapping("/{productNo}")
    public ResponseEntity<Response> removeProduct(@PathVariable long productNo) {
        productService.removeProduct(productNo);
        return ResponseEntity.ok(Response.success());
    }

    @PutMapping("/{productNo}")
    public ResponseEntity<Response> modifyProduct(@PathVariable long productNo, @Valid @RequestBody ModifyProductRequest request) {
        productService.modifyProduct(productNo, request.getPrice(), request.getStyleSetType());
        return ResponseEntity.ok(Response.success());
    }
    @PostMapping()
    public ResponseEntity<Response<AddProductResponse>> addProduct(@Valid @RequestBody AddProductRequest request) {
        long productNo = productService.addProduct( request.getCategoryNo(), request.getBrandNo(), request.getPrice(), request.getStyleSetType());
        return ResponseEntity.ok(Response.success(new AddProductResponse(productNo)));
    }
}
