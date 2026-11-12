package locadora.servico;

import locadora.contrato.Contrato;
import locadora.modelo.Jogo;
import locadora.nucleo.Aluguel;

/**
 * Proxy do padrão Proxy (controle de acesso).
 *
 * Antes de liberar o aluguel, compara a idade do cliente com a classificação
 * indicativa do jogo (ex.: 10+, 12+, 18+). Se o cliente for mais novo que a
 * classificação, o aluguel é bloqueado. Só então delega ao serviço real
 * ({@link Locadora}).
 */
public class LocadoraProxy implements ServicoLocadora {

    private final Locadora locadora;

    public LocadoraProxy(Locadora locadora) {
        this.locadora = locadora;
    }

    @Override
    public Aluguel alugar(String cliente, int idade, Jogo jogo, int dias, Contrato contrato) {
        if (idade < jogo.getClassificacaoIndicativa()) {
            System.out.printf("   [Proxy] BLOQUEADO: '%s' é %d+ e %s tem %d anos.%n",
                    jogo.getTitulo(), jogo.getClassificacaoIndicativa(), cliente, idade);
            return null;
        }
        System.out.printf("   [Proxy] Acesso liberado para '%s'.%n", jogo.getTitulo());
        return locadora.alugar(cliente, idade, jogo, dias, contrato);
    }
}
