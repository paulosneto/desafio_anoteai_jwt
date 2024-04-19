package com.anoteai.pedidos.anoteai.repositories;

import com.anoteai.pedidos.anoteai.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
}
