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

        // Verifica se o cliente foi informado
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }

        // Verifica se o jogo foi informado
        if (jogo == null) {
            throw new IllegalArgumentException("O jogo não pode ser nulo.");
        }

        // Verifica se a idade é válida
        if (idade < 0) {
            throw new IllegalArgumentException("A idade não pode ser negativa.");
        }

        // Verifica se a quantidade de dias é válida
        if (dias <= 0) {
            throw new IllegalArgumentException("A quantidade de dias deve ser maior que zero.");
        }

        // Verifica se o contrato foi informado
        if (contrato == null) {
            throw new IllegalArgumentException("O contrato não pode ser nulo.");
        }

        // Verifica a classificação indicativa
        if (idade < jogo.getClassificacaoIndicativa()) {
            System.out.printf("   [Proxy] BLOQUEADO: '%s' é %d+ e %s tem %d anos.%n",
                    jogo.getTitulo(), jogo.getClassificacaoIndicativa(), cliente, idade);
            return null;
        }

        System.out.printf("   [Proxy] Acesso liberado para '%s'.%n", jogo.getTitulo());

        // Delega o aluguel para a locadora real
        return locadora.alugar(cliente, idade, jogo, dias, contrato);
    }
}