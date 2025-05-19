package br.com.factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Cnv@x#2023!";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/estoque?useSSL=false&serverTimezone=UTC";
    
    public static Connection createConnectionToMySQL() throws Exception {
        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        return connection;
    }
    
    public static void main(String[] args) throws Exception {
        try (Connection con = createConnectionToMySQL()) {
            System.out.println("Conexão obtida com sucesso!");
        }
    } 
}