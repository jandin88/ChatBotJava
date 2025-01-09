package org.chat.chatbotjava.domain.service;

import org.chat.chatbotjava.infra.openai.DataRequestChatAi;
import org.chat.chatbotjava.infra.openai.OpenaiApiClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatBotService {

    private final OpenaiApiClient client;

    public ChatBotService(OpenaiApiClient client){
        this.client=client;
    }

    public Flux<String> responseQuestion(String pergunta){
        String promptSystem = "Você é um chatbot de atendimento a clientes de um ecommerce e deve responder apenas perguntas relacionadas com o ecommerce";
        DataRequestChatAi dados= new DataRequestChatAi(promptSystem, pergunta);
        return client.enviarRequestChatCompletion(dados);
    }


}
