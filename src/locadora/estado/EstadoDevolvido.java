package locadora.estado;

import locadora.nucleo.Aluguel;

/**
 * Estado "DEVOLVIDO": estado final do ciclo de vida do aluguel.
 */
public class EstadoDevolvido implements EstadoAluguel {

    @Override
    public void devolver(Aluguel aluguel) {
        System.out.println("   [State] Este aluguel já foi devolvido.");
    }

    @Override
    public void atrasar(Aluguel aluguel, int diasAtraso) {
        System.out.println("   [State] Não é possível atrasar um aluguel devolvido.");
    }

    @Override
    public String getNome() {
        return "DEVOLVIDO";
    }
}
