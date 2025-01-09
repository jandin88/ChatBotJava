package org.chat.chatbotjava.infra.openai;


import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.http.StreamResponse;
import com.openai.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.openai.client.OpenAIClient;
import reactor.core.publisher.Flux;

@Component
public class OpenaiApiClient {

    private final OpenAIClient openAiClient;

    public OpenaiApiClient(@Value("${app.openai.api.key}") String apiKey) {
        this.openAiClient = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }
    public Flux<String> enviarRequestChatCompletion(DataRequestChatAi dados) {

        ChatCompletionUserMessageParam userMessage = ChatCompletionUserMessageParam.builder()
                .role(ChatCompletionUserMessageParam.Role.USER)
                .content(ChatCompletionUserMessageParam.Content.ofTextContent(dados.promptUser()))
                .build();

        ChatCompletionCreateParams completionCreateParams=ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4O_MINI)
                .maxTokens(600)
                .addMessage(userMessage)
                .build();



        return Flux.create(sink -> {
            try (StreamResponse<ChatCompletionChunk> messageStream =
                         openAiClient.chat().completions().createStreaming(completionCreateParams)) {

                messageStream.stream()
                        .flatMap(chunk -> chunk.choices().stream())
                        .flatMap(choice -> choice.delta().content().stream())
                        .forEach(sink::next); // Envia diretamente ao cliente

                sink.complete();
            } catch (Exception e) {
                sink.error(e);
            }
        });

    }
}

