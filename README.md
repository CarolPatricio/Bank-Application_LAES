# Sistema Bancário Simples em Java

Este repositório contém uma simulação básica de um sistema bancário implementado em Java. O projeto demonstra os princípios da Programação Orientada a Objetos (POO) e segue estritamente o padrão arquitetural **Model-View-Controller (MVC)**. 

Além disso, o modelo de domínio principal utiliza a **Java Modeling Language (JML)** para especificar formalmente o comportamento dos componentes do software.

## Arquitetura (MVC)

O sistema está devidamente desacoplado em três camadas principais para garantir uma separação clara de responsabilidades:

* **Model (`br.ufrn.imd.banco.new_models`):** Contém a lógica de negócio central, as entidades e a validação de estado. O modelo está escrito em português (`Conta`, `Usuario`, `Transacao`) e é fortemente anotado com contratos JML (pré-condições, pós-condições, invariantes de classe e restrições de modificação de estado) para garantir a correção matemática e das regras de negócio.
* **View (`br.ufrn.imd.banco.view`):** Contém a `ConsoleView`, que é a única camada responsável pela interação com o usuário. Ela lida com a leitura de entradas do scanner, exibição de menus e formatação de saídas visuais (como o extrato da conta) para a interface de linha de comando.
* **Controller (`br.ufrn.imd.banco.controller`):** Contém o `BankController`, que atua como orquestrador. Ele gerencia um "banco de dados" em memória contendo as contas, escuta as escolhas do usuário vindas da View e invoca as operações apropriadas no Model.

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
`br.ufrn.imd.banco.Main`

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
~/openjml/openjml --esc --dir src/br/ufrn/imd/banco/model/
```

#### Windows:
```bash
C:\openjml\openjml.bat --esc --dir src\br\ufrn\imd\banco\model\
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
javac -d bin -sourcepath src src/br/ufrn/imd/banco/Main.java

# 3. Injete as verificações do JML apenas nos modelos
~/openjml/openjml -rac -d bin --dir src/br/ufrn/imd/banco/model/

# 4. Execute a aplicação vinculando a biblioteca JML
java -cp bin:~/openjml/jmlruntime.jar br.ufrn.imd.banco.Main
```

#### Windows:
```bash
# 1. Crie o diretório de saída
mkdir bin

# 2. Compile o projeto inteiro normalmente
javac -d bin -sourcepath src src\br\ufrn\imd\banco\Main.java

# 3. Injete as verificações do JML apenas nos modelos
C:\openjml\openjml.bat -rac -d bin --dir src\br\ufrn\imd\banco\model\

# 4. Execute a aplicação vinculando a biblioteca JML
java -cp "bin;C:\openjml\jmlruntime.jar" br.ufrn.imd.banco.Main
```
Com o RAC ativo, qualquer violação de regras (como tentar depositar um valor negativo) fará com que a aplicação lance automaticamente um JmlAssertionError, protegendo a integridade do sistema em tempo real.

### 4. Geração e Execução do arquivo JAR executável
Se desejar empacotar a aplicação inteira compilada (já contendo as asserções do OpenJML injetadas nos modelos), você pode exportá-la para um arquivo `.jar`.

Execute os passos a partir da raiz do projeto:

#### No Linux:
```bash
# 1. Crie o arquivo de manifesto apontando para a classe Main (deixe uma linha em branco no final)
echo "Main-Class: br.ufrn.imd.banco.Main" > manifest.txt
echo "" >> manifest.txt

# 2. Gere o arquivo JAR a partir da pasta de binários compilados
jar cvfm banco.jar manifest.txt -C bin/ .

# 3. Remova o manifesto temporário
rm manifest.txt

# 4. Execute o JAR vinculando o runtime do JML necessário para os modelos
java -cp banco.jar:~/openjml/jmlruntime.jar br.ufrn.imd.banco.Main
```

#### No Windows (Prompt de Comando):
```DOS
:: 1. Crie o arquivo de manifesto (certifique-se de salvar com uma linha em branco no final)
echo Main-Class: br.ufrn.imd.banco.Main> manifest.txt
echo.>> manifest.txt

:: 2. Gere o arquivo JAR a partir da pasta de binários compilados
jar cvfm banco.jar manifest.txt -C bin/ .

:: 3. Remova o manifesto temporário
del manifest.txt

:: 4. Execute o JAR vinculando o runtime do JML necessário para os modelos
java -cp "banco.jar;C:\openjml\jmlruntime.jar" br.ufrn.imd.banco.Main
```