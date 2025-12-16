package edu.icet.ecom.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailsDTO {
    private String productId;
    private int qty;
    private double unitPrice;
}
