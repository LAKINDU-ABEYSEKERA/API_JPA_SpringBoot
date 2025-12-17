package edu.icet.ecom.service;

import edu.icet.ecom.model.dto.OrderDTO;
import edu.icet.ecom.model.dto.OrderDetailsDTO;
import edu.icet.ecom.model.entity.Customer;
import edu.icet.ecom.model.entity.OrderDetails;
import edu.icet.ecom.model.entity.Orders;
import edu.icet.ecom.model.entity.Product;
import edu.icet.ecom.repository.CustomerRepository;
import edu.icet.ecom.repository.OrderDetailsRepository;
import edu.icet.ecom.repository.OrderRepository;
import edu.icet.ecom.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
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
        List<OrderDetailsDTO> productList = orderDTO.getOrderDetailsDTOS();

        for (OrderDetailsDTO detailDTO : productList) {
            Product product = productRepository.findById(detailDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderDetails orderDetails = new OrderDetails();

            // Set simple fields
            orderDetails.setProduct(product);
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

    @GetMapping("/searchOrder/{id}")
    public OrderDTO searchOrder(@PathVariable("id")String id) {

        Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found!.."));

        List<OrderDetails> orderDetails = order.getOrderDetailsList();

        List<OrderDetailsDTO> orderDetailsDTOs = new ArrayList<>();

        for(OrderDetails od : orderDetails ){
            orderDetailsDTOs.add(
                    new OrderDetailsDTO(
                            od.getOrderDetailId(),
                            od.getProduct().getProductId(),
                            od.getQuantity(),
                            od.getPrice()
                    )
            );
        }

        double total = 0.00;
        for (OrderDetails od : orderDetails){
            total += (od.getQuantity() * od.getPrice());

        }

        return new OrderDTO(
                order.getOrderId(),
                order.getCustomer().getCustomerId(),
                order.getOrderDate(),
                total,
                orderDetailsDTOs
        );
    }

    public List<OrderDTO> getAllOrders() {
        List<Orders> orderArray = orderRepository.findAll();

        List<OrderDTO> orderDTOArray = new ArrayList<>();

        for(Orders od : orderArray){

            List<OrderDetails> orderDetail = od.getOrderDetailsList();

            List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();

            double total = 0;
            for(OrderDetails detail : orderDetail){
                total += (detail.getQuantity() * detail.getPrice());
                orderDetailsDTOList.add(
                        new OrderDetailsDTO(
                                detail.getOrderDetailId(),
                                detail.getProduct().getProductId(),
                                detail.getQuantity(),
                                detail.getPrice()
                        )
                );
            }

            orderDTOArray.add(
                    new OrderDTO(
                            od.getOrderId(),
                            od.getCustomer().getCustomerId(),
                            od.getOrderDate(),
                            total,
                            orderDetailsDTOList

              )
            );

        }

        return orderDTOArray;
    }
}