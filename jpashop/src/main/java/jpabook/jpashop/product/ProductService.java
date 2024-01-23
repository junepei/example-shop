package jpabook.jpashop.product;

import jpabook.jpashop.brand.Brand;
import jpabook.jpashop.brand.BrandRepository;
import jpabook.jpashop.common.JpaShopErrorMessage;
import jpabook.jpashop.common.event.AddProductEvent;
import jpabook.jpashop.common.event.ModifyProductEvent;
import jpabook.jpashop.common.event.RemoveProductEvent;
import jpabook.jpashop.common.exception.BadRequestException;
import jpabook.jpashop.styleset.StyleSetType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void removeProduct(long productNo) {
        productRepository.deleteById(productNo);
        eventPublisher.publishEvent(new RemoveProductEvent(productNo));
    }

    @Transactional
    public void modifyProduct(long productNo, BigDecimal price, StyleSetType styleSetType) {
        Optional<Product> optional = productRepository.findById(productNo);
        Product product = optional.orElseThrow(
                () -> new BadRequestException(JpaShopErrorMessage.PRODUCT_NOT_EXIST_PRODUCT));
        product.setPrice(price);

        productRepository.save(product);
        if (styleSetType != null) {
            eventPublisher.publishEvent(new ModifyProductEvent(productNo, styleSetType));
        }
    }

    @Transactional
    public long addProduct(long categoryNo, long brandNo, BigDecimal price, StyleSetType styleSetType) {
        Product product = new Product();
        Brand brand = brandRepository.findById(brandNo).orElseThrow(() ->
            new BadRequestException(JpaShopErrorMessage.BRAND_NOT_EXIST_BRAND)
        );
        product.setBrandNo(brandNo);
        product.setBrandName(brand.getBrandName());

        Optional<Category> categoryOptional = categoryRepository.findById(categoryNo);
        categoryOptional.ifPresent(category -> product.getCategories().add(category));
        product.setPrice(price);

        productRepository.save(product);
        if (styleSetType != null) {
            eventPublisher.publishEvent(new AddProductEvent(product.getProductNo(), styleSetType));
        }

        return product.getProductNo();
    }
}
