package jpabook.jpashop.styleset;

import jpabook.jpashop.common.event.AddProductEvent;
import jpabook.jpashop.common.event.ModifyProductEvent;
import jpabook.jpashop.common.event.RemoveBrandEvent;
import jpabook.jpashop.common.event.RemoveProductEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StyleSetEventListener {
    private final StyleSetAggregateService styleSetAggregateService;


    @EventListener
    public void removeBrand(RemoveBrandEvent removeBrandEvent){
        styleSetAggregateService.removeBrand(removeBrandEvent.getBrandNo());
    }

    @EventListener
    public void removeProduct(RemoveProductEvent removeProductEvent){
        styleSetAggregateService.removeProduct(removeProductEvent.getProductNo());
    }

    @EventListener
    public void addProduct(AddProductEvent addProductEvent){
        styleSetAggregateService.addProduct(addProductEvent.getProductNo(), addProductEvent.getStyleSetType());
    }

    @EventListener
    public void modifyProduct(ModifyProductEvent modifyProductEvent){
        styleSetAggregateService.modifyProduct(modifyProductEvent.getProductNo(), modifyProductEvent.getStyleSetType());
    }
}
