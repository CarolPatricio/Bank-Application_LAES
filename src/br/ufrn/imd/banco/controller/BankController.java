package br.ufrn.imd.banco.controller;

import br.ufrn.imd.banco.model.Conta;
import br.ufrn.imd.banco.model.Usuario;
import br.ufrn.imd.banco.view.ConsoleView;

import java.util.HashMap;
import java.util.Map;

public class BankController {

    private Map<String, Conta> contas = new HashMap<>();
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
        String primeiroNome = view.getInput("Digite o Nome: ");
        String sobrenome = view.getInput("Digite o Sobrenome: ");
        String cpf = view.getInput("Digite o CPF: ");
        double depositoInicial = view.getAmount("depósito inicial");
        
        Usuario novoUsuario = new Usuario(primeiroNome, sobrenome, cpf);
        String iban = "GR" + (ibanCounter++);
        Conta novaConta = new Conta(novoUsuario, iban, depositoInicial);
        
        contas.put(iban, novaConta);
        view.displayMessage("Conta criada com sucesso!");
        view.displayMessage("AVISO: Salve seu IBAN para fazer operações -> " + iban);
    }

    private void handleDeposit() {
        Conta conta = getAccountByIban();
        if (conta == null) return;

        double amount = view.getAmount("depósito");
        conta.depositar(amount);
        view.displayMessage("Depósito realizado com sucesso. Novo saldo: R$ " + conta.getSaldo());
    }

    private void handleWithdraw() {
        Conta conta = getAccountByIban();
        if (conta == null) return;

        String cpf = view.getInput("Digite seu CPF para validação de segurança: ");
        double amount = view.getAmount("saque");

        conta.sacar(amount, cpf);
        view.displayMessage("Saque realizado com sucesso. Novo saldo: R$ " + conta.getSaldo());
    }

    private void handleCheckBalance() {
        Conta conta = getAccountByIban();
        if (conta != null) {
            view.displayMessage("Saldo atual da conta " + conta.getIban() + ": R$ " + conta.getSaldo());
        }
    }
    
    private void handleStatement() {
        Conta conta = getAccountByIban();
        if (conta != null) {
            view.printAccountStatement(conta);
        }
    }

    private Conta getAccountByIban() {
        String iban = view.getInput("Digite o IBAN da conta: ");
        Conta conta = contas.get(iban);
        
        if (conta == null) {
            view.displayError("Conta com IBAN '" + iban + "' não encontrada.");
        }
        return conta;
    }
}