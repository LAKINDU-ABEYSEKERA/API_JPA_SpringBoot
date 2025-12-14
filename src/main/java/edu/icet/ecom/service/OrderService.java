package edu.icet.ecom.service;

import edu.icet.ecom.model.dto.OrderDTO;
import edu.icet.ecom.model.dto.OrderProductsDTO;
import edu.icet.ecom.model.entity.Customer;
import edu.icet.ecom.model.entity.OrderProduct;
import edu.icet.ecom.model.entity.Orders;
import edu.icet.ecom.model.entity.Product;
import edu.icet.ecom.repository.CustomerRepository;
import edu.icet.ecom.repository.OrderDetailsRepository;
import edu.icet.ecom.repository.OrderRepository;
import edu.icet.ecom.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    // Logic to generate Order ID (O001, O002...)
    private String genOrderId() {
        Orders lastOrder = orderRepository.findTopByOrderByOrderIdDesc();
        if (lastOrder == null) {
            return "O001";
        }
        int num = Integer.parseInt(lastOrder.getOrderId().substring(1));
        num++;
        return String.format("O%03d", num);
    }

    public void placeOrder(OrderDTO orderDTO) {
        // 1. Create and Save the Order Parent first
        String orderId = genOrderId();
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setOrderDate(orderDTO.getLocalDate());

        // Fetch Customer
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        orders.setCustomer(customer);

        // Save Order to DB (so we have a valid Order entity to link to)
        orderRepository.save(orders);

        // 2. Loop through products and create OrderDetails
        List<OrderProductsDTO> productList = orderDTO.getOrderProductsDTOS();

        for (OrderProductsDTO detailDTO : productList) {
            Product product = productRepository.findById(detailDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderProduct orderDetails = new OrderProduct();

            // Set simple fields
            orderDetails.setProductName(product.getName());
            orderDetails.setQuantity(detailDTO.getQty());
            orderDetails.setPrice(detailDTO.getUnitPrice());

            // --- CRITICAL STEP: LINK THE CHILD TO THE PARENT ---
            orderDetails.setOrders(orders);

            // Save the detail
            orderDetailsRepository.save(orderDetails);

            // 3. Update Product Quantity
            int newQty = product.getQty() - detailDTO.getQty();
            product.setQty(newQty);
            productRepository.save(product);
        }
    }
}