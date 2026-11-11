package locadora.servico;

import locadora.contrato.Contrato;
import locadora.modelo.Jogo;
import locadora.nucleo.Aluguel;

/**
 * Proxy do padrão Proxy (controle de acesso).
 *
 * Antes de liberar o aluguel, verifica se o jogo é +18 e se o cliente tem
 * idade suficiente. Só então delega ao serviço real ({@link Locadora}).
 */
public class LocadoraProxy implements ServicoLocadora {

    private final Locadora locadora;

    public LocadoraProxy(Locadora locadora) {
        this.locadora = locadora;
    }

    @Override
    public Aluguel alugar(String cliente, int idade, Jogo jogo, int dias, Contrato contrato) {
        if (jogo.isAdulto() && idade < 18) {
            System.out.printf("   [Proxy] BLOQUEADO: '%s' é +18 e %s tem %d anos.%n",
                    jogo.getTitulo(), cliente, idade);
            return null;
        }
        System.out.printf("   [Proxy] Acesso liberado para '%s'.%n", jogo.getTitulo());
        return locadora.alugar(cliente, idade, jogo, dias, contrato);
    }
}
