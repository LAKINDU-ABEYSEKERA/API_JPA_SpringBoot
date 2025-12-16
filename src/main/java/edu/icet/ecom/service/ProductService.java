package edu.icet.ecom.service;

import edu.icet.ecom.model.dto.ProductDTO;
import edu.icet.ecom.model.dto.ProductSalesDTO;
import edu.icet.ecom.model.entity.OrderDetails;
import edu.icet.ecom.model.entity.Product;
import edu.icet.ecom.repository.ProductRepository;
import lombok.Builder;
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

        Product product = Product.builder()
                        .productId(String.valueOf(nextId))
                        .name(productDTO.getName())
                        .price(productDTO.getPrice())
                        .qty(productDTO.getQty())
                        .build();

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

    public List<ProductSalesDTO> getProductHistory(String productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        List<OrderDetails> orderDetailsArray = product.getOrderDetails();

        List<ProductSalesDTO> productSalesDTOS = new ArrayList<>();

        for (OrderDetails orderDetails : orderDetailsArray) {
            productSalesDTOS.add(
                    new ProductSalesDTO(
                            orderDetails.getOrders().getOrderId(),
                            orderDetails.getOrders().getOrderDate(),
                            orderDetails.getPrice(),
                            orderDetails.getQuantity()
                    )
            );

        }

        return productSalesDTOS;

    }

}
