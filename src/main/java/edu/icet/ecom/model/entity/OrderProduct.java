package edu.icet.ecom.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    private String productName;
    private int quantity;
    private double price;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;
}