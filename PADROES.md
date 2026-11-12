# 🧩 Classificação dos Padrões — Classe A e Classe B

Este documento mostra como o sistema **Locadora de Jogos** atende à regra do
projeto:

> **3.** Aplicação no projeto de **4 padrões** de forma completa:
> - **dois padrões da Classe A** — até 2 pontos
> - **dois padrões da Classe (A ou B)** — até 2 pontos

---

## ✅ O que o sistema utiliza

| Padrão | Classe | Categoria (GoF) | Pasta | Papel no sistema |
|--------|:------:|-----------------|-------|------------------|
| **Factory Method** | **A** | Criacional | `fabrica/` | Cria jogos físicos ou digitais |
| **State** | **A** | Comportamental | `estado/` | Ciclo de vida do aluguel |
| **Decorator** | **B** | Estrutural | `contrato/` | Adiciona extras ao aluguel |
| **Proxy** | **B** | Estrutural | `servico/` | Bloqueia jogos acima da idade do cliente |

---

## 🎯 Atendimento à regra

- **Classe A (obrigatórios):** `Factory Method` + `State` ✅
- **Classe A/B (livres):** `Decorator` + `Proxy` ✅

**Resultado:** **2 padrões da Classe A + 2 padrões da Classe B** → atende
**exatamente** ao que o projeto pede.

---

## 🔍 Justificativa de cada padrão

### 1. Factory Method — Classe A
- **Onde:** `FabricaJogo` (creator abstrato) + `FabricaJogoFisico` e
  `FabricaJogoDigital` (creators concretos).
- **Como funciona:** o método `fabricar(DadosJogo)` é implementado por cada
  fábrica, que decide qual subclasse de `Jogo` instanciar (`JogoFisico` ou
  `JogoDigital`).
- **Por que está "completo":** o código cliente (`Main`) não usa `new`
  diretamente para criar jogos; para criar um novo tipo basta adicionar uma
  nova fábrica, **sem alterar o código existente**.

### 2. State — Classe A
- **Onde:** `EstadoAluguel` (interface) + `EstadoAlugado`, `EstadoAtrasado` e
  `EstadoDevolvido` (estados concretos). Contexto: `Aluguel`.
- **Como funciona:** as ações `devolver()` e `atrasar()` mudam de comportamento
  conforme o estado atual. O próprio estado troca o estado do aluguel.
- **Por que está "completo":** o ciclo inteiro é modelado:
  `ALUGADO → ATRASADO → DEVOLVIDO`, com multa na devolução atrasada.

### 3. Decorator — Classe B
- **Onde:** `Contrato` (interface) + `ContratoBasico` (componente) +
  `DecoradorContrato` (abstrato) + `ExtraSegundoControle`, `ExtraSeguro` e
  `ExtraEntrega`.
- **Como funciona:** cada extra "embrulha" o contrato anterior e soma seu valor
  ao preço, permitindo combinar vários extras em cadeia.
- **Por que está "completo":** o preço final é montado em tempo de execução,
  sem alterar a classe `Aluguel`.

### 4. Proxy — Classe B
- **Onde:** `ServicoLocadora` (interface) + `Locadora` (Real Subject) +
  `LocadoraProxy` (Proxy).
- **Como funciona:** o Proxy intercepta a chamada `alugar(...)`, compara a
  idade do cliente com a **classificação indicativa** do jogo e só delega ao
  serviço real se a idade for suficiente (ex.: jogo 10+ exige 10 anos ou mais;
  jogo 18+ exige 18 anos ou mais).
- **Por que está "completo":** é um Proxy de **controle de acesso**, com a
  mesma interface do serviço real.

---

## 📊 Resumo para a apresentação

| Requisito | Situação |
|-----------|----------|
| 2 padrões da Classe A | ✅ Factory Method + State |
| 2 padrões da Classe A ou B | ✅ Decorator + Proxy |
| Padrões completos | ✅ 4 padrões implementados |
