package locadora;

import java.util.List;
import java.util.Scanner;

import locadora.contrato.Contrato;
import locadora.contrato.ContratoBasico;
import locadora.contrato.ExtraEntrega;
import locadora.contrato.ExtraSegundoControle;
import locadora.contrato.ExtraSeguro;
import locadora.fabrica.DadosJogo;
import locadora.fabrica.FabricaJogo;
import locadora.fabrica.FabricaJogoDigital;
import locadora.fabrica.FabricaJogoFisico;
import locadora.modelo.Jogo;
import locadora.nucleo.Aluguel;
import locadora.servico.Locadora;
import locadora.servico.LocadoraProxy;
import locadora.servico.ServicoLocadora;

/**
 * Aplicação em modo texto da Locadora de Jogos.
 *
 * Demonstra 4 padrões de projeto:
 *  - Factory Method (Classe A): criar jogos físicos/digitais
 *  - State            (Classe A): ciclo de vida do aluguel
 *  - Decorator        (Classe B): extras adicionados ao aluguel
 *  - Proxy            (Classe B): bloqueio por classificação indicativa
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Locadora locadora = new Locadora();
    private static final ServicoLocadora servico = new LocadoraProxy(locadora);

    public static void main(String[] args) {
        popularDadosIniciais();
        limparTela();
        exibirMenu();

        while (true) {
            int opcao = lerInteiro("Opção (0 = opções | 9 = sair): ");

            if (opcao == 9) {
                limparTela();
                System.out.println("Encerrando... Até a próxima!");
                break;
            }

            limparTela();
            switch (opcao) {
                case 0 -> exibirMenu();
                case 1 -> cadastrarJogo();
                case 2 -> listarJogos();
                case 3 -> alugarJogo();
                case 4 -> listarAlugueis();
                case 5 -> atrasarAluguel();
                case 6 -> devolverAluguel();
                default -> System.out.println("Opção inválida! Digite 0 para ver as opções.");
            }

            if (opcao != 0) {
                pausar();
                limparTela();
                exibirMenu();
            }
        }
    }

    /** Limpa a tela do terminal (Linux/macOS) ou do cmd (Windows). */
    private static void limparTela() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ignored) {
            // Se não for possível limpar, o programa continua normalmente.
        }
    }

    private static void pausar() {
        System.out.print("\nPressione Enter para voltar ao menu...");
        sc.nextLine();
    }

    private static void exibirMenu() {
        System.out.println("==================================================");
        System.out.println("                  LOCADORA DE JOGOS               ");
        System.out.println("==================================================");
        System.out.println("  1 - Cadastrar jogo       (Factory Method)");
        System.out.println("  2 - Listar jogos");
        System.out.println("  3 - Alugar jogo          (Proxy + Decorator)");
        System.out.println("  4 - Listar aluguéis");
        System.out.println("  5 - Simular atraso       (State)");
        System.out.println("  6 - Devolver jogo        (State)");
        System.out.println("  0 - Mostrar opções");
        System.out.println("  9 - Sair");
        System.out.println("==================================================");
    }

    private static void popularDadosIniciais() {
        FabricaJogo fisica = new FabricaJogoFisico();
        FabricaJogo digital = new FabricaJogoDigital();

        locadora.adicionarJogo(fisica.criarJogo(new DadosJogo("The Legend of Zelda", "Aventura", 12.0, 10, 3)));
        locadora.adicionarJogo(fisica.criarJogo(new DadosJogo("FIFA 26", "Esporte", 10.0, 0, 5)));
        locadora.adicionarJogo(digital.criarJogo(new DadosJogo("GTA VI", "Ação", 15.0, 18, 0)));
        locadora.adicionarJogo(digital.criarJogo(new DadosJogo("Minecraft", "Sandbox", 6.0, 10, 0)));
    }

    private static void cadastrarJogo() {
        System.out.println("--- CADASTRAR JOGO (Factory Method) ---");

        int tipo = lerInteiro("Tipo (1 - Físico | 2 - Digital): ");

        // Valida o tipo de jogo
        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo inválido! Escolha 1 para Físico ou 2 para Digital.");
            return;
        }

        String titulo = lerTexto("Título: ");
        String genero = lerTexto("Gênero: ");
        double preco = lerDouble("Preço base (R$/dia): ");
        int classificacao = lerInteiro("Classificação indicativa: ");

        FabricaJogo fabrica;
        int copias = 0;

        if (tipo == 1) {
            fabrica = new FabricaJogoFisico();
            copias = lerInteiro("Quantidade de cópias: ");
        } else {
            fabrica = new FabricaJogoDigital();
        }

        try {
            Jogo jogo = fabrica.criarJogo(
                new DadosJogo(titulo, genero, preco, classificacao, copias)
            );
            locadora.adicionarJogo(jogo);
            System.out.println("Jogo adicionado ao catálogo.");
        } catch (IllegalArgumentException e) {
            System.out.println("Não foi possível cadastrar: " + e.getMessage());
        }
    }

    private static void listarJogos() {
        System.out.println("--- CATÁLOGO ---");
        List<Jogo> catalogo = locadora.getCatalogo();
        if (catalogo.isEmpty()) {
            System.out.println("Nenhum jogo cadastrado.");
            return;
        }
        for (int i = 0; i < catalogo.size(); i++) {
            System.out.println((i + 1) + ". " + catalogo.get(i));
        }
    }

    private static void alugarJogo() {
        List<Jogo> catalogo = locadora.getCatalogo();
        if (catalogo.isEmpty()) {
            System.out.println("Cadastre um jogo antes de alugar.");
            return;
        }

        listarJogos();
        int idx = lerInteiro("Número do jogo: ") - 1;
        if (idx < 0 || idx >= catalogo.size()) {
            System.out.println("Jogo inválido.");
            return;
        }
        Jogo jogo = catalogo.get(idx);

        String cliente = lerTexto("Nome do cliente: ");
        int idade = lerInteiro("Idade do cliente: ");
        int dias = lerInteiro("Dias de aluguel: ");

        Contrato contrato = montarContrato(jogo, dias);

        try {
            Aluguel aluguel = servico.alugar(cliente, idade, jogo, dias, contrato);
            if (aluguel != null) {
                System.out.printf("Aluguel registrado! Total: R$ %.2f%n", aluguel.calcularPreco());
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível alugar: " + e.getMessage());
        }
    }

    private static Contrato montarContrato(Jogo jogo, int dias) {
        Contrato contrato = new ContratoBasico(jogo, dias);
        while (true) {
            System.out.println();
            System.out.println("Extras (Decorator) — opcional:");
            System.out.println(" 1 - Segundo controle      (+R$ 5,00)");
            System.out.println(" 2 - Seguro contra danos   (+R$ 3,00)");
            System.out.println(" 3 - Entrega em domicílio  (+R$ 10,00)");
            System.out.println(" 0 - Concluir aluguel");
            int opt = lerInteiro("Extra: ");
            switch (opt) {
                case 1 -> contrato = new ExtraSegundoControle(contrato);
                case 2 -> contrato = new ExtraSeguro(contrato);
                case 3 -> contrato = new ExtraEntrega(contrato);
                case 0 -> {
                    return contrato;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void listarAlugueis() {
        System.out.println("--- ALUGUÉIS ---");
        List<Aluguel> alugueis = locadora.getAlugueis();
        if (alugueis.isEmpty()) {
            System.out.println("Nenhum aluguel registrado.");
            return;
        }
        for (int i = 0; i < alugueis.size(); i++) {
            System.out.println((i + 1) + ". " + alugueis.get(i));
        }
    }

    private static void atrasarAluguel() {
        Aluguel aluguel = selecionarAluguel();

        if (aluguel != null) {
            int diasAtraso = lerInteiro(
                "Quantos dias de atraso? "
            );

            aluguel.atrasar(diasAtraso);
        }
    }

    private static void devolverAluguel() {
        Aluguel aluguel = selecionarAluguel();
        if (aluguel != null) {
            locadora.devolverAluguel(aluguel);
        }
    }

    private static Aluguel selecionarAluguel() {
        List<Aluguel> alugueis = locadora.getAlugueis();
        if (alugueis.isEmpty()) {
            System.out.println("Nenhum aluguel registrado.");
            return null;
        }
        listarAlugueis();
        int idx = lerInteiro("Número do aluguel: ") - 1;
        if (idx < 0 || idx >= alugueis.size()) {
            System.out.println("Aluguel inválido.");
            return null;
        }
        return alugueis.get(idx);
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine().trim();
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = sc.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = sc.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
