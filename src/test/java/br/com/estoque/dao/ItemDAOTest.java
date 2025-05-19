package br.com.estoque.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.factory.ConnectionFactory;
import br.com.model.Item;

public class ItemDAOTest {

    private ItemDAO itemDAO;
    // Nome da tabela exclusiva para testes
    private static final String TEST_TABLE = "itemTestes";

    @BeforeEach
    public void setup() throws Exception {
        // Cria a tabela "itemTestes" garantindo um ambiente limpo
        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             Statement stmt = conn.createStatement()) {

            // Remove a tabela TEST_TABLE se ela já existir para evitar conflitos
            stmt.execute("DROP TABLE IF EXISTS " + TEST_TABLE);

            // Cria a tabela com as colunas necessárias para os testes
            String sql = "CREATE TABLE " + TEST_TABLE + " ("
                       + "id INT AUTO_INCREMENT PRIMARY KEY, "
                       + "nome VARCHAR(255), "
                       + "quantidade INT, "
                       + "dataCadastro DATE"
                       + ")";
            stmt.execute(sql);
        }
        // Inicializa o DAO apontando para a tabela de testes
        itemDAO = new ItemDAO(TEST_TABLE);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Remove a tabela "itemTestes" após cada teste para limpar o ambiente
        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS " + TEST_TABLE);
        }
    }

    @Test
    public void testSaveItem() throws Exception {
        // Cria e salva um novo item na tabela de testes
        Item item = new Item();
        item.setNome("TesteItem");
        item.setQuantidade(10);
        item.setDataCadastro(new Date());
        itemDAO.save(item);

        // Recupera os itens salvos para validar a operação
        List<Item> itens = itemDAO.getItens();
        assertEquals(1, itens.size(), "Deve haver exatamente um item salvo");

        Item savedItem = itens.get(0);
        assertEquals("TesteItem", savedItem.getNome());
        assertEquals(10, savedItem.getQuantidade());
        assertNotNull(savedItem.getDataCadastro());
    }

    @Test
    public void testUpdateItem() throws Exception {
        // Insere um item inicial na tabela de testes
        Item item = new Item();
        item.setNome("Original");
        item.setQuantidade(5);
        item.setDataCadastro(new Date());
        itemDAO.save(item);

        // Recupera o item para obter o ID gerado
        List<Item> itens = itemDAO.getItens();
        assertFalse(itens.isEmpty(), "A lista de itens não deve estar vazia");
        Item savedItem = itens.get(0);

        // Atualiza os dados do item
        savedItem.setNome("Atualizado");
        savedItem.setQuantidade(20);
        savedItem.setDataCadastro(new Date());
        itemDAO.update(savedItem);

        // Valida se os dados foram atualizados corretamente
        List<Item> updatedItens = itemDAO.getItens();
        assertEquals(1, updatedItens.size());
        Item updatedItem = updatedItens.get(0);
        assertEquals("Atualizado", updatedItem.getNome());
        assertEquals(20, updatedItem.getQuantidade());
    }

    @Test
    public void testDeleteItem() throws Exception {
        // Insere um item que será deletado
        Item item = new Item();
        item.setNome("ParaDeletar");
        item.setQuantidade(7);
        item.setDataCadastro(new Date());
        itemDAO.save(item);

        // Recupera o item para obter seu ID e realiza a deleção
        List<Item> itens = itemDAO.getItens();
        assertFalse(itens.isEmpty(), "A lista de itens não deve estar vazia");
        Item savedItem = itens.get(0);
        int id = savedItem.getId();
        itemDAO.deleteByID(id);

        // Verifica se o item foi efetivamente removido
        List<Item> afterDeletion = itemDAO.getItens();
        assertTrue(afterDeletion.isEmpty(), "A lista de itens deve estar vazia após a remoção.");
    }

    @Test
    public void testGetItens() throws Exception {
        // Inicialmente, verifica que a lista de itens está vazia na tabela de testes
        List<Item> itens = itemDAO.getItens();
        assertNotNull(itens);
        assertTrue(itens.isEmpty(), "Inicialmente, a lista de itens deve estar vazia.");

        // Insere dois itens e verifica se ambos são recuperados corretamente
        Item item1 = new Item();
        item1.setNome("Item1");
        item1.setQuantidade(1);
        item1.setDataCadastro(new Date());
        itemDAO.save(item1);

        Item item2 = new Item();
        item2.setNome("Item2");
        item2.setQuantidade(2);
        item2.setDataCadastro(new Date());
        itemDAO.save(item2);

        List<Item> itensAfterInsert = itemDAO.getItens();
        assertEquals(2, itensAfterInsert.size(), "Deve haver exatamente dois itens salvos.");
    }

    @Test
    public void testDeleteNonExistingItem() throws Exception {
        // Tenta deletar um item inexistente, garantindo que nenhuma exceção seja lançada
        assertDoesNotThrow(() -> itemDAO.deleteByID(9999), "Não deve lançar exceção ao tentar deletar um item inexistente.");
    }
}