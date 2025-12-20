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

        // Took Customer into a variable
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        orders.setCustomer(customer);




        // Loop through products and create OrderDetails
        List<OrderDetailsDTO> productList = orderDTO.getOrderDetailsDTOS();

        for (OrderDetailsDTO detailDTO : productList) {
            Product product = productRepository.findById(detailDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

          OrderDetails orderDetails = new OrderDetails();

            // Set simple fields
            orderDetails.setProduct(product);
            orderDetails.setQuantity(detailDTO.getQty());
            orderDetails.setPrice(detailDTO.getUnitPrice());

            // Linking the Child To The Parent
            orders.addOrderDetail(orderDetails);


            // 3. Update Product Quantity
            int newQty = product.getQty() - detailDTO.getQty();
            product.setQty(newQty);
            productRepository.save(product);
        }

        orderRepository.save(orders);
    }

    public OrderDTO searchOrder(String id) {

        Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found!.."));

        List<OrderDetails> orderDetails = order.getOrderDetailsList();

        List<OrderDetailsDTO> orderDetailsDTOs = new ArrayList<>();

        double total = 0.00;
        for(OrderDetails od : orderDetails ){
            orderDetailsDTOs.add(
                    new OrderDetailsDTO(
                            od.getOrderDetailId(),
                            od.getProduct().getProductId(),
                            od.getQuantity(),
                            od.getPrice()
                    )
            );

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

    public OrderDTO updateOrder(OrderDTO orderDTO) {
        Orders order = orderRepository.findById(orderDTO.getOrderId()).orElseThrow(
                () -> new RuntimeException("Order not Found")
        );

        Customer customer = customerRepository.findById(orderDTO.getCustomerId()).orElseThrow(
                () -> new RuntimeException("Customer not Found")
        );

        order.setCustomer(customer);
        order.setOrderDate(orderDTO.getLocalDate());

        order.getOrderDetailsList().clear();


        List<OrderDetailsDTO> orderDetailsDTOList = orderDTO.getOrderDetailsDTOS();

        double total = 0;

        for (OrderDetailsDTO od : orderDetailsDTOList) {

            Product product = productRepository.findById(od.getProductId()).orElseThrow(
                    () -> new RuntimeException("Product not Found")
            );

            OrderDetails orderDetail = new OrderDetails();

            orderDetail.setProduct(product);
            orderDetail.setPrice(od.getUnitPrice());
            orderDetail.setQuantity(od.getQty());

            order.addOrderDetail(orderDetail);

            total += od.getQty() * od.getUnitPrice();
        }
        return new OrderDTO(
                order.getOrderId(),
                order.getCustomer().getCustomerId(),
                order.getOrderDate(),
                total,
                orderDetailsDTOList
        );
    }
}