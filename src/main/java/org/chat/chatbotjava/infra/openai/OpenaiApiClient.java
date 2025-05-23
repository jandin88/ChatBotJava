package org.chat.chatbotjava.infra.openai;

import com.openai.client.OpenAIClient;
import com.openai.client.OpenAIClientImpl;
import com.openai.models.*;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.http.StreamResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Component
public class OpenaiApiClient {

    private static final Logger log = LoggerFactory.getLogger(OpenaiApiClient.class);

    private final OpenAIClient openAiClient;
    private final List<ChatCompletionMessageParam> mensagens = new ArrayList<>();
    private final String version;
    private final Integer maxtoken;
    private final Double temperature;

    public OpenaiApiClient(
          @Value("${app.openai.api.key}")String apiKey,
          @Value("${app.openai.api.version}")  String version,
          @Value("${app.openai.api.maxtoken}") Integer maxtoken,
          @Value("${app.openai.api.temperature}") Double temperature
          ) {
        this.openAiClient = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
        this.version= version;
        this.maxtoken= maxtoken;
        this.temperature=temperature;

    }

    public Flux<String> sendRequestChatCompletion(DataRequestChatAi dados) {

        if(mensagens.isEmpty()) {
            mensagens.add(ChatCompletionMessageParam.ofChatCompletionSystemMessageParam(ChatCompletionSystemMessageParam.builder()
                            .role(ChatCompletionSystemMessageParam.Role.SYSTEM)
                            .content(ChatCompletionSystemMessageParam.Content.ofTextContent(dados.promptSystem()))
                            .build()));
        }

        mensagens.add(ChatCompletionMessageParam.ofChatCompletionUserMessageParam(ChatCompletionUserMessageParam.builder()
                .role(ChatCompletionUserMessageParam.Role.USER)
                .content(ChatCompletionUserMessageParam.Content.ofTextContent(dados.promptUser()))
                .build()));

        ChatCompletionCreateParams completionCreateParams = ChatCompletionCreateParams.builder()
              .model(version)               // modelo mais econômico :contentReference[oaicite:0]{index=0}
                .maxTokens(maxtoken)
                .temperature(temperature)
                .messages(mensagens)
                .build();



        return streamFluxo(completionCreateParams);
    }

//    public List<ChatCompletionMessageParam> loadConversations(){
//        if(mensagens.isEmpty()){
//            System.out.println("\n\n\n\n\n "+mensagens+"\n\n\n\n\n");
//            return mensagens;
//        }
//        return null;
//    }

    private Flux<String> streamFluxo(ChatCompletionCreateParams completionCreateParams) {
        return Flux.create(sink -> {
            try (StreamResponse<ChatCompletionChunk> messageStream =
                         openAiClient.chat().completions().createStreaming(completionCreateParams)) {

                messageStream.stream()
                        .flatMap(chunk -> chunk.choices().stream())
                        .flatMap(choice -> choice.delta().content().stream())
                        .forEach(sink::next);

                sink.complete();
            } catch (Exception e) {
                log.error("Erro durante o streaming", e);
                sink.error(e);
            }
        });
    }

}
