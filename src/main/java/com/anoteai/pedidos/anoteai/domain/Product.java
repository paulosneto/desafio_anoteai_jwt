package com.anoteai.pedidos.anoteai.domain;

import com.anoteai.pedidos.anoteai.dto.ProductDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Document("products")
public class Product {
    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;
    private Integer price;
    //private Category category;
    private String category;


    public Product(ProductDTO productDTO){
        this.title = productDTO.title();
        this.description = productDTO.description();
        this.ownerId = productDTO.ownerId();
        this.price = productDTO.price();
        this.category = productDTO.categoryId();
        //this.category.setId(productDTO.categoryId());
    }

    @Override
    public String toString(){
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        json.put("description", this.description);
        json.put("ownerId", this.ownerId);
        json.put("price", this.price);
        json.put("categoryId", this.category);
        json.put("Id", this.id);
        json.put("type", "product");

        return json.toString();

    }
}
