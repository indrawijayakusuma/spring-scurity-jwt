package com.scurity.testscurity.controller;

import com.scurity.testscurity.entities.AuthRequest;
import com.scurity.testscurity.entities.Product;
import com.scurity.testscurity.entities.User;
import com.scurity.testscurity.service.JwtService;
import com.scurity.testscurity.service.ProductService;
import com.scurity.testscurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return ResponseEntity.ok().body( productService.getProducts());
    }
    @PostMapping("/product")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        return ResponseEntity.ok().body(productService.addProduct(product));
    }

    @PostMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok().body(userService.AddUser(user));
    }
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body( userService.getUsers());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String>authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        if (authentication.isAuthenticated()){
            return ResponseEntity.ok().body(jwtService.generateToken(authRequest.getUsername()));
        }else {
            throw new UsernameNotFoundException("not found");
        }
    }

}
