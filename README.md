# :books: POC: RabbitMQ com Spring Boot :rabbit:
 - Repositório destinado a estudos sobre o RabbitMQ.
 - Serviços:
    - Person:
        - Cadastra pessoas e publica eventos de pessoas cadastradas/alteradas/excluídas,
    - Order: 
        - Criação do pedido e publicação de evento de pagamento criado.
        - Ouve os eventos de pagamento processado e atualiza em seu banco de dados.
        - Ouve os eventos de pessoa cadastrada/alterada/excluída e atualiza em seu banco de dados.
    - Payment: 
        - Ouve o evento de pagamento criado e salva em seu banco de dados.
        - Faz o processamento dos pagamentos com o gateway (simulação). 
        - Publica eventos de pagamento processado.
     
 - Testes de stress com vários processamentos simultâneos com controle de transação.
 - Testes parando e subindo novamente o serviço de pagamentos, simulando um deploy enquanto os processos estão sendo executados.
 - Testes de replicação e consistência de dados entre os serviços.
