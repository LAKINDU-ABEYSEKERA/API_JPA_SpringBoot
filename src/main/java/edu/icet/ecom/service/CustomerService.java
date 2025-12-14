package edu.icet.ecom.service;

import edu.icet.ecom.model.dto.CustomerDTO;
import edu.icet.ecom.model.entity.Customer;
import edu.icet.ecom.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public String addCustomer(CustomerDTO customerDTO) {

        List<Customer> customerArray = customerRepository.findAll();
        int nextId = 1;

        for (Customer array : customerArray){
            nextId++;
        }

        Customer customer = Customer.builder()
                .customerId(String.valueOf(nextId))
                .name(customerDTO.getName())
                .contact(customerDTO.getContact())
                .address(customerDTO.getAddress())
                .build();

        customerRepository.save(customer);

        return "Customer Added Successfully";
    }

    public CustomerDTO searchCustomer(String id) {
        Customer customer = customerRepository.findById(id).orElse(null);

        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getName(),
                customer.getAddress(),
                customer.getContact()
        );
    }

    public String updateCustomer(CustomerDTO customerDTO, String id) {

        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            return null;
        }

        customer.setCustomerId(id);
        customer.setName(customerDTO.getName());
        customer.setContact(customerDTO.getContact());
        customer.setAddress(customerDTO.getAddress());

        customerRepository.save(
                customer);

        return "Customer Successfully Updated";
    }

    public String removeCustomer(String id) {

        customerRepository.deleteById(id);
        if (customerRepository.existsById(id)){
            return "Customer Was not Deleted";
        } else {
            return "Customer Deleted Successfully";
        }
    }

    public List<CustomerDTO> getAll() {
        List<Customer> cusArray = customerRepository.findAll();

        List<CustomerDTO> cusDTO = new ArrayList<>();

        for (Customer entityArray : cusArray){
            cusDTO.add(
                    new CustomerDTO(
                            entityArray.getCustomerId(),
                            entityArray.getName(),
                            entityArray.getAddress(),
                            entityArray.getContact()
                    )
            );
        }

        return cusDTO;
    }
}
