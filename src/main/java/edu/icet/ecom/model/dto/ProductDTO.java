package edu.icet.ecom.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ProductDTO {
    private String productId;
    private String name;
    private int price;
}
