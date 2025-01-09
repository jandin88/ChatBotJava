package org.chat.chatbotjava.web.controller;


import org.chat.chatbotjava.domain.service.ChatBotService;
import org.chat.chatbotjava.web.dto.QuestionDto;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
@RequestMapping({"/", "chat"})
public class ChatController {

    private static final String PAGINA_CHAT = "chat";
    private ChatBotService chatBotService;

    public ChatController(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }


    @ResponseBody
    @PostMapping
    public Flux<String> responderPerguntaStreaming(@RequestBody QuestionDto dto) {
        // Obtém o fluxo de resposta da pergunta
        return chatBotService.responseQuestion(dto.pergunta())
                .delayElements(Duration.ofMillis(100)); // Opcional: simula uma pequena latência
    }

    @GetMapping
    public String carregarPaginaChatbot() {
        return  PAGINA_CHAT;
    }
    @GetMapping("limpar")
    public String limparConversa() {
        return PAGINA_CHAT;
    }
}
