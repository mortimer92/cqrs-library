package com.mortimer.command.controller;

import com.mortimer.command.model.Product;
import com.mortimer.command.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductCommandController {
    private ProductRepository repository;

    public ProductCommandController(ProductRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        repository.save(product);
        return ResponseEntity.ok().body(product);
    }
}
