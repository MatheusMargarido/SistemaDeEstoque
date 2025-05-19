package br.com.estoque.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.factory.ConnectionFactory;
import br.com.model.Item;

public class ItemDAO {

    private String tableName;

    public ItemDAO(String tableName) {
        this.tableName = tableName;
    }

    public void save(Item item) throws Exception {
        String sql = "INSERT INTO " + tableName + " (nome, quantidade, dataCadastro) VALUES (?,?,?)";

        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, item.getNome());
            pstm.setInt(2, item.getQuantidade());
            pstm.setDate(3, new Date(item.getDataCadastro().getTime()));

            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Item salvo com sucesso!");
            } else {
                System.out.println("Não foi possível salvar o item.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar item: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Item item) throws Exception {
        String sql = "UPDATE " + tableName + " SET nome = ?, quantidade = ?, dataCadastro = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, item.getNome());
            pstm.setInt(2, item.getQuantidade());
            pstm.setDate(3, new Date(item.getDataCadastro().getTime()));
            pstm.setInt(4, item.getId());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("Nenhum item atualizado. O ID informado não existe.");
            } else {
                System.out.println("Item atualizado com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar item: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteByID(int id) throws Exception {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";

        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id);
            int affectedRows = pstm.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("Nenhum item removido. O ID informado não existe.");
            } else {
                System.out.println("Item removido com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover item: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Item> getItens() throws Exception {
        String sql = "SELECT * FROM " + tableName;
        List<Item> itens = new ArrayList<>();

        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rset = pstm.executeQuery()) {

            while (rset.next()) {
                Item item = new Item();
                item.setId(rset.getInt("id"));
                item.setNome(rset.getString("nome"));
                item.setQuantidade(rset.getInt("quantidade"));
                item.setDataCadastro(rset.getDate("dataCadastro"));
                itens.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar itens: " + e.getMessage());
            e.printStackTrace();
        }
        return itens;
    }
    
    // Método que verifica se o item existe e retorna o objeto, ou null se não existir.
    public Item getItemByID(int id) throws Exception {
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
             
            pstm.setInt(1, id);
            
            try (ResultSet rset = pstm.executeQuery()) {
                if (rset.next()) {
                    Item item = new Item();
                    item.setId(rset.getInt("id"));
                    item.setNome(rset.getString("nome"));
                    item.setQuantidade(rset.getInt("quantidade"));
                    item.setDataCadastro(rset.getDate("dataCadastro"));
                    return item;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar item: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}