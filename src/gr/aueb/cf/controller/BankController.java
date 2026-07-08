package gr.aueb.cf.controller;

import gr.aueb.cf.model.Account;
import gr.aueb.cf.model.User;
import gr.aueb.cf.view.ConsoleView;
import gr.aueb.cf.exceptions.InsufficientAmountException;
import gr.aueb.cf.exceptions.InsufficientBalanceException;
import gr.aueb.cf.exceptions.SsnNotValidException;

import java.util.HashMap;
import java.util.Map;

public class BankController {

    private Map<String, Account> accounts = new HashMap<>();
    private ConsoleView view;
    private int ibanCounter = 1000;

    public BankController(ConsoleView view) {
        this.view = view;
    }

    public void start() {
        boolean running = true;
        
        while (running) {
            view.displayMainMenu();
            int choice = view.getUserChoice();

            try {
                switch (choice) {
                    case 1:
                        handleCreateAccount();
                        break;
                    case 2:
                        handleDeposit();
                        break;
                    case 3:
                        handleWithdraw();
                        break;
                    case 4:
                        handleCheckBalance();
                        break;
                    case 5:
                        handleStatement();
                        break;
                    case 0:
                        running = false;
                        view.displayMessage("Obrigado por usar nosso banco!");
                        break;
                    default:
                        view.displayError("Opção inválida. Por favor, tente novamente.");
                }
            } catch (Exception e) {
                view.displayError(e.getMessage());
            }
        }
    }

    private void handleCreateAccount() {
        view.displayMessage("\n--- Criar Nova Conta ---");
        String firstName = view.getInput("Digite o Nome: ");
        String lastName = view.getInput("Digite o Sobrenome: ");
        String ssn = view.getInput("Digite o CPF: ");
        double initialDeposit = view.getAmount("depósito inicial");
        
        User newUser = new User(firstName, lastName, ssn);
        String iban = "GR" + (ibanCounter++);
        Account newAccount = new Account(newUser, iban, initialDeposit);
        
        accounts.put(iban, newAccount);
        view.displayMessage("Conta criada com sucesso!");
        view.displayMessage("AVISO: Salve seu IBAN -> " + iban);
    }

    private void handleDeposit() throws InsufficientAmountException {
        Account acc = getAccountByIban();
        if (acc == null) return;

        double amount = view.getAmount("depósito");
        acc.deposit(amount);
        view.displayMessage("Depósito realizado com sucesso. Novo saldo: $" + acc.getBalance());
    }

    private void handleWithdraw() throws InsufficientBalanceException, InsufficientAmountException, SsnNotValidException {
        Account acc = getAccountByIban();
        if (acc == null) return;

        String ssn = view.getInput("Digite seu CPF para validação de segurança: ");
        double amount = view.getAmount("saque");

        acc.withdraw(amount, ssn);
        view.displayMessage("Saque realizado com sucesso. Novo saldo: $" + acc.getBalance());
    }

    private void handleCheckBalance() {
        Account acc = getAccountByIban();
        if (acc != null) {
            view.displayMessage("Saldo atual da conta " + acc.getIban() + ": $" + acc.getBalance());
        }
    }
    
    private void handleStatement() {
        Account acc = getAccountByIban();
        if (acc != null) {
            view.printAccountStatement(acc);
        }
    }

    private Account getAccountByIban() {
        String iban = view.getInput("Digite o IBAN da conta: ");
        Account acc = accounts.get(iban);
        
        if (acc == null) {
            view.displayError("Conta com IBAN '" + iban + "' não encontrada.");
        }
        return acc;
    }
}