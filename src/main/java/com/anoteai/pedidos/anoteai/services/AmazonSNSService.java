package com.anoteai.pedidos.anoteai.services;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.anoteai.pedidos.anoteai.dto.AmazonMessageDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AmazonSNSService {

    AmazonSNS amazonSNS;
    Topic catalogTopic;

    // Referencia o bean de acordo com o tópico escolhido na classe "AwsSnsConfig" isso pra esse
    // topico, se houvesse mais tópicos, era somente fazer outro metodo passando o topico desejado
    public AmazonSNSService(AmazonSNS snsClient, @Qualifier("catalogEventsTopic") Topic catalogTopic){
        this.amazonSNS = snsClient;
        this.catalogTopic = catalogTopic;
    }

    // irá efetuar a publicação
    public void publish(AmazonMessageDTO messageDTO){
            this.amazonSNS.publish(catalogTopic.getTopicArn(), messageDTO.message());
    }




}
