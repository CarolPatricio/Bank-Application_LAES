package gr.aueb.cf.model;

/**
 * A classe {@code EntidadeIdentificavel} fornece um modelo base
 * para qualquer objeto do sistema que necessite de um identificador único.
 * * Servirá como classe pai para as demais entidades do modelo.
 */
public class EntidadeIdentificavel {

  // @ spec_public
  private long id;

  // @ public invariant id >= 0;
  // @ public constraint id == \old(id);

  /**
   * Retorna o identificador único da entidade.
   *
   * @return o id da entidade
   */
  // @ ensures \result == id;
  // @ ensures \result >= 0;
  // @ pure
  public long getId() {
    return id;
  }
}