# ScreenMatch

Projeto desenvolvido no curso da formação Avançando com Java da Oracle e Alura.
Busca por dados na [API OMDB](https://www.omdbapi.com/apikey.aspx) e retorna informações sobre séries utilizando a biblioteca Jackson para deserializar os dados.

## 👨‍💻 Desenvolvedores

 **`Wellington Santos Costa`**

## 💡 Tecnologias

<img 
    align="left" 
    alt="JAVA"
    title="JAVA" 
    width="30px" 
    style="padding-right: 10px;" 
    src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original.svg" 
/>
<img 
    align="left" 
    alt="Spring" 
    title="Spring"
    width="30px" 
    style="padding-right: 10px;" 
    src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original-wordmark.svg" 
/>
<img 
    align="left" 
    alt="PostgreSQL" 
    title="PostgreSQL"
    width="30px" 
    style="padding-right: 10px;" 
    src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/postgresql/postgresql-original.svg" 
/>
<img 
    align="left" 
    alt="Hibernate" 
    title="Hibernate"
    width="30px" 
    style="padding-right: 10px;" 
    src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/hibernate/hibernate-original-wordmark.svg" 
/>
<br/>
<br/>

## 🔨 Objetivos do projeto

- Atualizar o projeto ScreenMatch, criado inicialmente com linha de comando, para se transformar em uma `API REST`;
- Entender a `estrutura MVC` no desenvolvimento de aplicações Web;
- Criar e mapear rotas utilizando as `anotações do Spring`;
- Utilizar boas práticas e entender o conceito de `DTO (Data Transfer Object)`;
- `Conectar dados` disponibilizados pelo back-end à uma aplicação front-end, disponibilizada nesse [link](https://github.com/jacqueline-oliveira/3356-java-web-front)
- Tratar erros de `CORS` na disponibilização de dados;
- Fornecer uma experiência fullstack, demonstrando o fluxo ponta a ponta da aplicação.

## 📦 Estrutura do Projeto

```plaintext
com.wellscosta.screenmatch
|   config                          // Configurações da aplicação
|   controller                      // Controllers REST
|   dto
|   model                           // Entidades do banco
|   principal                       // Classe para rodar o projeto sem front
|   service                         // Servicos (Regras de negocio)
|   |-- traducao                    // Recebe API de traducao
|   resources                       // Utilitários e configuração do banco
|   ScreenMatchApplication.java     // Classe principal
```

## Variáveis de Ambiente necessárias

| Variável                        | Descrição                                                                   | Exemplo         |
|---------------------------------|-----------------------------------------------------------------------------|-----------------|
| `OMDB_API_KEY`                  | API KEY do OMDB                                                             | `fXf615X3`      |
| `DB_HOST`                       | Host do Banco Postgre                                                       | `localhost:8080`|
| `DB_NAME`                       | Nome do Banco de Dados                                                      | `screen-match`  |
| `DB_USER`                       | Usuário do Banco Postgre                                                    | `postgres`      |
| `DB_PASSWORD`                   | Senha do Banco Postgre                                                      | `admin`         |

Pode-se pegar a chave da API aqui [OMDB API](https://www.omdbapi.com/apikey.aspx)

## 👥 Contribuindo

Contribuições são bem-vindas! Para contribuir com o projeto, por favor siga estes passos:

1. Faça um fork do repositório.
2. Crie uma branch para sua feature ou correção (`git checkout -b feature/nova-feature`).
3. Faça commit das suas mudanças (`git commit -am 'Adiciona nova feature'`).
4. Envie suas alterações para o repositório (`git push origin feature/nova-feature`).
5. Abra um pull request.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
