package edu.icet.ecom.model.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter

public class ProductSalesDTO {
    private String productId;
    private LocalDate date;
    private double priceAtTimeOfSale;
    private int quantitySold;
}
