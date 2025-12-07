package edu.icet.ecom.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    private String productName;
    private int quantity;
    private double price;

    // --- THIS WAS MISSING ---
    @ManyToOne
    @JoinColumn(name = "order_id") // This creates the Foreign Key column in the database
    private Orders orders;         // This name matches the 'mappedBy' in Orders.java
}