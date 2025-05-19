package br.com.estoque.service;

import br.com.estoque.dao.ItemDAO;
import br.com.model.Item;
import java.util.Date;
import java.util.List;

public class ItemService {
    private final ItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAO("items"); // Tabela no banco de dados
    }

    public void salvarItem(String nome, int quantidade) throws Exception {
        Item item = new Item();
        item.setNome(nome);
        item.setQuantidade(quantidade);
        item.setDataCadastro(new Date());
        itemDAO.save(item);
    }

    public void atualizarItem(int id, String nome, int quantidade) throws Exception {
        Item itemExistente = itemDAO.getItemByID(id);
        if (itemExistente != null) {
            itemExistente.setNome(nome);
            itemExistente.setQuantidade(quantidade);
            itemExistente.setDataCadastro(new Date());
            itemDAO.update(itemExistente);
        } else {
            System.out.println("Item não encontrado.");
        }
    }

    public void excluirItem(int id) throws Exception {
        itemDAO.deleteByID(id);
    }

    public List<Item> listarItens() throws Exception {
        return itemDAO.getItens();
    }

    public Item buscarItemPorID(int id) throws Exception {
        return itemDAO.getItemByID(id);
    }
}