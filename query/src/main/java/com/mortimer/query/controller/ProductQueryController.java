package com.mortimer.query.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mortimer.query.event.ProductEvent;
import com.mortimer.query.model.Product;
import com.mortimer.query.repository.ProductRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductQueryController {
    private final ProductRepository repository;

    public ProductQueryController(ProductRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "products", groupId = "products_group")
    public void processProductEvent(String event){
        System.out.println("Getting event "+ event);
        ProductEvent productEvent;//Optional.empty();
        try {
            productEvent = new ObjectMapper().readValue(event, ProductEvent.class);
            System.out.println(productEvent);

            switch (productEvent.getType()){
                case "ProductCreated":
                    this.repository.save(productEvent.getProduct());
                    break;
                default:
                    break;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public List<Product> getProducts(){
        return repository.findAll().stream().collect(Collectors.toList());
    }
}
