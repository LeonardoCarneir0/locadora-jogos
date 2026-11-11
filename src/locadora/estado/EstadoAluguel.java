package locadora.estado;

import locadora.nucleo.Aluguel;

/**
 * Interface do padrão State.
 *
 * Cada estado do aluguel implementa o comportamento das ações
 * {@link #devolver(Aluguel)} e {@link #atrasar(Aluguel)}.
 */
public interface EstadoAluguel {

    void devolver(Aluguel aluguel);

    void atrasar(Aluguel aluguel);

    String getNome();
}
