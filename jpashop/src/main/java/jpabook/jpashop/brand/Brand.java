package jpabook.jpashop.brand;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue
    private long brandNo;
    @NotNull
    private String brandName;

    public static Brand createForTest(String brandName) {
        Brand brand = new Brand();
        brand.setBrandName(brandName);
        return brand;
    }
}
