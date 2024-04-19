package com.anoteai.pedidos.anoteai.services;

import com.anoteai.pedidos.anoteai.domain.Category;
import com.anoteai.pedidos.anoteai.domain.Product;
import com.anoteai.pedidos.anoteai.dto.AmazonMessageDTO;
import com.anoteai.pedidos.anoteai.dto.ProductDTO;
import com.anoteai.pedidos.anoteai.exceptions.CategoryNotFoundException;
import com.anoteai.pedidos.anoteai.exceptions.ProductNotFoundException;
import com.anoteai.pedidos.anoteai.repositories.CategoryRepository;
import com.anoteai.pedidos.anoteai.repositories.ProductRepository;
import com.anoteai.pedidos.anoteai.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private CategoryService categoryService;
    private ProductRepository productRepository;
    private AmazonSNSService amazonSNSService;

    // Faz a instancia do repositorio de produto, juntamente com a instancia dos serviços da SNS
    public ProductService(CategoryService categoryService, ProductRepository productRepository, AmazonSNSService amazonSNSService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.amazonSNSService = amazonSNSService;
    }


    public Product insert(ProductDTO productDTO){

        // busca a categoria conforme o id informado
       Category category = this.categoryService.getById(productDTO.categoryId()).orElseThrow(CategoryNotFoundException::new);
       //Category category = this.categoryService.getById(productDTO.category().getId()).orElseThrow(CategoryNotFoundException::new);


        // Cria instancia para realizar o insert do produto informado
        Product nProduct = new Product(productDTO);
        // Armazena a categoria no produto
        nProduct.setCategory(category.getId());
        // Salva o produto
        this.productRepository.save(nProduct);

        // publica o topico
        amazonSNSService.publish(new AmazonMessageDTO(nProduct.toString()));

        // retorna o produto recém cadastrado
        return nProduct;
    }

    //retorna a lista de todos os produtos
    public List<Product> getAll(){
        /*List<Product> productList = this.productRepository.findAll();
        return productList;*/

        return this.productRepository.findAll();
    }

    // Faz a requisição para atualizar os dados do produto
    public Product update(String id, ProductDTO productDTO){
        // Verifica se existe o produto pelo id informado
       Product nProduct = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

       if(productDTO.categoryId() != null){
     //if(productDTO.category().getId() != null){
        // Se o produto existir, faz uma busca na categoria presente no produto
        this.categoryService.getById(productDTO.categoryId()).orElseThrow(CategoryNotFoundException::new);

        //this.categoryService.getById(productDTO.categoryId()).ifPresent(nProduct::setCategory);
        //this.categoryService.getById(productDTO.category().getId()).orElseThrow(CategoryNotFoundException::new);
        //this.categoryService.getById(productDTO.category().getId()).ifPresent(nProduct::setCategory);
       }

       /*if(!productDTO.description().isEmpty()) nProduct.setCategory(productDTO.categoryId());
       if(!(productDTO.price() == null)) nProduct.setPrice(productDTO.price());
        if(!(productDTO.categoryId() == null)) nProduct.getCategory().setId(productDTO.categoryId());*/

       // Se a categoria existe juntamente com o produto, faz a atualização dos dados abaixo
       if(!productDTO.title().isEmpty()) nProduct.setTitle(productDTO.title());
       if(!(productDTO.categoryId() == null)) nProduct.setCategory(productDTO.categoryId());
        if(!productDTO.description().isEmpty())  nProduct.setDescription(productDTO.description());
        if(!(productDTO.price() == null)) nProduct.setPrice(productDTO.price());


        // Salva o produto recém atualizado
        this.productRepository.save(nProduct);

        // publica o tópico
        this.amazonSNSService.publish(new AmazonMessageDTO(nProduct.toString()));

        // Retorna o produto atualizado
       return nProduct;
    }

    public void delete(String id){
        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        this.productRepository.delete(product);
    }




}
