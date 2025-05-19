package br.com.factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    // Pegando credenciais do banco de dados a partir de variáveis de ambiente
    private static final String USERNAME = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASS");
    private static final String DATABASE_URL = System.getenv("DB_URL");

    public static Connection createConnectionToMySQL() throws Exception {
        if (USERNAME == null || PASSWORD == null || DATABASE_URL == null) {
            throw new IllegalStateException("Variáveis de ambiente do banco de dados não configuradas!");
        }
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

    public static void main(String[] args) throws Exception {
        try (Connection con = createConnectionToMySQL()) {
            System.out.println("Conexão obtida com sucesso!");
        }
    }
}