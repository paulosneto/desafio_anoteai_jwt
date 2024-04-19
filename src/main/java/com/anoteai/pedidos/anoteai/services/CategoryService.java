package com.anoteai.pedidos.anoteai.services;

import com.anoteai.pedidos.anoteai.domain.Category;
import com.anoteai.pedidos.anoteai.dto.AmazonMessageDTO;
import com.anoteai.pedidos.anoteai.dto.CategoryDTO;
import com.anoteai.pedidos.anoteai.exceptions.CategoryNotFoundException;
import com.anoteai.pedidos.anoteai.repositories.CategoryRepository;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository repository;

    private AmazonSNSService amazonSNSService;

    // Construtor que receberá uma instância da classe Repository
    public CategoryService(CategoryRepository repository, AmazonSNSService amazonSNSService) {
        this.repository = repository;
        this.amazonSNSService = amazonSNSService;
    }

    public Category insert(CategoryDTO categoryDTO){
        //Cria uma nova categoria
        Category category = new Category(categoryDTO);
        // Salva a categoria no banco
        this.repository.save(category);

        // publica a categoria no topico
        this.amazonSNSService.publish(new AmazonMessageDTO(category.toString()));

        // retorna o objeto recém salvo
        return category;
    }

    public List<Category> getAll(){
        // Retorna todos os objetos salvos
        return this.repository.findAll();
    }

    public Optional<Category> getById(String id){
        return this.repository.findById(id);
    }

    public Category update(String id, CategoryDTO categoryDTO){

        //Pesquisa pelo id informado se existe o objeto ja salvo no banco
        Category category = this.repository.findById(id).orElseThrow(CategoryNotFoundException::new);

            //Verifica se o titulo enviado está diferente de vazio
            if(!categoryDTO.title().equals(""))
                category.setTitle(categoryDTO.title());

            //Verifica se a descrição enviada está diferente de vazio
            if(!categoryDTO.description().equals(""))
                category.setDescription(categoryDTO.description());



            //Salva os dados recém atualizado no banco de dados
            this.repository.save(category);

            // publica a categoria no topico
            this.amazonSNSService.publish(new AmazonMessageDTO(category.toString()));

            //retorna a instanciaa recém alterada para exibição
        return category;
    }

       public void delete(String id){
            // Faz uma busca no banco com o Id informado, se o Id for encontrado,
           // faz a requisição a repository para Deleção
            Category category = this.repository.findById(id).orElseThrow(CategoryNotFoundException::new);
            // Efetua a ação de delete
            this.repository.delete(category);

       }
}
