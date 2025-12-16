package edu.icet.ecom.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class OrderDTO {
    private String orderId;
    private String orderDetailsId;
    private String customerId;
    private LocalDate localDate;
    private double totalPrice;
    private List<OrderDetailsDTO> orderDetailsDTOS;
}
