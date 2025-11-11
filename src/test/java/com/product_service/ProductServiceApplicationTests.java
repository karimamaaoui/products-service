package com.product_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product_service.models.Product;
import com.product_service.services.IProductService;
import com.product_service.repositories.ProductRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration-style unit tests for the Product REST API using MockMvc.
 * These tests verify that endpoints behave correctly when interacting with
 * the mocked ProductService and repository.
 */

@SpringBootTest
@AutoConfigureMockMvc

class ProductServiceApplicationTests {

	   @Autowired
       private MockMvc mockMvc;
	   
	    // Mocked repository to avoid real database interaction

	   @MockitoBean
	    private ProductRepository productRepository;

	    // Mocked service layer

       @MockitoBean
       private IProductService productService;
       // Used to convert Java objects into JSON strings (and vice versa)

       @Autowired
       private ObjectMapper objectMapper;

       
       //CREATE PRODUCT
       @Test
       public void givenProductObject_whenCreateProduct_thenReturnSavedProduct() throws Exception {
           Product product = Product.builder()
                   .id(1L)
                   .name("Book")
                   .price(50.0)
                   .build();
           // Mock the behavior of the service
           given(productService.createProduct(any(Product.class))).willReturn(product);
           // When: sending a POST request with product JSON

           ResultActions response = mockMvc.perform(post("/products")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsString(product)));
           // Then: verify the response contains the created product data

           response.andDo(print())
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.name", is(product.getName())))
                   .andExpect(jsonPath("$.price", is(product.getPrice())));
       }
       
       
    //  Test READ ALL Products (GET)
       @Test
       void givenListOfProducts_whenGetAllProducts_thenReturnProductsList() throws Exception {
    	   
           List<Product> products = new ArrayList<>();
           products.add(Product.builder().id(1L).name("Phone").price(1200.0).build());
           products.add(Product.builder().id(2L).name("Tablet").price(800.0).build());
           // Mock the service response
           given(productService.getAllProduct()).willReturn(products);
           // When & Then: perform GET request and check list size

           mockMvc.perform(get("/products/list"))
                   .andExpect(status().isOk())
                   .andDo(print())
                   .andExpect(jsonPath("$.size()", is(products.size())));
       }
       
       
       
       // Test READ ONE Product (GET by ID)
       @Test
       void givenProductId_whenGetProductById_thenReturnProductObject() throws Exception {
           Long productId = 1L;
           Product product = Product.builder()
                   .id(productId)
                   .name("Keyboard")
                   .price(250.0)
                   .build();
           // Mock the service behavior
           given(productService.getProductById(productId)).willReturn(product);
           // When: sending a GET request
           ResultActions response = mockMvc.perform(get("/products/get/{id}", productId));
           // Then: verify the returned product data
           response.andExpect(status().isOk())
                   .andDo(print())
                   .andExpect(jsonPath("$.name", is(product.getName())))
                   .andExpect(jsonPath("$.price", is(product.getPrice())));
       }

       // Test UPDATE Product (PUT)
       @Test
       void givenUpdatedProduct_whenUpdateProduct_thenReturnUpdatedProduct() throws Exception {
           Long productId = 1L;
           Product existing = Product.builder()
                   .id(productId)
                   .name("Old Laptop")
                   .price(1500.0)
                   .build();

           Product updated = Product.builder()
                   .id(productId)
                   .name("New Laptop 2025")
                   .price(2200.0)
                   .build();
           // Mock service response for update
           given(productService.updateProduct(any(Product.class))).willReturn(updated);
           // When: sending PUT request with updated product data

           ResultActions response = mockMvc.perform(put("/products/update/{id}", productId)
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsString(updated)));
           // Then: verify the response reflects the updated product

           response.andExpect(status().isOk())
                   .andDo(print())
                   .andExpect(jsonPath("$.name", is(updated.getName())))
                   .andExpect(jsonPath("$.price", is(updated.getPrice())));
       }
       // Test DELETE Product (DELETE)
       @Test
       void givenProductId_whenDeleteProduct_thenReturn200() throws Exception {
           Long productId = 1L;
           // Mock the delete operation
           willDoNothing().given(productService).deleteProduct(productId);
           // When: sending a DELETE request
           ResultActions response = mockMvc.perform(delete("/products/delete/{id}", productId));
           // Then: verify successful deletion
           response.andExpect(status().isOk())
                   .andDo(print());
       }
	@Test
	void contextLoads() {
	}

}
