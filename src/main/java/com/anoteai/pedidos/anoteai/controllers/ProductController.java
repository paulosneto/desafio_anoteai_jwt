package com.anoteai.pedidos.anoteai.controllers;

import com.anoteai.pedidos.anoteai.domain.Product;
import com.anoteai.pedidos.anoteai.dto.ProductDTO;
import com.anoteai.pedidos.anoteai.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    //Instância da classe Service
    private ProductService productService;

    //Construtor que receberá a instancia do service
    public  ProductController(ProductService productService) {
        this.productService = productService;
    }

        // Método que recebe a requisição de insert e envia a ação para o service realizar o insert
        @PostMapping
        public ResponseEntity<Product> insert(@RequestBody ProductDTO productDTO){
            // Envia o objeto para ser inserido no banco
            Product newProduct = this.productService.insert(productDTO);
            // Retorna ao corpo da requisição o objeto recem inserido
            //return new ResponseEntity<>(newProduct, HttpStatus.OK);
            return ResponseEntity.ok().body(newProduct);
        }

        // Metodo que recebe a requisição de get e manda a ação para o service
        @GetMapping
        public ResponseEntity<List<Product>> getAll(){
            // Retorna a lista de categorias que vem da classe service
            List<Product> producties = this.productService.getAll();
            // retorna a lista completa no corpo da requisição
            return ResponseEntity.ok().body(producties);
        }

        // Método que recebe a requisição para update
        @PutMapping("/{id}")
        public ResponseEntity<Product> update(@PathVariable("id") String id, @RequestBody ProductDTO productDTO){
            // Realiza o update na classe services buscando o id e passando os novos dados
            Product upProduct = this.productService.update(id, productDTO);
            // retorna o objeto atualizado no corpo da requisição.
            return ResponseEntity.ok().body(upProduct);
        }

        // Método que receberá a requisição para delete da categoria pelo id informado
        @DeleteMapping("/{id}")
        public ResponseEntity<Product> delete(@PathVariable("id") String id){
            // Realiza a exclusão da categoria case ela exista
            this.productService.delete(id);
            return ResponseEntity.noContent().build();

        }


}
