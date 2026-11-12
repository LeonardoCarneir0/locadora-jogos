# 🎮 Locadora de Jogos

> Sistema de locadora de jogos em **modo texto (console)** que aplica
> **4 padrões de projeto** de forma completa: **2 da Classe A + 2 da Classe B**.

![Java](https://img.shields.io/badge/Java-25-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Console](https://img.shields.io/badge/UI-console-lightgrey?style=flat)
![Padrões](https://img.shields.io/badge/padrões-4-blueviolet?style=flat)
![Status](https://img.shields.io/badge/status-concluído-success?style=flat)

Projeto da disciplina **Programação III** — um sistema real de aluguel de jogos
com cadastro, aluguel com extras, bloqueio de jogos +18 e controle do ciclo de
vida do aluguel.

---

## 🚀 Como executar

**Pré-requisito:** JDK 11+ (o `run.sh` localiza o Java automaticamente).

```bash
./run.sh
```

> ou, manualmente:
> ```bash
> javac -d out $(find src -name '*.java')
> java -cp out locadora.Main
> ```

> 💡 No menu: **0** mostra as opções novamente e **9** encerra o programa.

---

## 🧩 Padrões de projeto (2 da Classe A + 2 da Classe B)

| # | Padrão | Classe | Categoria | Pasta | O que faz |
|---|--------|:------:|-----------|-------|-----------|
| 1 | **Factory Method** | **A** | Criacional | `fabrica/` | Cria jogos físicos/digitais |
| 2 | **State** | **A** | Comportamental | `estado/` | Ciclo de vida do aluguel |
| 3 | **Decorator** | **B** | Estrutural | `contrato/` | Adiciona extras ao aluguel |
| 4 | **Proxy** | **B** | Estrutural | `servico/` | Bloqueia jogos acima da idade do cliente |

> 📄 A justificativa detalhada de cada padrão está em **[PADROES.md](PADROES.md)**.

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

**3. Decorator** — `Contrato` define `calcularPreco()` e `getDescricao()`.
`ContratoBasico` é o aluguel puro; `DecoradorContrato` (abstrato) embrulha um
`Contrato`; os extras (`ExtraSegundoControle`, `ExtraSeguro`, `ExtraEntrega`)
somam valores e podem ser **combinados em cadeia**.

**4. Proxy** — `ServicoLocadora` define `alugar(...)`. `Locadora` (Real
Subject) executa o aluguel; `LocadoraProxy` compara a **idade** do cliente
com a **classificação indicativa** do jogo e bloqueia se o cliente for mais
novo (ex.: jogo 10+ para uma criança de 6 anos, ou jogo 18+ para um jovem de
15 anos).

---

## 📁 Estrutura do projeto

```
locadora-jogos/
├── run.sh                       # compila e executa o sistema
├── README.md
├── PADROES.md                   # classificação e justificativa dos padrões
└── src/locadora/
    ├── Main.java                # menu em console
    ├── modelo/                  # produtos (Factory Method)
    ├── fabrica/                 # creators (Factory Method)
    ├── estado/                  # State
    ├── contrato/                # Decorator
    ├── servico/                 # Proxy
    └── nucleo/Aluguel.java      # contexto (State) + cliente (Decorator)
```

---

## 🎬 Roteiro de demonstração

1. **Factory Method** — opção `1`: cadastre um jogo físico e um digital.
2. **Proxy** — opção `3`: tente alugar `GTA VI` (18+) para um cliente de
   15 anos, ou `Minecraft` (10+) para uma criança de 6 anos → o sistema
   **bloqueia**.
3. **Decorator** — opção `3`: alugue um jogo escolhendo extras (segundo
   controle, seguro, entrega) → o preço soma os extras.
4. **State** — opções `5` e `6`: simule atraso e depois devolva → repare na
   multa (R$ 5,00/dia) e na mudança de estado.

---

## 📚 Referências

- [Refactoring Guru — Catálogo de padrões](https://refactoring.guru/pt-br/design-patterns/catalog)

---

Feito com ☕ e Java por *[Leonardo](https://github.com/LeonardoCarneir0)*.
