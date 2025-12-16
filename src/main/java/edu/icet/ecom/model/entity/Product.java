package edu.icet.ecom.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder

@Entity
public class Product {

    @Id
    @Column(name = "product_id")
    private String productId;
    private String name;
    private int price;
    private int qty;

    @OneToMany(mappedBy = "product")
    private List<OrderDetails> orderDetails ;
}
