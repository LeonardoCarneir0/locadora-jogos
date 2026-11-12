# 🎮 Locadora de Jogos

> Um sistema de locadora de jogos com **interface gráfica (Swing)** que aplica
> **padrões de projeto** de forma completa — de verdade, não só no papel.

![Java](https://img.shields.io/badge/Java-25-ED8B00?style=flat&logo=openjdk&logoColor=white)
![UI](https://img.shields.io/badge/UI-Swing-blue?style=flat)
![Padrões](https://img.shields.io/badge/padrões-5-blueviolet?style=flat)
![Status](https://img.shields.io/badge/status-concluído-success?style=flat)

Projeto da disciplina **Programação III** — um sistema real de aluguel de jogos
com **5 padrões de projeto (GoF)** e arquitetura **MVC + Observer**.

---

## ✨ Por que este projeto é legal?

| | |
|---|---|
| 🖥️ | **Interface gráfica** (Swing) e versão em **console** |
| 🧩 | **5 padrões de projeto** aplicados de forma natural, sem forçar |
| 🔄 | **Observer** — as telas se atualizam sozinhas, sem botão de "recarregar" |
| 🔞 | **Proxy** — bloqueia jogos +18 para menores de idade |
| 🧾 | **Decorator** — soma extras no aluguel em tempo de execução |
| 🔁 | **State** — o aluguel muda de comportamento conforme o estado |

---

## 🚀 Como executar

**Pré-requisito:** JDK 11+ (o `run.sh` localiza o Java automaticamente).

### Interface gráfica (recomendado)

```bash
./run.sh
```

> ou, manualmente:
> ```bash
> javac -d out $(find src -name '*.java')
> java -cp out locadora.ui.App
> ```

### Console (opcional)

```bash
java -cp out locadora.Main
```

---

## 🧩 Padrões de projeto aplicados

> 📄 A classificação detalhada (Classe A e Classe A/B) e a justificativa de
> cada padrão estão em **[PADROES.md](PADROES.md)**.

| # | Padrão | Categoria | Classe | Pasta | O que faz |
|---|--------|-----------|:------:|-------|-----------|
| 1 | **Factory Method** | Criacional | A | `fabrica/` | Cria jogos físicos/digitais |
| 2 | **State** | Comportamental | A | `estado/` | Ciclo de vida do aluguel |
| 3 | **Observer** | Comportamental | A | `observador/` | Atualiza as telas automaticamente |
| 4 | **Decorator** | Estrutural | B | `contrato/` | Adiciona extras ao aluguel |
| 5 | **Proxy** | Estrutural | B | `servico/` | Bloqueia jogos +18 para menores |

> ✅ Atende a regra da avaliação com folga: **3 da Classe A + 2 da Classe B**
> (o mínimo exigido é 2 da Classe A + 2 da Classe A/B).

### 🔁 Máquina de estados do aluguel (State)

```
ALUGADO ──(atrasar)──▶ ATRASADO ──(devolver)──▶ DEVOLVIDO
   │                       │
   └───────(devolver)──────┴──────▶ DEVOLVIDO
```

### Como cada padrão aparece

**1. Factory Method** — `FabricaJogo` (creator abstrato) define o método
`fabricar(DadosJogo)`. `FabricaJogoFisico` cria `JogoFisico` (com estoque de
cópias) e `FabricaJogoDigital` cria `JogoDigital` (com código de ativação).
Para adicionar um novo tipo de jogo, basta criar **uma nova fábrica**, sem
alterar o código existente.

**2. State** — a interface `EstadoAluguel` define `devolver()` e `atrasar()`.
`EstadoAlugado`, `EstadoAtrasado` e `EstadoDevolvido` implementam o
comportamento de cada estado. A classe `Aluguel` (contexto) delega as ações
ao estado atual — a devolução com atraso, por exemplo, cobra multa.

**3. Observer** — a `Locadora` é o **Subject**: guarda uma lista de
`ObservadorLocadora` e chama `atualizar()` em todos quando o catálogo ou os
aluguéis mudam. Cada painel da interface implementa a interface e se
redesenha sozinho.

**4. Decorator** — `Contrato` define `calcularPreco()` e `getDescricao()`.
`ContratoBasico` é o aluguel puro; `DecoradorContrato` (abstrato) embrulha um
`Contrato`; os extras (`ExtraSegundoControle`, `ExtraSeguro`, `ExtraEntrega`)
somam valores e podem ser **combinados em cadeia**.

**5. Proxy** — `ServicoLocadora` define `alugar(...)`. `Locadora` (Real
Subject) executa o aluguel; `LocadoraProxy` verifica a **idade** do cliente
antes de delegar e bloqueia jogos +18 para menores.

---

## 🏗️ Arquitetura — MVC

A interface segue o padrão **MVC** (arquitetural):

| Camada | Responsabilidade | Onde |
|--------|------------------|------|
| **Model** | Regras de negócio e padrões | `modelo/`, `fabrica/`, `estado/`, `contrato/`, `servico/` |
| **View** | Telas Swing | `ui/*Panel`, `MainWindow` |
| **Controller** | Ações do usuário | listeners de botão + `LocadoraProxy` |

---

## 📁 Estrutura do projeto

```
locadora-jogos/
├── run.sh                       # compila e abre a interface gráfica
├── README.md
└── src/locadora/
    ├── Main.java                # versão console (opcional)
    ├── modelo/                  # produtos (Factory Method)
    ├── fabrica/                 # creators (Factory Method)
    ├── estado/                  # State
    ├── observador/              # Observer (interface)
    ├── contrato/                # Decorator
    ├── servico/                 # Proxy (+ Subject do Observer)
    ├── nucleo/Aluguel.java      # contexto (State) + cliente (Decorator)
    └── ui/                      # interface gráfica (Swing + MVC)
```

---

## 🎬 Roteiro de demonstração

1. **Factory Method** — aba *Catálogo* → *Cadastrar jogo*: crie um jogo
   físico e um digital.
2. **Proxy** — aba *Alugar*: tente alugar `GTA VI` (+18) para um cliente de
   15 anos → o sistema **bloqueia**.
3. **Decorator** — aba *Alugar*: alugue um jogo marcando "segundo controle" e
   "seguro" → o preço soma os extras.
4. **State** — aba *Aluguéis*: selecione um aluguel e use *Simular atraso* e
   depois *Devolver* → repare na multa (R$ 5,00/dia).
5. **Observer** — repare que as três abas se atualizam **sozinhas** a cada
   operação, sem recarregar nada.

---

## 📚 Referências

- [Refactoring Guru — Catálogo de padrões](https://refactoring.guru/pt-br/design-patterns/catalog)

---

Feito com ☕ e Java por *[Leonardo](https://github.com/LeonardoCarneir0)*.
