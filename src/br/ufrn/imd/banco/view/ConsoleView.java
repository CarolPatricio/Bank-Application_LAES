package br.ufrn.imd.banco.view;

import java.util.List;
import java.util.Scanner;

import br.ufrn.imd.banco.model.Conta;
import br.ufrn.imd.banco.model.Transacao;

public class ConsoleView {
    private Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMainMenu() {
        System.out.println("\n=== SISTEMA BANCÁRIO ===");
        System.out.println("1. Abrir Conta");
        System.out.println("2. Depósito");
        System.out.println("3. Saque");
        System.out.println("4. Ver Saldo");
        System.out.println("5. Extrato da Conta");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public int getUserChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public double getAmount(String operation) {
        System.out.print("Insira um valor para " + operation + ": ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 
        return amount;
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void printAccountStatement(Conta conta) {
        System.out.println("========================================");
        System.out.println("EXTRATO DA CONTA");
        System.out.println("========================================");
        System.out.println("IBAN: " + conta.getIban());
        System.out.println("Titular: " + conta.getTitular().getPrimeiroNome() + " " + conta.getTitular().getSobrenome());
        System.out.println("CPF: " + conta.getTitular().getCpf());
        System.out.println("Status: " + (conta.isAtivo() ? "ATIVA" : "ENCERRADA"));
        System.out.println(String.format("Saldo Atual: R$ %.2f", conta.getSaldo()));
        System.out.println("========================================");
        System.out.println("HISTÓRICO DE TRANSAÇÕES");
        System.out.println("========================================");
        
        List<Transacao> historico = conta.getHistoricoTransacoes();
        if (historico.isEmpty()) {
            System.out.println("Nenhuma transação registrada.");
        } else {
            for (Transacao transacao : historico) {
                System.out.println(transacao.toString());
            }
        }
        
        System.out.println("========================================");
        System.out.println("Total de Transações: " + historico.size());
        System.out.println("========================================");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String error) {
        System.out.println("ERRO: " + error);
    }
}