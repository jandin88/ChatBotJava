package org.chat.chatbotjava.domain.service;

import com.openai.models.Thread;
import org.chat.chatbotjava.infra.openai.DataRequestChatAi;
import org.chat.chatbotjava.infra.openai.OpenaiApiClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ChatBotService {

    private final OpenaiApiClient client;

    public ChatBotService(OpenaiApiClient client){
        this.client=client;
    }

    public Flux<String> responseQuestion(String pergunta)  {
        String promptSystem = "Seu nome é Dury é um chatbot especializado em atendimento ao cliente de um e-commerce. " +
                        "Sua função é responder exclusivamente a perguntas relacionadas aos produtos, serviços e " +
                        "processos deste e-commerce."+readDataEcomart();

        DataRequestChatAi dados= new DataRequestChatAi(promptSystem, pergunta);
        return client.sendRequestChatCompletion(dados);
    }

    private String readDataEcomart()  {
        try {
            Path infoDataEcomart = Paths.get("./src/main/resources/ecomart/informacoes.md");
            Path politicasDataEcomart = Paths.get("./src/main/resources/ecomart/politicas.md");


            return Files.readString(infoDataEcomart) + "\n\n\n\n" + Files.readString(politicasDataEcomart);

        }catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch(SecurityException e){
            e.printStackTrace();
            throw new SecurityException("erro ao encontrar o arquivo");
        }
    }


//    public List<String> loadHistory(){
//        return client.
//    }


}
