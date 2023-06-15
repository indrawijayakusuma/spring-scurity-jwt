package com.scurity.testscurity.service;

import com.scurity.testscurity.entities.Product;
import com.scurity.testscurity.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.Optional;
@Component
public interface ProductService {

    List<Product> getProducts();
    Product addProduct(Product product);


}
