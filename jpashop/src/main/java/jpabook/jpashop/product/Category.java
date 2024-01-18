package jpabook.jpashop.product;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category {
    @Id
    @GeneratedValue
    private long categoryNo;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_product",
            joinColumns = @JoinColumn(name = "category_no"),
            inverseJoinColumns = @JoinColumn(name = "product_no")
    )
    private List<Product> products = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "parentCategoryNo")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children;
}
