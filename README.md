# wishlist
Dado a situação informada, vamos imaginar que já temos dentro do e-commerce toda parte já desenhada do
front e do bff que fara toda parte de conexão e seguranças primarais.
Passado pela requisição do BFF, temos a arquitetura abaixo onde app_wishlist é
responsavel por salvar, editar, visualizar e adicionar produtos no carrinho do
usuario dentro do MongoDB e tambem receber e enviar mensagem ao tópico se assim necessário.

Tendo em vista que a compra foi finalizada, será consumido uma mensagem no topico
finished_dream_product onde será deletado da base do mongo o carrinho e enviado uma
mensagem para os topicos finished_email_cliente e dream_produt onde eles daram 
continuidade a todo tramite de finalização da solicitação.

![img_1.png](img_1.png)

## Pré-requisitos.
Java 11.<br/>
MongoDB.<br/>
Kafka.<br/>
Conhecimento em desenvolvimento REATIVO.

## Patterns
Build<br/>
Facade


## Execução
Considerando que voce já esteja com o docker rodando as imagens do kafka e 
do mongoDB, configurar o arquivo application.yml.

### Observações adicionais
Até o momento não foi registrado nenhum problema ao executar o projeto.

### Históricos de tarefas solicitadas
LINK 01<br/>
LINK 02<br/>
LINK 03<br/>
LINK 04<br/>
LINK 05<br/>
LINK 06<br/>


