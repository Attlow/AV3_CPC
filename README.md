
# Leitor de Palavras em Arquivos de Texto

## Descrição

Este projeto Java permite contar quantas vezes uma palavra aparece em grandes arquivos de texto. Ele possui duas implementações:

- Leitura Serial: Processa o arquivo linha por linha de forma sequencial.
- Leitura Paralela: Utiliza threads para otimizar a leitura e processamento dos arquivos, tornando o processo mais eficiente em arquivos grandes.

## Estrutura do Projeto

```
AV3_CPC
├── src
│   ├── Main.java               # Menu principal e ponto de entrada
│   ├── SerialReader.java       # Leitura sequencial
│   └── ParallelReader.java     # Leitura paralela utilizando threads
├── DonQuixote-388208.txt       # Arquivo de texto de exemplo
├── Dracula-165307.txt          # Arquivo de texto de exemplo
├── MobyDick-217452.txt         # Arquivo de texto de exemplo
├── .gitignore
└── README.md                   # (Este arquivo)
```

## Como Executar

### Pré-requisitos

- Java JDK 8 ou superior instalado
- IDE de sua preferência (IntelliJ, Eclipse, VSCode) ou terminal com javac e java

### Execução via terminal

1. Compile os arquivos:

```bash
javac -d bin src/*.java
```

2. Execute o programa:

```bash
cd bin
java Main
```

### Execução na IDE

- Abra o projeto na sua IDE favorita.
- Execute o arquivo Main.java.

## Funcionalidades

- Entrada interativa: o usuário informa a palavra que deseja contar.
- Escolha entre modo sequencial e paralelo.
- Processamento de arquivos de texto extensos.

## Arquivos de Exemplo

O projeto já inclui três livros de domínio público:

- Dom Quixote
- Drácula
- Moby Dick

## Tecnologias

- Java 8+
- Programação concorrente com Threads

## Licença

Este projeto é de uso acadêmico e educativo.
