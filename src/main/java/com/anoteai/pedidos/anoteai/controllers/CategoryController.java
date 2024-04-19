package com.anoteai.pedidos.anoteai.controllers;

import com.anoteai.pedidos.anoteai.domain.Category;
import com.anoteai.pedidos.anoteai.dto.CategoryDTO;
import com.anoteai.pedidos.anoteai.services.CategoryService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    //Instância da classe Service
    private CategoryService categoryService;

    //Construtor que receberá a instancia do service
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    // Método que recebe a requisição de insert e envia a ação para o service realizar o insert
    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryDTO categoryData){
        // Envia o objeto para ser inserido no banco
        Category newCategory = this.categoryService.insert(categoryData);
        // Retorna ao corpo da requisição o objeto recem inserido
        return ResponseEntity.ok().body(newCategory);
    }

    // Metodo que recebe a requisição de get e manda a ação para o service
    @GetMapping
    public ResponseEntity<List<Category>> getAll(){
        // Retorna a lista de categorias que vem da classe service
        List<Category> categories = this.categoryService.getAll();
        // retorna a lista completa no corpo da requisição
        return ResponseEntity.ok().body(categories);
    }

    // Método que recebe a requisição para update
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") String id, @RequestBody CategoryDTO categoryDTO){
        // Realiza o update na classe services buscando o id e passando os novos dados
        Category upCategory = this.categoryService.update(id, categoryDTO);
        // retorna o objeto atualizado no corpo da requisição.
        return ResponseEntity.ok().body(upCategory);
    }

    // Método que receberá a requisição para delete da categoria pelo id informado
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable("id") String id){
            // Realiza a exclusão da categoria case ela exista
            this.categoryService.delete(id);
            return ResponseEntity.noContent().build();
    }
}
