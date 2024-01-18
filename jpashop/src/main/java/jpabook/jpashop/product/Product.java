package jpabook.jpashop.product;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Product {
    @Id
    @GeneratedValue
    private long productNo;
    @ManyToMany(mappedBy = "products")
    private List<Category> categories = new ArrayList<>();
    private long brandNo;
    private BigDecimal price;
    private String name;
    private LocalDateTime displayStartDate;
    private LocalDateTime displayEndDate;
    private LocalDateTime saleStartDate;
    private LocalDateTime saleEndDate;
    private boolean saleSuspend;

}
