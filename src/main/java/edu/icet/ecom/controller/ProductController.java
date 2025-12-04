package edu.icet.ecom.controller;

import edu.icet.ecom.model.dto.ProductDTO;
import edu.icet.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/addProduct")
    public String addProduct(@RequestBody ProductDTO productDTO){
       return productService.addProduct(productDTO);
    }

    @GetMapping("searchProduct={id}")
    public ProductDTO searchProduct(@PathVariable("id")String id){
        return productService.searchProduct(id);
    }

    @PostMapping("updateProduct={id}")
    public String updateProduct(@RequestBody ProductDTO productDTO,@PathVariable("id")String id){
        return productService.updateProduct(productDTO,id);
    }
}
