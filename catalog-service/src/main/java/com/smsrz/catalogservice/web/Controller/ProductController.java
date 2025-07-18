package com.smsrz.catalogservice.web.Controller;

import com.smsrz.catalogservice.domain.PagedResult;
import com.smsrz.catalogservice.domain.Product;
import com.smsrz.catalogservice.domain.ProductNotFoundException;
import com.smsrz.catalogservice.domain.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/products")
class ProductController {
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getproducts(@RequestParam(name = "page",defaultValue="1") int pageNo){
        return productService.getproducts(pageNo);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Product> getProductByCode(@PathVariable  String code){
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(()->ProductNotFoundException.forCode(code));
    }

}
