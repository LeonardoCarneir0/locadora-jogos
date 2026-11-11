package locadora.servico;

import java.util.ArrayList;
import java.util.List;

import locadora.contrato.Contrato;
import locadora.modelo.Jogo;
import locadora.nucleo.Aluguel;
import locadora.observador.ObservadorLocadora;

/**
 * Real Subject do padrão Proxy e Subject do padrão Observer.
 *
 * - Proxy: executa o aluguel de verdade; o acesso a ela passa pelo
 *   {@link LocadoraProxy}.
 * - Observer: mantém uma lista de {@link ObservadorLocadora} e notifica
 *   todos quando o catálogo ou os aluguéis mudam.
 */
public class Locadora implements ServicoLocadora {

    private final List<Jogo> catalogo = new ArrayList<>();
    private final List<Aluguel> alugueis = new ArrayList<>();
    private final List<ObservadorLocadora> observadores = new ArrayList<>();

    // ---------------- Observer (Subject) ----------------

    public void adicionarObservador(ObservadorLocadora observador) {
        observadores.add(observador);
    }

    private void notificar() {
        for (ObservadorLocadora o : observadores) {
            o.atualizar();
        }
    }

    // ---------------- Operações que mudam dados ----------------

    public void adicionarJogo(Jogo jogo) {
        catalogo.add(jogo);
        notificar();
    }

    public List<Jogo> getCatalogo() {
        return catalogo;
    }

    public List<Aluguel> getAlugueis() {
        return alugueis;
    }

    @Override
    public Aluguel alugar(String cliente, int idade, Jogo jogo, int dias, Contrato contrato) {
        Aluguel aluguel = new Aluguel(cliente, jogo, dias, contrato);
        alugueis.add(aluguel);
        notificar();
        return aluguel;
    }

    /** Ação de State delegada ao aluguel, seguida de notificação. */
    public void atrasar(Aluguel aluguel) {
        aluguel.atrasar();
        notificar();
    }

    /** Ação de State delegada ao aluguel, seguida de notificação. */
    public void devolver(Aluguel aluguel) {
        aluguel.devolver();
        notificar();
    }
}
