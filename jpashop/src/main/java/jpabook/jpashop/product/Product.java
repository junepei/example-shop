package jpabook.jpashop.product;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue
    private long productNo;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_no"),
            inverseJoinColumns = @JoinColumn(name = "category_no")
    )
    private List<Category> categories = new ArrayList<>();

    @NotNull
    private long brandNo;
    @NotNull
    private String brandName;
    @NotNull
    private BigDecimal price;

    public static Product createForTest(long brandNo, String brandName, BigDecimal price, Category category) {
        Product product = new Product();
        product.setBrandNo(brandNo);
        product.setBrandName(brandName);
        product.setPrice(price);
        product.addCategory(category);
        return product;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

}
