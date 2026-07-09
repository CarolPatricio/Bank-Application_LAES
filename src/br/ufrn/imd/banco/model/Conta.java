package br.ufrn.imd.banco.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A classe {@code Conta} representa uma conta bancária essencial associada a um
 * usuário.
 * Oferece as operações fundamentais de depósito, saque e transferência entre
 * contas.
 * Esta classe estende {@code EntidadeIdentificavel} para herdar o id único.
 */
public class Conta extends EntidadeIdentificavel {

  // @ spec_public
  private /*@ non_null @*/ final Usuario titular;
  
  // @ spec_public
  private /*@ non_null @*/ final String iban;
  
  // @ spec_public
  private double saldo;
  
  // @ spec_public
  private boolean ativo;
  
  // @ spec_public
  private /*@ non_null @*/ final List<Transacao> historicoTransacoes;

  // @ public invariant saldo >= 0;

  // @ public invariant titular != null;
  // @ public invariant iban != null;
  // @ public invariant historicoTransacoes != null;
  // @ public invariant saldo >= 0;

  /**
   * Construtor para inicializar uma conta bancária ativa com um saldo inicial.
   *
   * @param titular      o usuário dono da conta
   * @param iban         o identificador internacional da conta
   * @param saldoInicial o saldo de abertura da conta
   */
  // @ requires titular != null;
  // @ requires iban != null;
  // @ requires saldoInicial >= 0;
  // @ ensures this.titular == titular;
  // @ ensures this.iban == iban;
  // @ ensures this.saldo == saldoInicial;
  // @ ensures this.ativo == true;
  public Conta(Usuario titular, String iban, double saldoInicial) {
    this.titular = titular;
    this.iban = iban;
    this.saldo = saldoInicial;
    this.ativo = true;
    this.historicoTransacoes = new ArrayList<>();

    if (saldoInicial > 0) {
      adicionarTransacao(Transacao.TipoTransacao.DEPOSITO, saldoInicial, saldoInicial);
    }
  }

  // Getters e Setters

  // @ ensures \result == titular;
  // @ pure
  public Usuario getTitular() {
    return titular;
  }

  // @ ensures \result == iban;
  // @ pure
  public String getIban() {
    return iban;
  }

  // @ ensures \result == saldo;
  // @ pure
  public double getSaldo() {
    return saldo;
  }

  // @ ensures \result == ativo;
  // @ pure
  public boolean isAtivo() {
    return ativo;
  }

  // @ pure
  public List<Transacao> getHistoricoTransacoes() {
    return new ArrayList<>(historicoTransacoes);
  }

  /**
   * Deposita um valor positivo na conta.
   *
   * @param valor o montante a ser depositado
   * @throws IllegalStateException    se a conta estiver fechada
   * @throws IllegalArgumentException se o valor for menor ou igual a zero
   */
  // @ public normal_behavior
  // @ requires ativo;
  // @ requires valor > 0;
  // @ assignable saldo, historicoTransacoes.*;
  // @ ensures saldo == \old(saldo) + valor;
  // @ also
  // @ public exceptional_behavior
  // @ requires !ativo;
  // @ assignable \nothing;
  // @ signals (IllegalStateException);
  // @ also
  // @ public exceptional_behavior
  // @ requires ativo && valor <= 0;
  // @ assignable \nothing;
  // @ signals (IllegalArgumentException);
  public void depositar(double valor) {
    if (!ativo) {
      throw new IllegalStateException("Não é possível operar em uma conta encerrada.");
    }
    if (valor <= 0) {
      throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
    }

    saldo += valor;
    adicionarTransacao(Transacao.TipoTransacao.DEPOSITO, valor, saldo);
  }

  /**
   * Saca um valor da conta caso haja saldo suficiente e o CPF informado seja
   * válido.
   *
   * @param valor o montante a ser retirado
   * @param cpf   o CPF para validação de segurança do titular
   * @throws IllegalStateException    se a conta estiver fechada ou se o saldo for
   *                                  insuficiente
   * @throws IllegalArgumentException se o valor for inválido ou o CPF for
   *                                  incorreto
   */
  // @ public normal_behavior
  // @ requires ativo;
  // @ requires valor > 0;
  // @ requires valor <= saldo;
  // @ requires cpf != null && titular.cpf.equals(cpf);
  // @ assignable saldo, historicoTransacoes.*;
  // @ ensures saldo == \old(saldo) - valor;
  // @ also
  // @ public exceptional_behavior
  // @ requires !ativo;
  // @ assignable \nothing;
  // @ signals (IllegalStateException);
  // @ also
  // @ public exceptional_behavior
  // @ requires ativo && (valor <= 0 || cpf == null || !titular.cpf.equals(cpf));
  // @ assignable \nothing;
  // @ signals (IllegalArgumentException);
  // @ also
  // @ public exceptional_behavior
  // @ requires ativo && valor > saldo && cpf != null && titular.cpf.equals(cpf);
  // @ assignable \nothing;
  // @ signals (IllegalStateException);
  public void sacar(double valor, String cpf) {
    if (!ativo) {
      throw new IllegalStateException("Não é possível operar em uma conta encerrada.");
    }
    if (valor <= 0) {
      throw new IllegalArgumentException("O valor do saque deve ser positivo.");
    }
    if (cpf == null || !titular.getCpf().equals(cpf)) {
      throw new IllegalArgumentException("CPF inválido ou não corresponde ao titular.");
    }
    if (valor > saldo) {
      throw new IllegalStateException("Saldo insuficiente.");
    }

    saldo -= valor;
    adicionarTransacao(Transacao.TipoTransacao.SAQUE, valor, saldo);
  }

  /**
   * Transfere um valor desta conta para uma conta de destino.
   *
   * @param valor        o montante a ser transferido
   * @param contaDestino a conta que receberá o dinheiro
   * @throws IllegalStateException    se esta conta ou a de destino estiverem
   *                                  fechadas, ou por saldo insuficiente
   * @throws IllegalArgumentException se o valor for inválido ou a conta de
   *                                  destino for nula/idêntica
   */
  // @ public normal_behavior
  // @ requires ativo;
  // @ requires valor > 0;
  // @ requires valor <= saldo;
  // @ requires contaDestino != null && contaDestino != this &&
  // contaDestino.ativo;
  // @ assignable saldo, historicoTransacoes.*, contaDestino.saldo, contaDestino.historicoTransacoes.*;
  // @ ensures saldo == \old(saldo) - valor;
  // @ ensures contaDestino.saldo == \old(contaDestino.saldo) + valor;
  public void transferir(double valor, Conta contaDestino) {
    if (!ativo || contaDestino == null || !contaDestino.isAtivo()) {
      throw new IllegalStateException("Operação inválida. Certifique-se de que ambas as contas estão ativas.");
    }
    if (this == contaDestino) {
      throw new IllegalArgumentException("Não é possível transferir para a própria conta.");
    }
    if (valor <= 0) {
      throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
    }
    if (valor > saldo) {
      throw new IllegalStateException("Saldo insuficiente para transferência.");
    }

    saldo -= valor;
    adicionarTransacao(Transacao.TipoTransacao.TRANSFERENCIA_SAIDA, valor, saldo);

    contaDestino.saldo += valor;
    contaDestino.adicionarTransacao(Transacao.TipoTransacao.TRANSFERENCIA_ENTRADA, valor, contaDestino.getSaldo());
  }

  /**
   * Encerra a conta bancária. Uma conta só pode ser encerrada se o saldo estiver
   * zerado.
   *
   * @throws IllegalStateException se a conta já estiver fechada ou possuir saldo
   *                               residual
   */
  // @ public normal_behavior
  // @ requires ativo;
  // @ requires saldo == 0;
  // @ assignable ativo;
  // @ ensures ativo == false;
  // @ also
  // @ public exceptional_behavior
  // @ requires !ativo || (ativo && saldo != 0);
  // @ assignable \nothing;
  // @ signals (IllegalStateException);
  public void encerrarConta() {
    if (!ativo) {
      throw new IllegalStateException("A conta já está encerrada.");
    }
    if (saldo != 0) {
      throw new IllegalStateException(
          "Não é possível encerrar uma conta com saldo diferente de zero. Retire o saldo primeiro.");
    }

    ativo = false;
  }

  /**
   * Registra uma nova transação no histórico da conta.
   */
  // @ public normal_behavior
  // @ requires type != null;
  // @ requires amount >= 0;
  // @ assignable historicoTransacoes.*;
  public void adicionarTransacao(Transacao.TipoTransacao type, double amount, double balanceAfter) {
    historicoTransacoes.add(new Transacao(type, amount, balanceAfter));
  }
}