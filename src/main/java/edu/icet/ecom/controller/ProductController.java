package edu.icet.ecom.controller;

import edu.icet.ecom.model.dto.ProductDTO;
import edu.icet.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/addProduct")
    public String addProduct(@RequestBody ProductDTO productDTO){
       return productService.addProduct(productDTO);
    }

}
