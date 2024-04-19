package com.anoteai.pedidos.anoteai.domain;

import com.anoteai.pedidos.anoteai.dto.CategoryDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.json.JsonObject;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("categories")
public class Category {
    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;

    public Category(CategoryDTO categoryDTO) {
        this.title = categoryDTO.title();
        this.description = categoryDTO.description();
        this.ownerId = categoryDTO.ownerId();
    }

    @Override
    public String toString(){
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        json.put("description", this.description);
        json.put("ownerId", this.ownerId);
        json.put("Id", this.id);
        json.put("type", "category");

        return json.toString();

    }

}
