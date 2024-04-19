package com.anoteai.pedidos.anoteai.config.aws;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSnsConfig {
    // os topicos abaixo fazem referencia aos
    // dados de acesso aos cerviços da AWS SNS

    // Retorna a regiao do serviço
    @Value("${aws.region}")
    private String region;

    // Retorna o acesso a 'acces-key'
    @Value("${aws.accessKeyId}")
    private String accesKeyId;

    // Retorna a secret key
    @Value("@{aws.secretLKey}")
    private String secretKey;

    // retorna o nome do topico com o nome do catalogo riado na AWS
    @Value("${aws.sns.topic.catalog.arn}")
    private String catalogTopicArn;

    // Cria a instancia passando as credenciais do serviço para poder fazer os registros no topico
    @Bean
    public AmazonSNS amazonSNSBuilder(){
        BasicAWSCredentials credentials = new BasicAWSCredentials(accesKeyId, secretKey);

        return AmazonSNSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }



    @Bean(name = "catalogEventsTopic")
    public Topic snsCatalogTopicBuilder(){
        return new Topic().withTopicArn(catalogTopicArn);
    }


}
