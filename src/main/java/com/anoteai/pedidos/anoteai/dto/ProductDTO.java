package com.anoteai.pedidos.anoteai.dto;

import com.anoteai.pedidos.anoteai.domain.Category;

public record ProductDTO(String title, String description, String ownerId, Integer price, String categoryId) {
//public record ProductDTO(String title, String description, String ownerId, Integer price, Category category) {
}
