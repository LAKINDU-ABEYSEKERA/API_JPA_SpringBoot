package edu.icet.ecom.service;

import edu.icet.ecom.model.dto.ProductDTO;
import edu.icet.ecom.model.entity.Product;
import edu.icet.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;



    public String addProduct(ProductDTO productDTO) {

        List<Product> productArray;
        productArray = productRepository.findAll();

        int nextId = 1;

        for (Product array : productArray){
            nextId++;
        }

        Product product = new Product(
                String.valueOf(nextId),
                productDTO.getName(),
                productDTO.getPrice()

        );

        productRepository.save(product);

        return "Customer Added Successfully";
    }
}
