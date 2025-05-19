package br.com.estoque.application;

import java.text.SimpleDateFormat;
import java.util.Scanner;

import br.com.estoque.service.ItemService;
import br.com.model.Item;

public class Main {
    public static void main(String[] args) {
        ItemService itemService = new ItemService(); // Agora usamos o serviço para intermediar operações
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

            if (!sc.hasNextInt()) {
                System.out.println("Entrada inválida! Digite um número entre 1 e 5.");
                sc.nextLine();
                continue;
            }

            int resposta = sc.nextInt();
            sc.nextLine();
            System.out.println();

            try {
                switch (resposta) {
                    case 1:
                        System.out.print("Nome do Item: ");
                        String nome = sc.nextLine();

                        System.out.print("Quantidade: ");
                        if (!sc.hasNextInt()) {
                            System.out.println("Entrada inválida! A quantidade deve ser um número inteiro.");
                            sc.nextLine();
                            continue;
                        }
                        int quantidade = sc.nextInt();
                        sc.nextLine();

                        itemService.salvarItem(nome, quantidade);
                        break;

                    case 2:
                        System.out.print("Informe o ID do item a ser removido: ");
                        if (!sc.hasNextInt()) {
                            System.out.println("Entrada inválida! O ID deve ser um número inteiro.");
                            sc.nextLine();
                            continue;
                        }
                        int idRemover = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Você realmente deseja excluir o item de ID " + idRemover + "? (S/N): ");
                        String confirmacao = sc.nextLine();
                        if (confirmacao.equalsIgnoreCase("S")) {
                            itemService.excluirItem(idRemover);
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

                        Item itemExistente = itemService.buscarItemPorID(idAtualizar);
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

                        itemService.atualizarItem(idAtualizar, novoNome, novaQuantidade);
                        break;

                    case 4:
                        System.out.println("[ Itens no Armazém ]");
                        for (Item item : itemService.listarItens()) {
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
                sc.nextLine();
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