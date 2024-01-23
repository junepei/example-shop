package jpabook.jpashop.brand;

import jpabook.jpashop.common.JpaShopErrorMessage;
import jpabook.jpashop.common.event.RemoveBrandEvent;
import jpabook.jpashop.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final ApplicationEventPublisher eventPublisher;

    public long addBrand(String brandName) {
        Optional<Brand> optional = brandRepository.findByBrandName(brandName);
        if (optional.isPresent()) {
            throw new BadRequestException(JpaShopErrorMessage.BRAND_USED_BRAND_NAME);
        }

        Brand brand = new Brand();
        brand.setBrandName(brandName);

        brandRepository.save(brand);
        return brand.getBrandNo();
    }

    public void modifyBrand(long brandNo, String brandName) {
        Optional<Brand> optional = brandRepository.findById(brandNo);

        if (optional.isEmpty()) {
            throw new BadRequestException(JpaShopErrorMessage.BRAND_NOT_EXIST_BRAND);
        }

        Optional<Brand> optionalByName = brandRepository.findByBrandName(brandName);
        if (optionalByName.isPresent() && optionalByName.get().getBrandNo() != brandNo) {
            throw new BadRequestException(JpaShopErrorMessage.BRAND_USED_BRAND_NAME);
        }

        Brand brand = optional.get();
        brand.setBrandName(brandName);

        brandRepository.save(brand);
    }

    public void removeBrand(long brandNo) {
        Optional<Brand> optional = brandRepository.findById(brandNo);

        if (optional.isEmpty()) {
            throw new BadRequestException(JpaShopErrorMessage.BRAND_NOT_EXIST_BRAND);
        }

        brandRepository.deleteById(brandNo);

        eventPublisher.publishEvent(new RemoveBrandEvent(brandNo));
    }

}
