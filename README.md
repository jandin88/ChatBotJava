# Chatbot E-commerce com Java e Spring Boot

Este repositório contém o código-fonte de um chatbot desenvolvido para um e-commerce utilizando Java, Spring Boot e a biblioteca oficial do ChatGPT. O objetivo é oferecer uma solução eficiente e funcional para atender às necessidades de interação automatizada com os clientes.

---

## 📋 Funcionalidades

- Respostas automatizadas a perguntas frequentes.
- Suporte ao cliente em tempo real.
- Integração direta com a API oficial da OpenAI.

---

## 🛠️ Tecnologias Utilizadas

- **Java**: Linguagem principal do projeto.
- **Spring Boot**: Framework para desenvolvimento de aplicações web.
- **OpenAI Java SDK**: Biblioteca oficial para integração com a API do ChatGPT.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

Certifique-se de ter os seguintes programas instalados em sua máquina:

- [Java 17+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/)
- Conta na [OpenAI](https://platform.openai.com/)

### Passo a Passo

1. Clone o repositório:

   ```bash
   git clone https://github.com/jandin88/ChatBotJava.git
   cd ChatBotJava
   ```

2. Configure a chave da API:

   No arquivo `application.properties`, adicione sua chave da OpenAI:

   ```properties
   openai.api.key=YOUR_API_KEY
   ```

3. Compile e execute o projeto:

   ```bash
   mvn spring-boot:run
   ```

4. Acesse a aplicação no navegador:

   ```
   http://localhost:8080
   ```

---

## 📂 Estrutura do Projeto

```plaintext
src
├── main
│   ├── java
│   │   └── org.chat.chatbotjava
│   │       ├── config              # Configurações do projeto
│   │       ├── domain.service      # Lógica de negócios
│   │       ├── infra.openai        # Integração com a openai
│   │       ├── service             # Lógica de negócios
│   │       └── Web
│   │           ├── Controllers     #Endpoints
│   │           └── Dto                 
│   └── resources
│       ├── application.properties  # Configurações da aplicação
│       ├── static                  # CSS e JS
│       └── templates               # Templates HTML 
```

---


