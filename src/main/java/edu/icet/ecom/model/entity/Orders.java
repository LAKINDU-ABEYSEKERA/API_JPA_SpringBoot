package edu.icet.ecom.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Orders {
    @Id
    @Column(name = "orders_id")
    private String orderId;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private LocalDate orderDate;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<OrderDetails> orderDetailsList = new ArrayList<>();

    public void addOrderDetail(OrderDetails detail){
        orderDetailsList.add(detail);
        detail.setOrders(this);

    }


}
