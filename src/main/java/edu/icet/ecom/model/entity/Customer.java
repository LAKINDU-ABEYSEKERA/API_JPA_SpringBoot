package edu.icet.ecom.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

@Entity
public class Customer {

    @Id
    private String customerId;
    private String name;
    private String address;
    private String contact;

    @OneToMany(mappedBy = "customer" ,cascade = CascadeType.ALL)
    private List<Orders> orders;

}
