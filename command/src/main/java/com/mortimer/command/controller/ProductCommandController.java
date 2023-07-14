package com.mortimer.command.controller;

import com.mortimer.command.event.ProductEvent;
import com.mortimer.command.model.Product;
import com.mortimer.command.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductCommandController {
    private final ProductRepository repository;
    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

    public ProductCommandController(ProductRepository repository, KafkaTemplate<String, ProductEvent> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        product = repository.save(product);
        ProductEvent event = new ProductEvent("ProductCreated", product);
        this.kafkaTemplate.send("products", event);
        return ResponseEntity.ok().body(product);
    }
}
