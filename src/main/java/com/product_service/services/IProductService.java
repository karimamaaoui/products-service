package com.product_service.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.product_service.models.Product;


public interface IProductService {
    Product createProduct(Product product);

    Product updateProduct(Product product);

    List < Product > getAllProduct();

    Product getProductById(Long productId);

    void deleteProduct(Long id);
}
