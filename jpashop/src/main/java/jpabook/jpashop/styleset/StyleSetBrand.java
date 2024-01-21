package jpabook.jpashop.styleset;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class StyleSetBrand {
    @Id
    @GeneratedValue
    private long styleSetBrandNo;
    @NotNull
    private long brandNo;
    @NotNull
    private String brandName;

    private BigDecimal totalPrice;
}
