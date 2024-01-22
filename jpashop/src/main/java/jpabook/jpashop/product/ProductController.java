package jpabook.jpashop.product;

import jpabook.jpashop.common.Response;
import jpabook.jpashop.product.request.AddProductRequest;
import jpabook.jpashop.product.request.ModifyProductRequest;
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
    @PostMapping("/")
    public ResponseEntity<Response> addProduct(@Valid @RequestBody AddProductRequest request) {
        productService.addProduct( request.getCategoryNo(), request.getBrandNo(), request.getPrice(), request.getStyleSetType());
        return ResponseEntity.ok(Response.success());
    }
}
