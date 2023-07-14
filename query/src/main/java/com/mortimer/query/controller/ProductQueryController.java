package com.mortimer.query.controller;

import com.mortimer.query.model.Product;
import com.mortimer.query.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductQueryController {
    private final ProductRepository repository;

    public ProductQueryController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> getProducts(){
        return repository.findAll().stream().toList();
    }
}
