
package locadora.servico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import locadora.contrato.Contrato;
import locadora.modelo.Jogo;
import locadora.modelo.JogoFisico;
import locadora.nucleo.Aluguel;

/**
 * Real Subject do padrão Proxy.
 *
 * É a locadora de verdade: guarda o catálogo e o histórico de aluguéis e
 * executa o aluguel de fato. O acesso a ela passa pelo LocadoraProxy.
 */
public class Locadora implements ServicoLocadora {

    private final List<Jogo> catalogo = new ArrayList<>();
    private final List<Aluguel> alugueis = new ArrayList<>();

    public void adicionarJogo(Jogo jogo) {
        if (jogo == null) {
            throw new IllegalArgumentException(
                "Jogo não pode ser nulo."
            );
        }

        catalogo.add(jogo);
    }

    public List<Jogo> getCatalogo() {
        return Collections.unmodifiableList(catalogo);
    }

    public List<Aluguel> getAlugueis() {
        return Collections.unmodifiableList(alugueis);
    }

    @Override
    public Aluguel alugar(
            String cliente,
            int idade,
            Jogo jogo,
            int dias,
            Contrato contrato) {

        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Nome do cliente obrigatório."
            );
        }

        if (idade < 0 || dias <= 0) {
            throw new IllegalArgumentException(
                "Idade ou período de aluguel inválido."
            );
        }

        if (jogo == null || contrato == null) {
            throw new IllegalArgumentException(
                "Jogo e contrato são obrigatórios."
            );
        }

        if (!catalogo.contains(jogo)) {
            throw new IllegalArgumentException(
                "Jogo não encontrado no catálogo."
            );
        }

        // Controle de estoque dos jogos físicos.
        if (jogo instanceof JogoFisico) {
            JogoFisico fisico = (JogoFisico) jogo;

            if (!fisico.temCopiaDisponivel()) {
                throw new IllegalStateException(
                    "Não existem cópias disponíveis."
                );
            }

            fisico.retirarCopia();
        }

        Aluguel aluguel = new Aluguel(
            cliente, jogo, dias, contrato
        );

        alugueis.add(aluguel);

        return aluguel;
    }

    /**
     * Realiza a devolução e devolve a cópia ao estoque,
     * caso seja um jogo físico.
     */
    public void devolverAluguel(Aluguel aluguel) {

        if (aluguel == null || !alugueis.contains(aluguel)) {
            throw new IllegalArgumentException(
                "Aluguel não encontrado."
            );
        }

        if ("DEVOLVIDO".equals(aluguel.getEstado())) {
            System.out.println(
                "Este aluguel já foi devolvido."
            );
            return;
        }

        aluguel.devolver();

        if ("DEVOLVIDO".equals(aluguel.getEstado())
                && aluguel.getJogo() instanceof JogoFisico) {

            JogoFisico fisico =
                (JogoFisico) aluguel.getJogo();

            fisico.devolverCopia();
        }
    }
}
