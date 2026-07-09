package gr.aueb.cf.model;

/**
 * A classe {@code Usuario} representa o titular de uma conta bancária.
 * Um usuário é identificado por seu CPF e possui nome e sobrenome.
 * Esta classe estende {@code EntidadeIdentificavel} para herdar o identificador
 * único (id).
 */
public class Usuario extends EntidadeIdentificavel {

  // @ spec_public
  private String primeiroNome;
  // @ spec_public
  private String sobrenome;
  // @ spec_public
  private String cpf;

  // @ public invariant primeiroNome != null;
  // @ public invariant sobrenome != null;
  // @ public invariant cpf != null;

  /**
   * Construtor completo para inicializar um usuário com nome, sobrenome e CPF.
   *
   * @param primeiroNome o primeiro nome do usuário
   * @param sobrenome    o sobrenome do usuário
   * @param cpf          o CPF do usuário
   */
  // @ requires primeiroNome != null && sobrenome != null && cpf != null;
  // @ requires !primeiroNome.isEmpty() && !sobrenome.isEmpty() && !cpf.isEmpty();
  // @ ensures this.primeiroNome == primeiroNome;
  // @ ensures this.sobrenome == sobrenome;
  // @ ensures this.cpf == cpf;
  public Usuario(String primeiroNome, String sobrenome, String cpf) {
    this.primeiroNome = primeiroNome;
    this.sobrenome = sobrenome;
    this.cpf = cpf;
  }

  /**
   * Construtor de cópia para criar uma nova instância a partir de outro usuário.
   * Útil para evitar compartilhamento indesejado de referências (aliasing).
   *
   * @param usuario o usuário a ser copiado
   */
  // @ requires usuario != null;
  // @ requires usuario.primeiroNome != null && usuario.sobrenome != null &&
  // usuario.cpf != null;
  // @ ensures this.primeiroNome == usuario.primeiroNome;
  // @ ensures this.sobrenome == usuario.sobrenome;
  // @ ensures this.cpf == usuario.cpf;
  public Usuario(Usuario usuario) {
    this.primeiroNome = usuario.primeiroNome;
    this.sobrenome = usuario.sobrenome;
    this.cpf = usuario.cpf;
  }

  // Getters e Setters

  // @ ensures \result != null;
  // @ ensures \result == primeiroNome;
  // @ pure
  public String getPrimeiroNome() {
    return primeiroNome;
  }

  // @ public normal_behavior
  // @ requires primeiroNome != null && !primeiroNome.isEmpty();
  // @ assignable this.primeiroNome;
  // @ ensures this.primeiroNome == primeiroNome;
  public void setPrimeiroNome(String primeiroNome) {
    this.primeiroNome = primeiroNome;
  }

  // @ ensures \result != null;
  // @ ensures \result == sobrenome;
  // @ pure
  public String getSobrenome() {
    return sobrenome;
  }

  // @ public normal_behavior
  // @ requires sobrenome != null && !sobrenome.isEmpty();
  // @ assignable this.sobrenome;
  // @ ensures this.sobrenome == sobrenome;
  public void setSobrenome(String sobrenome) {
    this.sobrenome = sobrenome;
  }

  // @ ensures \result != null;
  // @ ensures \result == cpf;
  // @ pure
  public String getCpf() {
    return cpf;
  }

  // @ public normal_behavior
  // @ requires cpf != null && !cpf.isEmpty();
  // @ assignable this.cpf;
  // @ ensures this.cpf == cpf;
  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  @Override
  public /*@ skipesc @*/ String toString() {
    return "Usuario{" +
        "primeiroNome='" + primeiroNome + '\'' +
        ", sobrenome='" + sobrenome + '\'' +
        ", cpf='" + cpf + '\'' +
        '}';
  }
}