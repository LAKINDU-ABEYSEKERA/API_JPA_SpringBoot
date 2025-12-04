package edu.icet.ecom.controller;

import edu.icet.ecom.model.dto.CustomerDTO;
import edu.icet.ecom.model.entity.Customer;
import edu.icet.ecom.repository.CustomerRepository;
import edu.icet.ecom.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/customer")
@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/addCustomer")
    public String addCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.addCustomer(customerDTO);

    }

    @GetMapping("/searchCustomer={id}")
    public CustomerDTO searchCustomer(@PathVariable("id") String id){
        return customerService.searchCustomer(id);
    }

    @GetMapping("getAll")
    public List<CustomerDTO> getAll(){
        return customerService.getAll();
    }

    @PostMapping("/updateCustomer={id}")
    public String updateCustomer(@RequestBody CustomerDTO customerDTO ,@PathVariable("id") String id){
        return customerService.updateCustomer(customerDTO , id);
    }

    @DeleteMapping("/deleteCustomer={id}")
    public String removeCustomer(@PathVariable("id") String id){
        return customerService.removeCustomer(id);
    }

}
