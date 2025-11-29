package edu.icet.ecom.service;

import edu.icet.ecom.model.dto.CustomerDTO;
import edu.icet.ecom.model.entity.Customer;
import edu.icet.ecom.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public String addCustomer(CustomerDTO customerDTO) {
        customerRepository.save(
                new Customer(
                        "C001",
                        customerDTO.getName(),
                        customerDTO.getAddress(),
                        customerDTO.getContact()
                ));

        return "Customer Added Successfully";
    }


}
