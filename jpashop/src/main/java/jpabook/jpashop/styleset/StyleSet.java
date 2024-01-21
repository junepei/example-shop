package jpabook.jpashop.styleset;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class StyleSet {
    @Id
    @GeneratedValue
    private long styleSetNo;
    @Enumerated(EnumType.STRING)
    private StyleSetType styleSetType;
    private long brandNo;
    private String brandName;
    private long productNo;
    private BigDecimal price;
}
