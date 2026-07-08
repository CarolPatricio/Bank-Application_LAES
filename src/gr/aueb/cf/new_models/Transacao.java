package gr.aueb.cf.new_models;

/**
 * A classe {@code Transacao} representa uma operação financeira em uma conta.
 * Refatorada: Foco exclusivo nas operações essenciais (depósito, saque e
 * transferências)
 * para simplificar a verificação formal.
 */
public class Transacao {

  public enum TipoTransacao {
    DEPOSITO,
    SAQUE,
    TRANSFERENCIA_SAIDA,
    TRANSFERENCIA_ENTRADA
  }

  // @ spec_public nullable
  private TipoTransacao tipo;

  // @ spec_public
  private double valor;

  // @ spec_public
  private double saldoAposTransacao;

  // @ public invariant valor >= 0;

  /**
   * Construtor para registrar uma nova transação.
   * * @param tipo o tipo da transação
   * 
   * @param valor              o montante envolvido
   * @param saldoAposTransacao o saldo da conta após a operação
   */
  // @ requires valor >= 0;
  // @ ensures this.tipo == tipo;
  // @ ensures this.valor == valor;
  // @ ensures this.saldoAposTransacao == saldoAposTransacao;
  // @ pure
  public Transacao(TipoTransacao tipo, double valor, double saldoAposTransacao) {
    this.tipo = tipo;
    this.valor = valor;
    this.saldoAposTransacao = saldoAposTransacao;
  }

  // Getters

  // @ ensures \result == tipo;
  public /* @ pure nullable @ */ TipoTransacao getTipo() {
    return tipo;
  }

  // @ ensures \result == valor;
  public /* @ pure @ */ double getValor() {
    return valor;
  }

  // @ ensures \result == saldoAposTransacao;
  public /* @ pure @ */ double getSaldoAposTransacao() {
    return saldoAposTransacao;
  }

  /**
   * Retorna uma representação formatada da transação.
   *
   * @return string formatada da transação
   */
  // @ skipesc
  @Override
  public String toString() {
    String sinal = (tipo == TipoTransacao.DEPOSITO ||
        tipo == TipoTransacao.TRANSFERENCIA_ENTRADA) ? "+" : "-";

    String tipoStr = (tipo != null) ? tipo.name() : "DESCONHECIDO";

    return String.format("[%s] %s%.2f | Saldo: %.2f",
        tipoStr,
        sinal,
        valor,
        saldoAposTransacao);
  }
}