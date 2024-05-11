# API Rest 
## Requisitos
- Entidades : Pessoa e Endereços, onde as cada pessoa pode ter um ou vários endereços, sendo possível definir o endereço principal da pessoa. 

Atributos de Pessoa:

- Nome completo |  Data de nascimento 

Atributos de  Endereço:

- Logradouro | CEP | Número | Cidade | Estado | Principal

- Criar, editar e consultar uma ou mais pessoas;

- Criar, editar e consultar um ou mais endereços de uma pessoa; e

- Poder indicar qual endereço será considerado o principal de uma pessoa.

## Funcionalidades
- Buscar pessoas por nomes 
- Buscar endereco principal
- Buscar todos enderecos de uma pessoa
- Listar todas Pessoas e Endereços registrados
- Editar Pessoa
- Editar Endereço
- Salvar cadastro de nova Pessoa
- Salvar cadastro de novo endereço

## Arquitetura
- Arquitetura utilizada foi de MVC para Web, criando pacotes de Controller, Entidades, Repositório, Request DTO e Response DTO.
- Controller: É a camada que interage diretamente com as solicitações do cliente. Ele recebe a solicitação, processa os dados recebidos e delega a execução das operações de negócio para a camada de serviço.

- Entidade: Representa as tabelas do banco de dados na aplicação. Cada instância de uma entidade corresponde a uma linha na tabela.

- Repositório: É a camada responsável por lidar com operações de banco de dados, como criar, ler, atualizar e excluir registros.

- RequestDTO e ResponseDTO: DTO significa Data Transfer Object. É um padrão de design usado para transferir dados entre processos de software. 

- RequestDTO é o objeto que carrega os dados vindos do cliente para o servidor, enquanto ResponseDTO é o objeto que carrega os dados do servidor para o cliente.
- RequestDTO: É usado para passar os dados da camada Controller para a camada de Serviço.
- ResponseDTO: É criado na camada de Serviço e passado para a camada Controller para ser enviado de volta ao cliente.
- Devido a simplicidade dos relacionamentos e entidades, não foi necessário criar uma nova camada de Serviço, porém é importante observar o tamanho e complexidade do projeto e analisar os esforço x benefícios x riscos da criação de serviços.

## Linguagens e Tecnologias utilizadas
- **Java**: Foi usado Java como linguagem de programação principal
- **Spring Boot**: Foi usado Spring Boot como framework para criar API Rest
- **PostgreSQL**: Foi usado o PostgreSQL como gerenciador de banco de dados
- **Hibernate**: Foi usado o Hibernate para criação das tabelas e atributos a partir do código em JPA
- **Swagger**: Foi usado do Swagger para documentação da API