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
        String promptSystem = "Seu nome é Dury é um chatbot especializado em atendimento ao cliente de um e-commerce. " +
                        "Sua função é responder exclusivamente a perguntas relacionadas aos produtos, serviços e " +
                        "processos deste e-commerce.";
        DataRequestChatAi dados= new DataRequestChatAi(promptSystem, pergunta);
        return client.enviarRequestChatCompletion(dados);
    }


}
