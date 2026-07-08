# Sistema Bancário Simples em Java

Este repositório contém uma simulação básica de um sistema bancário implementado em Java. O projeto demonstra os princípios da Programação Orientada a Objetos (POO) e segue estritamente o padrão arquitetural **Model-View-Controller (MVC)**. 

Além disso, o modelo de domínio principal utiliza a **Java Modeling Language (JML)** para especificar formalmente o comportamento dos componentes do software.

## Arquitetura (MVC)

O sistema está devidamente desacoplado em três camadas principais para garantir uma separação clara de responsabilidades:

* **Model (`gr.aueb.cf.new_models`):** Contém a lógica de negócio central, as entidades e a validação de estado. O modelo está escrito em português (`Conta`, `Usuario`, `Transacao`) e é fortemente anotado com contratos JML (pré-condições, pós-condições, invariantes de classe e restrições de modificação de estado) para garantir a correção matemática e das regras de negócio.
* **View (`gr.aueb.cf.view`):** Contém a `ConsoleView`, que é a única camada responsável pela interação com o usuário. Ela lida com a leitura de entradas do scanner, exibição de menus e formatação de saídas visuais (como o extrato da conta) para a interface de linha de comando.
* **Controller (`gr.aueb.cf.controller`):** Contém o `BankController`, que atua como orquestrador. Ele gerencia um "banco de dados" em memória contendo as contas, escuta as escolhas do usuário vindas da View e invoca as operações apropriadas no Model.

## Descrição e Funcionalidades

O sistema suporta a criação de contas e a execução de operações como depósitos, saques, verificação de saldo e geração de extratos. Ele inclui validações para vários problemas potenciais, como valores negativos, operações em contas encerradas e verificação de CPF.

### As principais classes incluídas no projeto são:

1. `Usuario`: Representa um titular de conta com propriedades como nome, sobrenome e CPF.
2. `Conta`: Representa uma conta bancária com operações fundamentais como depósito e saque.
3. `Transacao`: Representa uma operação financeira registrada no histórico de uma conta.
4. `EntidadeIdentificavel`: Uma classe base que fornece um identificador único (`id`) para as entidades de domínio.

### Tratamento de Exceções

Em vez de depender de exceções personalizadas, a nova arquitetura apoia-se em exceções nativas do Java para fazer cumprir os contratos JML e as regras de negócio:
* `IllegalArgumentException`: Lançada ao fornecer entradas inválidas (ex: valores de depósito negativos, CPF incorreto ou contas de destino nulas).
* `IllegalStateException`: Lançada ao tentar operações em um estado inválido (ex: tentar sacar mais do que o saldo disponível ou operar em uma conta encerrada).

## Instalação e Uso

O projeto pode ser executado usando qualquer IDE Java (como IntelliJ, Eclipse ou VS Code) ou a partir da linha de comando, compilando os arquivos Java e executando o ponto de entrada da aplicação.

Para iniciar o console bancário interativo, execute o método main localizado em:
`gr.aueb.cf.Main`