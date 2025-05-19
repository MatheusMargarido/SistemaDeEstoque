# Sistema de Gerenciamento de Estoque 📦

## Visão Geral 

Este projeto é um sistema simples de gerenciamento de estoque desenvolvido em Java. A aplicação permite que o usuário realize operações básicas de CRUD (Criar, Ler, Atualizar, Deletar) sobre itens, por meio de um menu interativo em console. Cada item possui informações como nome, quantidade e data de cadastro.

## Funcionalidades 

- **Inserir Item:** Cadastra um novo item no estoque, registrando seu nome, quantidade e data de cadastro.
- **Atualizar Item:** Permite modificar os dados de um item já existente, identificado pelo seu ID.
- **Deletar Item:** Remove um item do estoque com base no ID informado.
- **Consultar Itens:** Lista todos os itens presentes no banco de dados, exibindo detalhes como ID, nome, quantidade e data de cadastro.

## Requisitos 

- **Java 8 ou superior:** Assegure que uma versão compatível do Java esteja instalada.
- **MySQL:** O projeto utiliza um banco de dados MySQL para persistência dos dados.
- **Variáveis de Ambiente:**  
  Configure as seguintes variáveis para estabelecer a conexão com o banco de dados:
  - `DB_USER` - Nome do usuário do banco (ex.: `setx DB_USER "root"`).
  - `DB_PASS` - Senha do banco (ex.: `setx DB_PASS "123456789"`).
  - `DB_URL`  - URL de conexão (ex.: `jdbc:mysql://localhost:3306/nomeDoBanco`).

## Configuração e Execução ⚙️

1. **Configuração do Banco de Dados:**  
   - Certifique-se de que o MySQL está instalado e em execução.
   - Defina as variáveis de ambiente (`DB_USER`, `DB_PASS`, `DB_URL`) com as credenciais corretas.

2. **Compilação do Projeto:**  
   - Caso utilize uma IDE (como Eclipse ou IntelliJ), importe o projeto como um projeto Java.
   - Para compilação via linha de comando, navegue até o diretório do projeto e compile os arquivos Java. Exemplo:
     ```bash
     javac -d bin src/br/com/estoque/application/Main.java
     ```
   
3. **Execução da Aplicação:**  
   - Execute a classe principal `Main` utilizando o comando:
     ```bash
     java -cp bin br.com.estoque.application.Main
     ```

4. **Execução dos Testes Unitários:**  
   - Os testes unitários estão localizados no pacote `br.com.estoque.dao` (classe `ItemDAOTest`).
   - Utilize o JUnit 5 para rodar os testes, garantindo o correto funcionamento das operações de inserção, atualização, deleção e consulta.

