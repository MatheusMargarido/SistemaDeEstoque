package br.com.estoque.application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import br.com.estoque.dao.ItemDAO;
import br.com.model.Item;

public class Main {

    public static void main(String[] args) {
        ItemDAO itemDao = new ItemDAO("items");
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        boolean continuar = true;

        while (continuar) {
            clearConsole();

            System.out.println("[  MENU  ]");
            System.out.println("1 - Inserir Item");
            System.out.println("2 - Deletar Item");
            System.out.println("3 - Atualizar Item");
            System.out.println("4 - Consultar Itens");
            System.out.println("5 - Encerrar");
            System.out.print("Resposta: ");

            if (!sc.hasNextInt()) {  // Verifica se a entrada é um número inteiro válido
                System.out.println("Entrada inválida! Por favor, digite um número entre 1 e 5.");
                sc.nextLine(); // Descarta entrada inválida
                continue; // Volta para o menu
            }

            int resposta = sc.nextInt();
            sc.nextLine(); // Consumir o newline deixado pelo nextInt()
            System.out.println();

            try {
                switch (resposta) {
                    case 1:
                        System.out.print("Nome do Item: ");
                        String nome = sc.nextLine();

                        System.out.print("Quantidade: ");
                        if (!sc.hasNextInt()) {  // Verifica se a entrada é um número válido
                            System.out.println("Entrada inválida! A quantidade deve ser um número inteiro.");
                            sc.nextLine();
                            continue;
                        }
                        int quantidade = sc.nextInt();
                        sc.nextLine();

                        Item novoItem = new Item();
                        novoItem.setNome(nome);
                        novoItem.setQuantidade(quantidade);
                        novoItem.setDataCadastro(new Date());
                        itemDao.save(novoItem);
                        break;

                    case 2:
                        System.out.print("Informe o ID do item a ser removido: ");
                        if (!sc.hasNextInt()) {  // Verifica se o ID é um número válido
                            System.out.println("Entrada inválida! O ID deve ser um número inteiro.");
                            sc.nextLine();
                            continue;
                        }
                        int idRemover = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Você realmente deseja excluir o item de ID " + idRemover + "? (S/N): ");
                        String confirmacao = sc.nextLine();
                        if (confirmacao.equalsIgnoreCase("S")) {
                            itemDao.deleteByID(idRemover);
                        } else {
                            System.out.println("Operação de exclusão cancelada.");
                        }
                        break;

                    case 3:
                        System.out.print("Informe o ID do item: ");
                        if (!sc.hasNextInt()) {
                            System.out.println("Entrada inválida! O ID deve ser um número inteiro.");
                            sc.nextLine();
                            continue;
                        }
                        int idAtualizar = sc.nextInt();
                        sc.nextLine();

                        Item itemExistente = itemDao.getItemByID(idAtualizar);
                        if (itemExistente == null) {
                            System.out.println("ID não encontrado. Operação cancelada.");
                            break;
                        }

                        System.out.print("Novo Nome: ");
                        String novoNome = sc.nextLine();

                        System.out.print("Nova Quantidade: ");
                        if (!sc.hasNextInt()) {
                            System.out.println("Entrada inválida! A quantidade deve ser um número inteiro.");
                            sc.nextLine();
                            continue;
                        }
                        int novaQuantidade = sc.nextInt();
                        sc.nextLine();

                        Item itemAtualizado = new Item();
                        itemAtualizado.setId(idAtualizar);
                        itemAtualizado.setNome(novoNome);
                        itemAtualizado.setQuantidade(novaQuantidade);
                        itemAtualizado.setDataCadastro(new Date());
                        itemDao.update(itemAtualizado);
                        break;

                    case 4:
                        System.out.println("[ Itens no Armazém ]");
                        for (Item item : itemDao.getItens()) {
                            System.out.printf("ID: %2d | Nome: %-15s | Quantidade: %-5d | Data de Cadastro: %s%n",
                                    item.getId(), item.getNome(), item.getQuantidade(), sdf.format(item.getDataCadastro()));
                        }
                        System.out.println();
                        break;

                    case 5:
                        continuar = false;
                        System.out.println("[ FIM DO PROGRAMA ]");
                        continue;
                        
                    default:
                        System.out.println("Opção inválida! Digite um número entre 1 e 5.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                sc.nextLine(); // Limpa o buffer caso ocorra erro
            }

            // Confirmação antes de continuar
            String respostaContinuar;
            do {
                System.out.print("Deseja continuar? (S/N): ");
                respostaContinuar = sc.nextLine().trim();
                if (!respostaContinuar.equalsIgnoreCase("S") && !respostaContinuar.equalsIgnoreCase("N")) {
                    System.out.println("Resposta inválida! Digite S para continuar ou N para sair.");
                }
            } while (!respostaContinuar.equalsIgnoreCase("S") && !respostaContinuar.equalsIgnoreCase("N"));

            if (!respostaContinuar.equalsIgnoreCase("S")) {
                continuar = false;
                System.out.println("[ FIM DO PROGRAMA ]");
            }
        }
        sc.close();
    }

    // Método para limpar o terminal
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
        }
    }
}