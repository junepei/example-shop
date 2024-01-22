package jpabook.jpashop.brand;

import jpabook.jpashop.brand.request.AddBrandRequest;
import jpabook.jpashop.brand.request.ModifyBrandRequest;
import jpabook.jpashop.common.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;

    @PostMapping("/")
    public ResponseEntity<Response> addBrand(@Valid @RequestBody AddBrandRequest request) {
        brandService.addBrand(request.getBrandName());
        return ResponseEntity.ok(Response.success());
    }

    @PutMapping("/{brandNo}")
    public ResponseEntity<Response> modifyBrand(@PathVariable long brandNo, @Valid @RequestBody ModifyBrandRequest request) {
        brandService.modifyBrand(brandNo, request.getBrandName());
        return ResponseEntity.ok(Response.success());
    }

    @DeleteMapping("/{brandNo}")
    public ResponseEntity<Response> removeBrand(@PathVariable long brandNo) {
        brandService.removeBrand(brandNo);
        return ResponseEntity.ok(Response.success());
    }
}
