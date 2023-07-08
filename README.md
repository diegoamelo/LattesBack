## Projeto de Currículos Lattes de Pesquisadores
Este projeto tem como objetivo permitir o cadastro e a apresentação de currículos de pesquisadores cadastrados na plataforma Lattes.

### Funcionalidades
- Cadastro de pesquisadores com informações básicas como nome, ufNascimento e identificador
- Busca de pesquisadores por nome ou identificador
- Inclusão de produções acadêmicas do pesquisador, como artigos e livros publicados
- Listagem de instituições de pesquisa relacionadas aos pesquisadores cadastrados

### Tecnologias utilizadas
- Banco de dados PostgreSQL para armazenamento dos dados
- Linguagem de programação Java com framework Spring Boot na construção das páginas e conexão com o banco de dados.
- Arquivos XML para extração dos dados do Lattes

### Como usar
1. Clone o repositório
2. Importe o arquivo "bdlattes_backup.sql" no PostgreSQL
3. Configure as informações de conexão com o banco de dados no arquivo "application.properties"
4. Acesse o arquivo "Requisições.http" para cadastrar e visualizar a lista de pesquisadores e navegar pelas funcionalidades.

### Contribuições
Contribuições são sempre bem-vindas! Caso queira participar do desenvolvimento deste projeto, entre em contato com a equipe de desenvolvedores.
