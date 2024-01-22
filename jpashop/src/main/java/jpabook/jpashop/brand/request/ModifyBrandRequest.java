package jpabook.jpashop.brand.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Setter
@Getter
public class ModifyBrandRequest {
    @NotNull
    private String brandName;
}
