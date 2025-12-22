package edu.icet.ecom.controller;

import edu.icet.ecom.model.dto.OrderDTO;
import edu.icet.ecom.service.OrderService;
import edu.icet.ecom.util.OrderIdUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestBody OrderDTO orderDTO){
        orderService.placeOrder(orderDTO);
        return "Order Placed Successfully";
    }

    @GetMapping("/searchOrder/{id}")
    public OrderDTO searchOrder(@PathVariable("id") String id){
        String formattedId = OrderIdUtil.formatId(id);

        return orderService.searchOrder(formattedId);
    }

    @GetMapping("/getAllOrders")
    public List<OrderDTO> getAllOrders(){
        return orderService.getAllOrders();

    }

    @PutMapping("/updateOrder")
    public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO){
        return orderService.updateOrder(orderDTO);
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deleteOrderId/{id}")
    public void deleteOrderDetail(@PathVariable("id")String id){
        orderService.deleteOrderDetail(id);

    }
}