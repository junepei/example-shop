package jpabook.jpashop.common.event;

import lombok.Getter;

@Getter
public class RemoveProductEvent {
    private final long productNo;

    public RemoveProductEvent(long productNo) {

        this.productNo = productNo;
    }
}
