package jpabook.jpashop.Brand;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Brand {
    @Id
    @GeneratedValue
    private long brandNo;
    private String name;
}
