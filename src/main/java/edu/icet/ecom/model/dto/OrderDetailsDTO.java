package edu.icet.ecom.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailsDTO {
    private Long orderDetailId;
    private String productId;
    private int qty;
    private double unitPrice;
}
