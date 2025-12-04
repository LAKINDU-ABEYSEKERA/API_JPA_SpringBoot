package edu.icet.ecom.service;

import edu.icet.ecom.model.dto.ProductDTO;
import edu.icet.ecom.model.entity.Product;
import edu.icet.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public String addProduct(ProductDTO productDTO) {

        Product product = new Product(
                productDTO.getProductId(),
                productDTO.getName(),
                productDTO.getPrice()

        );

        productRepository.save(product);

        return "Customer Added Successfully";
    }
}
