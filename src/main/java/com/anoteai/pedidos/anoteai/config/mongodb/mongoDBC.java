package com.anoteai.pedidos.anoteai.config.mongodb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
public class mongoDBC {

    // Classe que fará a conexão com o bando que nesse caso foi escolhido o "MongoDB"
    @Bean
    public MongoDatabaseFactory mongoConfigure(){
        return new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/product-catalog");
        //return new SimpleMongoClientDatabaseFactory("mongodb://root:example@mongo:27017/");
    }


    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(mongoConfigure());
    }

}
