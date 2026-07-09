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

## Verificação Formal com OpenJML

Este projeto utiliza o OpenJML para garantir que a lógica de negócios obedeça aos contratos matemáticos. Siga os passos abaixo para baixar a ferramenta e executar os testes na camada `Model`.

### 1. Download do OpenJML
1. Acesse a página de releases no GitHub: https://github.com/OpenJML/OpenJML/releases/latest

2. Baixe o arquivo .zip com a marcação para o seu sistema operacional (ex: openjml-windows-21.x.xx.zip).

3. Extraia o conteúdo do .zip para uma pasta na raiz do seu disco, ou qualquer outra pasta de sua preferência. 

### 2. Teste Estático (ESC - Extended Static Checking)

A verificação estática prova matematicamente a exatidão das regras de negócio sem executar a aplicação. Para verificar os modelos, execute o seguinte comando no terminal, substituindo o caminho pelo local onde você extraiu o OpenJML:

#### Linux:
```bash
~/openjml/openjml --esc --dir src/gr/aueb/cf/model/
```

#### Windows:
```bash
C:\openjml\openjml.bat --esc --dir src\gr\aueb\cf\model\
```
(Se o comando terminar sem saída no terminal, significa que a lógica de negócio passou em todas as provas matemáticas do JML com 0 falhas).
### 3. Teste em Tempo de Execução (RAC - Runtime Assertion Checking)

Para testar a aplicação no console com as regras do JML ativas, é necessário compilar a aplicação inteira e depois injetar os testes especificamente nos Modelos (evitando avisos na View).

Execute os comandos abaixo na ordem:

#### Linux:
```bash
# 1. Crie o diretório de saída
mkdir -p bin

# 2. Compile o projeto inteiro normalmente
javac -d bin -sourcepath src src/gr/aueb/cf/Main.java

# 3. Injete as verificações do JML apenas nos modelos
~/openjml/openjml -rac -d bin --dir src/gr/aueb/cf/model/

# 4. Execute a aplicação vinculando a biblioteca JML
java -cp bin:~/openjml/jmlruntime.jar gr.aueb.cf.Main
```

#### Windows:
```bash
# 1. Crie o diretório de saída
mkdir bin

# 2. Compile o projeto inteiro normalmente
javac -d bin -sourcepath src src\gr\aueb\cf\Main.java

# 3. Injete as verificações do JML apenas nos modelos
C:\openjml\openjml.bat -rac -d bin --dir src\gr\aueb\cf\model\

# 4. Execute a aplicação vinculando a biblioteca JML
java -cp "bin;C:\openjml\jmlruntime.jar" gr.aueb.cf.Main
```
Com o RAC ativo, qualquer violação de regras (como tentar depositar um valor negativo) fará com que a aplicação lance automaticamente um JmlAssertionError, protegendo a integridade do sistema em tempo real.