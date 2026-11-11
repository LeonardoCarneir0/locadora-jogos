package locadora.servico;

import locadora.contrato.Contrato;
import locadora.modelo.Jogo;
import locadora.nucleo.Aluguel;

/**
 * Interface do padrão Proxy.
 *
 * Tanto o serviço real ({@link Locadora}) quanto o proxy
 * ({@link LocadoraProxy}) implementam este mesmo contrato.
 */
public interface ServicoLocadora {

    /**
     * Realiza um aluguel.
     *
     * @return o aluguel criado, ou {@code null} se foi bloqueado.
     */
    Aluguel alugar(String cliente, int idade, Jogo jogo, int dias, Contrato contrato);
}
