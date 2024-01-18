package jpabook.jpashop.styleset;


import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
public class StyleSet {
    @Id
    @GeneratedValue
    private long styleSetNo;
    @Enumerated(EnumType.STRING)
    private StyleSetType styleSetType;
    private long brandNo;
    private long productNo;
    private BigDecimal price;
}
