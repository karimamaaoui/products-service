package com.product_service.serviceImple;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.product_service.models.Product;
import com.product_service.repositories.ProductRepository;
import com.product_service.services.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

	@Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> existing = productRepository.findById(product.getId());
        if (existing.isPresent()) {
            Product toUpdate = existing.get();
            toUpdate.setName(product.getName());
            toUpdate.setPrice(product.getPrice());
            return productRepository.save(toUpdate);
        }
        return null; 
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}