package locadora.servico;

import java.util.ArrayList;
import java.util.List;

import locadora.contrato.Contrato;
import locadora.modelo.Jogo;
import locadora.nucleo.Aluguel;

/**
 * Real Subject do padrão Proxy.
 *
 * É a locadora de verdade: guarda o catálogo e o histórico de aluguéis e
 * executa o aluguel de fato. O acesso a ela passa pelo {@link LocadoraProxy}.
 */
public class Locadora implements ServicoLocadora {

    private final List<Jogo> catalogo = new ArrayList<>();
    private final List<Aluguel> alugueis = new ArrayList<>();

    public void adicionarJogo(Jogo jogo) {
        catalogo.add(jogo);
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
        return aluguel;
    }
}
