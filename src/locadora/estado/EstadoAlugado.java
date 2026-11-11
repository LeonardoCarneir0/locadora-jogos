package locadora.estado;

import locadora.nucleo.Aluguel;

/**
 * Estado "ALUGADO": o jogo está com o cliente, dentro do prazo.
 */
public class EstadoAlugado implements EstadoAluguel {

    @Override
    public void devolver(Aluguel aluguel) {
        aluguel.setEstado(new EstadoDevolvido());
        System.out.println("   [State] Aluguel devolvido sem multa.");
    }

    @Override
    public void atrasar(Aluguel aluguel) {
        aluguel.setEstado(new EstadoAtrasado());
        System.out.println("   [State] Aluguel marcado como ATRASADO (multa de R$ 5,00/dia).");
    }

    @Override
    public String getNome() {
        return "ALUGADO";
    }
}
