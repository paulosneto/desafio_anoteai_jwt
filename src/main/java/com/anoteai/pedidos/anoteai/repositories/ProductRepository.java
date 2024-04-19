package com.anoteai.pedidos.anoteai.repositories;

import com.anoteai.pedidos.anoteai.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
