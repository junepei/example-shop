package jpabook.jpashop.common.event;

import lombok.Getter;

@Getter
public class RemoveBrandEvent {
    private final long brandNo;

    public RemoveBrandEvent(long brandNo) {

        this.brandNo = brandNo;
    }
}
