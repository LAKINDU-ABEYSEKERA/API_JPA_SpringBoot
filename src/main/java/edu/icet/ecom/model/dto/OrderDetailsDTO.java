package edu.icet.ecom.model.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailsDTO {
    private Long orderDetailId;
    private String productId;
    private Integer qty;
    private double unitPrice;
}
