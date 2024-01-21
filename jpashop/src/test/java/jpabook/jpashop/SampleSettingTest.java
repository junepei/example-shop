package jpabook.jpashop;

import jpabook.jpashop.Brand.Brand;
import jpabook.jpashop.Brand.BrandRepository;
import jpabook.jpashop.common.QueryDslConfig;
import jpabook.jpashop.product.Category;
import jpabook.jpashop.product.CategoryRepository;
import jpabook.jpashop.product.Product;
import jpabook.jpashop.product.ProductRepository;
import jpabook.jpashop.styleset.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;


import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Transactional
@Import(QueryDslConfig.class)
@SpringBootTest
public class SampleSettingTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StyleSetProductRepository styleSetProductRepository;

    @Autowired
    private StyleSetBrandRepository styleSetBrandRepository;

    @Rollback(false)
    @Test
    public void setting() {
        Brand brandA = Brand.createForTest("A");
        Brand brandB = Brand.createForTest("B");
        Brand brandC = Brand.createForTest("C");
        Brand brandD = Brand.createForTest("D");
        Brand brandE = Brand.createForTest("E");
        Brand brandF = Brand.createForTest("F");
        Brand brandG = Brand.createForTest("G");
        Brand brandH = Brand.createForTest("H");
        Brand brandI = Brand.createForTest("I");

        brandRepository.save(brandA);
        brandRepository.save(brandB);
        brandRepository.save(brandC);
        brandRepository.save(brandD);
        brandRepository.save(brandE);
        brandRepository.save(brandF);
        brandRepository.save(brandG);
        brandRepository.save(brandH);
        brandRepository.save(brandI);

        Category category = Category.createForTest();
        categoryRepository.save(category);

        setProductBrandA(brandA, category);
        setProductBrandB(brandB, category);
        setProductBrandC(brandC, category);
        setProductBrandD(brandD, category);
        setProductBrandE(brandE, category);
        setProductBrandF(brandF, category);
        setProductBrandG(brandG, category);
        setProductBrandH(brandH, category);
        setProductBrandI(brandI, category);
    }

    private void setProductBrandA(Brand brand, Category category) {
        Product product1 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(11200), category);
        Product product2 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(5500), category);
        Product product3 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(4200), category);
        Product product4 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(9000), category);
        Product product5 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2000), category);
        Product product6 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(1700), category);
        Product product7 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(1800), category);
        Product product8 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2300), category);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);

        StyleSetProduct styleSetProduct1 = StyleSetProduct.of(product1, StyleSetType.TOP, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct2 = StyleSetProduct.of(product2, StyleSetType.OUTER, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct3 = StyleSetProduct.of(product3, StyleSetType.PANTS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct4 = StyleSetProduct.of(product4, StyleSetType.SNEAKERS, StyleSetPriceTag.LOWEST_PRICE);
        StyleSetProduct styleSetProduct5 = StyleSetProduct.of(product5, StyleSetType.BAG, StyleSetPriceTag.LOWEST_PRICE);
        StyleSetProduct styleSetProduct6 = StyleSetProduct.of(product6, StyleSetType.HAT, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct7 = StyleSetProduct.of(product7, StyleSetType.SOCKS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct8 = StyleSetProduct.of(product8, StyleSetType.ACCESSORIES, StyleSetPriceTag.BRAND_LOWEST_PRICE);

        styleSetProductRepository.save(styleSetProduct1);
        styleSetProductRepository.save(styleSetProduct2);
        styleSetProductRepository.save(styleSetProduct3);
        styleSetProductRepository.save(styleSetProduct4);
        styleSetProductRepository.save(styleSetProduct5);
        styleSetProductRepository.save(styleSetProduct6);
        styleSetProductRepository.save(styleSetProduct7);
        styleSetProductRepository.save(styleSetProduct8);

        setStyleSetBrand(brand, Lists.newArrayList(product1, product2, product3, product4,
                product5, product6, product7, product8));
    }

    private void setProductBrandB(Brand brand, Category category) {
        Product product1 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(10500), category);
        Product product2 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(5900), category);
        Product product3 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(3800), category);
        Product product4 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(9100), category);
        Product product5 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2100), category);
        Product product6 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2000), category);
        Product product7 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2000), category);
        Product product8 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2200), category);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);

        StyleSetProduct styleSetProduct1 = StyleSetProduct.of(product1, StyleSetType.TOP, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct2 = StyleSetProduct.of(product2, StyleSetType.OUTER, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct3 = StyleSetProduct.of(product3, StyleSetType.PANTS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct4 = StyleSetProduct.of(product4, StyleSetType.SNEAKERS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct5 = StyleSetProduct.of(product5, StyleSetType.BAG, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct6 = StyleSetProduct.of(product6, StyleSetType.HAT, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct7 = StyleSetProduct.of(product7, StyleSetType.SOCKS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct8 = StyleSetProduct.of(product8, StyleSetType.ACCESSORIES, StyleSetPriceTag.BRAND_LOWEST_PRICE);

        styleSetProductRepository.save(styleSetProduct1);
        styleSetProductRepository.save(styleSetProduct2);
        styleSetProductRepository.save(styleSetProduct3);
        styleSetProductRepository.save(styleSetProduct4);
        styleSetProductRepository.save(styleSetProduct5);
        styleSetProductRepository.save(styleSetProduct6);
        styleSetProductRepository.save(styleSetProduct7);
        styleSetProductRepository.save(styleSetProduct8);

        setStyleSetBrand(brand, Lists.newArrayList(product1, product2, product3, product4,
                product5, product6, product7, product8));
    }

    private void setProductBrandC(Brand brand, Category category) {
        Product product1 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(10000), category);
        Product product2 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(6200), category);
        Product product3 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(3300), category);
        Product product4 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(9200), category);
        Product product5 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2200), category);
        Product product6 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(1900), category);
        Product product7 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2200), category);
        Product product8 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2100), category);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);

        StyleSetProduct styleSetProduct1 = StyleSetProduct.of(product1, StyleSetType.TOP, StyleSetPriceTag.LOWEST_PRICE);
        StyleSetProduct styleSetProduct2 = StyleSetProduct.of(product2, StyleSetType.OUTER, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct3 = StyleSetProduct.of(product3, StyleSetType.PANTS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct4 = StyleSetProduct.of(product4, StyleSetType.SNEAKERS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct5 = StyleSetProduct.of(product5, StyleSetType.BAG, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct6 = StyleSetProduct.of(product6, StyleSetType.HAT, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct7 = StyleSetProduct.of(product7, StyleSetType.SOCKS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct8 = StyleSetProduct.of(product8, StyleSetType.ACCESSORIES, StyleSetPriceTag.BRAND_LOWEST_PRICE);

        styleSetProductRepository.save(styleSetProduct1);
        styleSetProductRepository.save(styleSetProduct2);
        styleSetProductRepository.save(styleSetProduct3);
        styleSetProductRepository.save(styleSetProduct4);
        styleSetProductRepository.save(styleSetProduct5);
        styleSetProductRepository.save(styleSetProduct6);
        styleSetProductRepository.save(styleSetProduct7);
        styleSetProductRepository.save(styleSetProduct8);

        setStyleSetBrand(brand, Lists.newArrayList(product1, product2, product3, product4,
                product5, product6, product7, product8));
    }

    private void setProductBrandD(Brand brand, Category category) {
        Product product1 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(10100), category);
        Product product2 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(5100), category);
        Product product3 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(3000), category);
        Product product4 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(9500), category);
        Product product5 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2500), category);
        Product product6 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(1500), category);
        Product product7 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2400), category);
        Product product8 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2000), category);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);

        StyleSetProduct styleSetProduct1 = StyleSetProduct.of(product1, StyleSetType.TOP, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct2 = StyleSetProduct.of(product2, StyleSetType.OUTER, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct3 = StyleSetProduct.of(product3, StyleSetType.PANTS, StyleSetPriceTag.LOWEST_PRICE);
        StyleSetProduct styleSetProduct4 = StyleSetProduct.of(product4, StyleSetType.SNEAKERS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct5 = StyleSetProduct.of(product5, StyleSetType.BAG, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct6 = StyleSetProduct.of(product6, StyleSetType.HAT, StyleSetPriceTag.LOWEST_PRICE);
        StyleSetProduct styleSetProduct7 = StyleSetProduct.of(product7, StyleSetType.SOCKS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct8 = StyleSetProduct.of(product8, StyleSetType.ACCESSORIES, StyleSetPriceTag.BRAND_LOWEST_PRICE);

        styleSetProductRepository.save(styleSetProduct1);
        styleSetProductRepository.save(styleSetProduct2);
        styleSetProductRepository.save(styleSetProduct3);
        styleSetProductRepository.save(styleSetProduct4);
        styleSetProductRepository.save(styleSetProduct5);
        styleSetProductRepository.save(styleSetProduct6);
        styleSetProductRepository.save(styleSetProduct7);
        styleSetProductRepository.save(styleSetProduct8);

        setStyleSetBrand(brand, Lists.newArrayList(product1, product2, product3, product4,
                product5, product6, product7, product8));
    }

    private void setProductBrandE(Brand brand, Category category) {
        Product product1 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(10700), category);
        Product product2 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(5000), category);
        Product product3 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(3800), category);
        Product product4 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(9900), category);
        Product product5 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2300), category);
        Product product6 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(1800), category);
        Product product7 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2100), category);
        Product product8 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2100), category);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);

        StyleSetProduct styleSetProduct1 = StyleSetProduct.of(product1, StyleSetType.TOP, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct2 = StyleSetProduct.of(product2, StyleSetType.OUTER, StyleSetPriceTag.LOWEST_PRICE);
        StyleSetProduct styleSetProduct3 = StyleSetProduct.of(product3, StyleSetType.PANTS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct4 = StyleSetProduct.of(product4, StyleSetType.SNEAKERS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct5 = StyleSetProduct.of(product5, StyleSetType.BAG, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct6 = StyleSetProduct.of(product6, StyleSetType.HAT, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct7 = StyleSetProduct.of(product7, StyleSetType.SOCKS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct8 = StyleSetProduct.of(product8, StyleSetType.ACCESSORIES, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        styleSetProductRepository.save(styleSetProduct1);
        styleSetProductRepository.save(styleSetProduct2);
        styleSetProductRepository.save(styleSetProduct3);
        styleSetProductRepository.save(styleSetProduct4);
        styleSetProductRepository.save(styleSetProduct5);
        styleSetProductRepository.save(styleSetProduct6);
        styleSetProductRepository.save(styleSetProduct7);
        styleSetProductRepository.save(styleSetProduct8);

        setStyleSetBrand(brand, Lists.newArrayList(product1, product2, product3, product4,
                product5, product6, product7, product8));
    }

    private void setProductBrandF(Brand brand, Category category) {
        Product product1 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(11200), category);
        Product product2 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(7200), category);
        Product product3 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(4000), category);
        Product product4 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(9300), category);
        Product product5 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2100), category);
        Product product6 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(1600), category);
        Product product7 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2300), category);
        Product product8 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(1900), category);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);

        StyleSetProduct styleSetProduct1 = StyleSetProduct.of(product1, StyleSetType.TOP, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct2 = StyleSetProduct.of(product2, StyleSetType.OUTER, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct3 = StyleSetProduct.of(product3, StyleSetType.PANTS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct4 = StyleSetProduct.of(product4, StyleSetType.SNEAKERS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct5 = StyleSetProduct.of(product5, StyleSetType.BAG, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct6 = StyleSetProduct.of(product6, StyleSetType.HAT, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct7 = StyleSetProduct.of(product7, StyleSetType.SOCKS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct8 = StyleSetProduct.of(product8, StyleSetType.ACCESSORIES, StyleSetPriceTag.LOWEST_PRICE);

        styleSetProductRepository.save(styleSetProduct1);
        styleSetProductRepository.save(styleSetProduct2);
        styleSetProductRepository.save(styleSetProduct3);
        styleSetProductRepository.save(styleSetProduct4);
        styleSetProductRepository.save(styleSetProduct5);
        styleSetProductRepository.save(styleSetProduct6);
        styleSetProductRepository.save(styleSetProduct7);
        styleSetProductRepository.save(styleSetProduct8);

        setStyleSetBrand(brand, Lists.newArrayList(product1, product2, product3, product4,
                product5, product6, product7, product8));
    }

    private void setProductBrandG(Brand brand, Category category) {
        Product product1 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(10500), category);
        Product product2 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(5800), category);
        Product product3 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(3900), category);
        Product product4 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(9000), category);
        Product product5 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2200), category);
        Product product6 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(1700), category);
        Product product7 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2100), category);
        Product product8 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2000), category);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);

        StyleSetProduct styleSetProduct1 = StyleSetProduct.of(product1, StyleSetType.TOP, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct2 = StyleSetProduct.of(product2, StyleSetType.OUTER, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct3 = StyleSetProduct.of(product3, StyleSetType.PANTS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct4 = StyleSetProduct.of(product4, StyleSetType.SNEAKERS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct5 = StyleSetProduct.of(product5, StyleSetType.BAG, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct6 = StyleSetProduct.of(product6, StyleSetType.HAT, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct7 = StyleSetProduct.of(product7, StyleSetType.SOCKS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct8 = StyleSetProduct.of(product8, StyleSetType.ACCESSORIES, StyleSetPriceTag.BRAND_LOWEST_PRICE);

        styleSetProductRepository.save(styleSetProduct1);
        styleSetProductRepository.save(styleSetProduct2);
        styleSetProductRepository.save(styleSetProduct3);
        styleSetProductRepository.save(styleSetProduct4);
        styleSetProductRepository.save(styleSetProduct5);
        styleSetProductRepository.save(styleSetProduct6);
        styleSetProductRepository.save(styleSetProduct7);
        styleSetProductRepository.save(styleSetProduct8);

        setStyleSetBrand(brand, Lists.newArrayList(product1, product2, product3, product4,
                product5, product6, product7, product8));
    }

    private void setProductBrandH(Brand brand, Category category) {
        Product product1 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(10800), category);
        Product product2 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(6300), category);
        Product product3 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(3100), category);
        Product product4 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(9700), category);
        Product product5 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2100), category);
        Product product6 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(1600), category);
        Product product7 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2000), category);
        Product product8 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2000), category);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);

        StyleSetProduct styleSetProduct1 = StyleSetProduct.of(product1, StyleSetType.TOP, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct2 = StyleSetProduct.of(product2, StyleSetType.OUTER, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct3 = StyleSetProduct.of(product3, StyleSetType.PANTS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct4 = StyleSetProduct.of(product4, StyleSetType.SNEAKERS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct5 = StyleSetProduct.of(product5, StyleSetType.BAG, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct6 = StyleSetProduct.of(product6, StyleSetType.HAT, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct7 = StyleSetProduct.of(product7, StyleSetType.SOCKS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct8 = StyleSetProduct.of(product8, StyleSetType.ACCESSORIES, StyleSetPriceTag.BRAND_LOWEST_PRICE);

        styleSetProductRepository.save(styleSetProduct1);
        styleSetProductRepository.save(styleSetProduct2);
        styleSetProductRepository.save(styleSetProduct3);
        styleSetProductRepository.save(styleSetProduct4);
        styleSetProductRepository.save(styleSetProduct5);
        styleSetProductRepository.save(styleSetProduct6);
        styleSetProductRepository.save(styleSetProduct7);
        styleSetProductRepository.save(styleSetProduct8);

        setStyleSetBrand(brand, Lists.newArrayList(product1, product2, product3, product4,
                product5, product6, product7, product8));
    }

    private void setProductBrandI(Brand brand, Category category) {
        Product product1 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(11400), category);
        Product product2 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(6700), category);
        Product product3 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(3200), category);
        Product product4 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(9500), category);
        Product product5 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2400), category);
        Product product6 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(1700), category);
        Product product7 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(1700), category);
        Product product8 = Product.createForTest(brand.getBrandNo(), brand.getBrandName(), BigDecimal.valueOf(2400), category);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);

        StyleSetProduct styleSetProduct1 = StyleSetProduct.of(product1, StyleSetType.TOP, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct2 = StyleSetProduct.of(product2, StyleSetType.OUTER, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct3 = StyleSetProduct.of(product3, StyleSetType.PANTS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct4 = StyleSetProduct.of(product4, StyleSetType.SNEAKERS, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct5 = StyleSetProduct.of(product5, StyleSetType.BAG, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct6 = StyleSetProduct.of(product6, StyleSetType.HAT, StyleSetPriceTag.BRAND_LOWEST_PRICE);
        StyleSetProduct styleSetProduct7 = StyleSetProduct.of(product7, StyleSetType.SOCKS, StyleSetPriceTag.LOWEST_PRICE);
        StyleSetProduct styleSetProduct8 = StyleSetProduct.of(product8, StyleSetType.ACCESSORIES, StyleSetPriceTag.BRAND_LOWEST_PRICE);

        styleSetProductRepository.save(styleSetProduct1);
        styleSetProductRepository.save(styleSetProduct2);
        styleSetProductRepository.save(styleSetProduct3);
        styleSetProductRepository.save(styleSetProduct4);
        styleSetProductRepository.save(styleSetProduct5);
        styleSetProductRepository.save(styleSetProduct6);
        styleSetProductRepository.save(styleSetProduct7);
        styleSetProductRepository.save(styleSetProduct8);

        setStyleSetBrand(brand, Lists.newArrayList(product1, product2, product3, product4,
                product5, product6, product7, product8));
    }


    private void setStyleSetBrand(Brand brand, List<Product> products) {
        StyleSetBrand styleSetBrand = new StyleSetBrand();
        styleSetBrand.setBrandNo(brand.getBrandNo());
        styleSetBrand.setBrandName(brand.getBrandName());
        styleSetBrand.setTotalPrice(new BigDecimal(products.stream().mapToInt(p -> p.getPrice().intValue()).sum()));
        styleSetBrandRepository.save(styleSetBrand);
    }

}
