package jpabook.jpashop.product.request;

import jpabook.jpashop.styleset.StyleSetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ModifyProductRequest {
    @NotNull
    private BigDecimal price;
    private StyleSetType styleSetType; //nullable. styleSet에 등록하고 싶은 상품만 추가함.
}
