# Hackaton - Bookflix
## INF332 (2021)

## Equipe 1
> Bernardo Fonseca Andrade de Carvalho <br>
> Hélio De Rosa Junior <br>
> José Octavio Vitoriano Martines Penna <br>
> Pablo Gabriel Rodrigues Neves Bedoya <br>

## Proposta

Com a atual revolução digital, muitas pessoas abandonaram o hábito da leitura recreativa, que sempre foi uma das melhores amigas dos estressados, preocupados e aflitos, pois sempre serviu de refúgio dos problemas do cotidiano. <br>
BookFlix é uma plataforma para centralizar as publicações de autores já consagrados e também de novos autores, de forma a fornecer um catálogo de diversos gêneros literários, contemplando também trabalhos de autores amadores, que podem ser reconhecidos e incentivados pelos leitores. <br>
É importante salientar que o BookFlix tem a principal missão de estimular a leitura saudável dentre as pessoas, de forma que a contribuir com o bem estar da sociedade em geral. <br>

## Steps to run
0. Pre-requisitos:
- Maven
- Spring Boot
- Lombok
- MySQL

1. Clone o repositório usando a URL a seguir: 
~~~
https://github.com/heliorosajr/bookflix.git
~~~
2. Crie uma `table` em sua instância local do `MySQL` e a nomeie como `bookflix`
3. Na pasta do projeto, siga por `src/main/resources` e popule os campos mostrados abaixo com a `URL` na qual o banco foi apontado, e o `username` e `password` de um usuário root do banco `MySQL`
~~~java
spring.datasource.url=jdbc:mysql://localhost:3306/bookflix
spring.datasource.username={root}
spring.datasource.password={root}
~~~
2. Na pasta raiz do projeto rode o comando 
~~~
mvn clean install
~~~
3. Rode o projeto utilizando o comando:
~~~
mvn spring-boot:run
~~~

## Documentação

Swagger para as APIs expostas podem ser encontradas, após rodar o projeto, na página:

~~~
http://127.0.0.1:8080/swagger-ui.html
~~~

## Links interassantes:
1. Publicação da revista Galileu sobre os benefícios da leitura:
- obs: Links dos estudos podem ser encontrados na própria pagina da publicação.
~~~
https://revistagalileu.globo.com/Ciencia/noticia/2016/12/7-provas-de-que-ler-faz-bem-para-sua-saude.html
~~~

2. OpenLibrary API DOC:
~~~
https://openlibrary.org/developers/api
~~~

