package gr.aueb.cf.view;

import java.util.List;
import java.util.Scanner;

import gr.aueb.cf.model.Account;
import gr.aueb.cf.model.Transaction;

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
        System.out.println("5. Estado da Conta");
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

    public void printAccountStatement(Account account) {
        System.out.println("========================================");
        System.out.println("EXTRATO DA CONTA");
        System.out.println("========================================");
        System.out.println("IBAN: " + account.getIban());
        System.out.println("Titular: " + account.getHolder().getFirstName() + " " + account.getHolder().getLastName());
        System.out.println("CPF: " + account.getHolder().getSsn());
        System.out.println("Status: " + (account.isActive() ? "ATIVA" : "ENCERRADA"));
        System.out.println(String.format("Saldo Atual: %.2f", account.getBalance()));
        System.out.println(String.format("Saldo do Empréstimo: %.2f", account.getLoanBalance()));
        System.out.println(String.format("Limite de Crédito: %.2f", account.getCreditLimit()));
        System.out.println("========================================");
        System.out.println("HISTÓRICO DE TRANSAÇÕES");
        System.out.println("========================================");
        
        List<Transaction> history = account.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("Nenhuma transação registrada.");
        } else {
            for (Transaction transaction : history) {
                System.out.println(transaction.toString());
            }
        }
        
        System.out.println("========================================");
        System.out.println("Total de Transações: " + history.size());
        System.out.println("========================================");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String error) {
        System.out.println("ERRO: " + error);
    }
}