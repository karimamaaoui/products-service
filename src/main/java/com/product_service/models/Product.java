package com.product_service.models;

import java.util.List;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")  
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;
	    private double price;

}
