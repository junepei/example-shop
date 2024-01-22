package jpabook.jpashop.common.event;

import jpabook.jpashop.styleset.StyleSetType;
import lombok.Getter;

@Getter
public class ModifyProductEvent {
    private final long productNo;
    private final StyleSetType styleSetType;

    public ModifyProductEvent(long productNo, StyleSetType styleSetType) {

        this.productNo = productNo;
        this.styleSetType = styleSetType;
    }
}
