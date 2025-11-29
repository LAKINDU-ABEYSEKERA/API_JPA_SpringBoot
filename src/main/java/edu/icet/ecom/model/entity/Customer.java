package edu.icet.ecom.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
public class Customer {

    @Id
    private String customerId;
    private String name;
    private String address;
    private String contact;

}
