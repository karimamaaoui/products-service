package com.product_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product_service.models.Product;
import com.product_service.services.IProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	  @Autowired
	    private IProductService productService;
	  @PostMapping
	    public Product create(@RequestBody Product product) {
	        return productService.createProduct(product);
	    }

	    @GetMapping("/list")
	    public List<Product> getAll() {
	        return productService.getAllProduct();
	    }

	    @GetMapping("/get/{id}")
	    public Product getById(@PathVariable Long id) {
	        return productService.getProductById(id);
	    }

	    @PutMapping("/update/{id}")
	    public Product update(@PathVariable Long id, @RequestBody Product product) {
	        product.setId(id);
	        return productService.updateProduct(product);
	    }

	    @DeleteMapping("/delete/{id}")
	    public void delete(@PathVariable Long id) {
	        productService.deleteProduct(id);
	    }
}
