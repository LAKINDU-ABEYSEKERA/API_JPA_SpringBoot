package edu.icet.ecom.service;

import edu.icet.ecom.model.dto.ProductDTO;
import edu.icet.ecom.model.entity.Product;
import edu.icet.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                productDTO.getPrice(),
                productDTO.getQty()
        );

        productRepository.save(product);

        return "Customer Added Successfully";
    }

    public ProductDTO searchProduct(String id) {
        Product product = productRepository.findById(id).orElse(null);

        return new ProductDTO(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getQty()
        );
    }

    public String updateProduct(ProductDTO productDTO, String id) {
        Product product = productRepository.findById(id).orElse(null);

        product.setProductId(id);
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());

        productRepository.save(product);

        return "Product Updated";

    }

    public String deleteProduct(String id) {
        productRepository.deleteById(id);

        if (productRepository.findById(id).isPresent()) {
            return "deleting failed";
        }

        return "Delete Product Successful";
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }
}
