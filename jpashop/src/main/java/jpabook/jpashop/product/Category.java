package jpabook.jpashop.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    private long categoryNo;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parentCategoryNo")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children;

    public static Category createForTest() {
        Category category = new Category();
        category.setName("테스트 카테고리");
        return category;
    }
}
